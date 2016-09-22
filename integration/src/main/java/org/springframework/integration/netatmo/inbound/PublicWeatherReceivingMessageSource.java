/**
 * 
 */
package org.springframework.integration.netatmo.inbound;

import java.util.List;

import org.springframework.social.netatmo.api.Netatmo;
import org.springframework.social.netatmo.api.PublicWeatherParameter;

/**
 * @author Nikolai Bock
 *
 */
public class PublicWeatherReceivingMessageSource
        extends AbstractNetatmoMessageSource<String> {

    private volatile double latSW;
    private volatile double lonSW;
    private volatile double latNE;
    private volatile double lonNE;
    private volatile List<String> requiredData;
    private volatile Boolean filter;

    public PublicWeatherReceivingMessageSource(Netatmo netatmo,
            String metadataKey) {
        super(netatmo, metadataKey);
    }

    /**
     * @param latSW
     *            the latSW to set
     */
    public void setLatSW(double latSW) {
        this.latSW = latSW;
    }

    /**
     * @param lonSW
     *            the lonSW to set
     */
    public void setLonSW(double lonSW) {
        this.lonSW = lonSW;
    }

    /**
     * @param latNE
     *            the latNE to set
     */
    public void setLatNE(double latNE) {
        this.latNE = latNE;
    }

    /**
     * @param lonNE
     *            the lonNE to set
     */
    public void setLonNE(double lonNE) {
        this.lonNE = lonNE;
    }

    /**
     * @param requiredData
     *            the requiredData to set
     */
    public void setRequiredData(List<String> requiredData) {
        this.requiredData = requiredData;
    }

    /**
     * @param filter
     *            the filter to set
     */
    public void setFilter(Boolean filter) {
        this.filter = filter;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.integration.netatmo.inbound.
     * AbstractNetatmoMessageSource#pollForMeasurements(long)
     */
    @Override
    protected List<String> pollForMeasurements(long sinceId) {
        return this.getNetatmo().weatherOperations().getPublicWeather(
                new PublicWeatherParameter(lonSW, latSW, lonNE, latNE)
                        .requiredData(requiredData).filter(filter));
    }

}
