/**
 * 
 */
package org.springframework.integration.netatmo.inbound;

import java.util.Comparator;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.integration.context.IntegrationContextUtils;
import org.springframework.integration.context.IntegrationObjectSupport;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.metadata.MetadataStore;
import org.springframework.integration.metadata.SimpleMetadataStore;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;
import org.springframework.social.netatmo.api.Netatmo;
import org.springframework.social.netatmo.api.WeatherStationMeasurement;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * @author Nikolai Bock
 *
 */
public abstract class AbstractNetatmoMessageSource<T>
        extends IntegrationObjectSupport implements MessageSource<T> {

    private final Netatmo netatmo;
    private final Object lastEnqueuedIdMonitor = new Object();
    private final String metadataKey;
    private volatile MetadataStore metadataStore;
    private final Queue<T> measurements = new LinkedBlockingQueue<T>();
    private volatile int prefetchThreshold = 0;
    private volatile long lastEnqueuedId = -1;
    private volatile long lastProcessedId = -1;
    private final NetatmoComparator comparator = new NetatmoComparator();

    protected AbstractNetatmoMessageSource(Netatmo netatmo,
            String metadataKey) {

        Assert.notNull(netatmo, "netatmo must not be null");
        Assert.notNull(metadataKey, "metadataKey must not be null");
        this.netatmo = netatmo;
        // if (this.netatmo.isAuthorized()) {
        // UserOperations userOperations = this.twitter.userOperations();
        // String profileId = String.valueOf(userOperations.getProfileId());
        // if (profileId != null) {
        // metadataKey += "." + profileId;
        // }
        // }
        this.metadataKey = metadataKey;
    }

    public void setMetadataStore(MetadataStore metadataStore) {
        this.metadataStore = metadataStore;
    }

    public void setPrefetchThreshold(int prefetchThreshold) {
        this.prefetchThreshold = prefetchThreshold;
    }

    /**
     * @return the netatmo
     */
    public Netatmo getNetatmo() {
        return netatmo;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.integration.context.IntegrationObjectSupport#onInit()
     */
    @Override
    protected void onInit() throws Exception {
        super.onInit();
        if (this.metadataStore == null) {
            // first try to look for a 'metadataStore' in the context
            BeanFactory beanFactory = this.getBeanFactory();
            if (beanFactory != null) {
                this.metadataStore = IntegrationContextUtils
                        .getMetadataStore(beanFactory);
            }
            if (this.metadataStore == null) {
                this.metadataStore = new SimpleMetadataStore();
            }
        }

        String lastId = this.metadataStore.get(this.metadataKey);
        // initialize the last status ID from the metadataStore
        if (StringUtils.hasText(lastId)) {
            this.lastProcessedId = Long.parseLong(lastId);
            this.lastEnqueuedId = this.lastProcessedId;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.integration.core.MessageSource#receive()
     */
    @Override
    public Message<T> receive() {
        T measurement = this.measurements.poll();
        if (measurement == null) {
            this.refreshMeasurementQueueIfNecessary();
            measurement = this.measurements.poll();
        }

        if (measurement != null) {
            this.lastProcessedId = this.getIdForMeasurement(measurement);
            this.metadataStore.put(this.metadataKey,
                    String.valueOf(this.lastProcessedId));
            return this.getMessageBuilderFactory().withPayload(measurement)
                    .build();
        }
        return null;
    }

    private void enqueueAll(List<T> measurements) {
        measurements.stream().sorted(comparator).forEach(this::enqueue);
    }

    private void enqueue(T measurement) {
        synchronized (this.lastEnqueuedIdMonitor) {
            long id = this.getIdForMeasurement(measurement);
            if (id > this.lastEnqueuedId) {
                this.measurements.add(measurement);
                this.lastEnqueuedId = id;
            }
        }
    }

    private void refreshMeasurementQueueIfNecessary() {
        try {
            if (this.measurements.size() <= this.prefetchThreshold) {
                List<T> measurements = pollForMeasurements(this.lastEnqueuedId);
                if (!CollectionUtils.isEmpty(measurements)) {
                    enqueueAll(measurements);
                }
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new MessagingException("failed while polling Netatmo", e);
        }
    }

    protected abstract List<T> pollForMeasurements(long sinceId);

    private long getIdForMeasurement(T netatmoMessage) {
        if (netatmoMessage instanceof WeatherStationMeasurement) {
            return Long.getLong(((WeatherStationMeasurement) netatmoMessage)
                    .getId().split("||")[1]);
        } else {
            throw new IllegalArgumentException(
                    "Unsupported Netatmo object: " + netatmoMessage);
        }
    }

    /**
     * Remove the metadata key and the corresponding value from the Metadata
     * Store.
     */
    @ManagedOperation(description = "Remove the metadata key and the corresponding value from the Metadata Store.")
    void resetMetadataStore() {
        synchronized (this) {
            this.metadataStore.remove(this.metadataKey);
            this.lastProcessedId = -1L;
            this.lastEnqueuedId = -1L;
        }
    }

    /**
     *
     * @return {@code -1} if lastProcessedId is not set, yet.
     */
    @ManagedAttribute
    public long getLastProcessedId() {
        return this.lastProcessedId;
    }

    private class NetatmoComparator implements Comparator<T> {

        @Override
        public int compare(T measurement1, T measurement2) {
            if (measurement1 instanceof String
                    && measurement2 instanceof String) {
                return measurement1.toString()
                        .compareTo(measurement2.toString());
            } else if (measurement1 instanceof WeatherStationMeasurement
                    && measurement2 instanceof WeatherStationMeasurement) {
                Long m1 = getLong((WeatherStationMeasurement) measurement1);
                Long m2 = getLong((WeatherStationMeasurement) measurement2);
                return m1.compareTo(m2);
            } else {
                throw new IllegalArgumentException(
                        "Uncomparable netatmo measurement objects: "
                                + measurement1 + " and " + measurement2);
            }
        }

        private Long getLong(WeatherStationMeasurement measurement) {
            return Long.getLong(measurement.getId().split("||")[1]);
        }
    }

}
