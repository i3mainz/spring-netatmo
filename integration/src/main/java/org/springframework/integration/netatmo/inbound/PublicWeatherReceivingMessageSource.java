/**
 * 
 */
package org.springframework.integration.netatmo.inbound;

import java.util.List;

import org.springframework.social.netatmo.api.Netatmo;
import org.springframework.social.netatmo.api.PublicWeatherParameter;
import org.springframework.social.netatmo.api.WeatherStationMeasurement;

import com.vividsolutions.jts.util.Assert;

/**
 * @author Nikolai Bock
 *
 */
public class PublicWeatherReceivingMessageSource
        extends AbstractNetatmoMessageSource<WeatherStationMeasurement> {

    private volatile Double latSW;
    private volatile Double lonSW;
    private volatile Double latNE;
    private volatile Double lonNE;
    private volatile List<String> requiredData;
    private volatile Boolean filter;
    private volatile PublicWeatherParameter parameter;

    public PublicWeatherReceivingMessageSource(Netatmo netatmo,
            String metadataKey) {
        super(netatmo, metadataKey);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.integration.netatmo.inbound.
     * AbstractNetatmoMessageSource#onInit()
     */
    @Override
    protected void onInit() throws Exception {
        super.onInit();

        if (parameter == null) {
            Assert.isTrue(bboxIsNull(), "The bounding box has to set!");
            this.parameter = new PublicWeatherParameter(this.lonSW, this.latSW,
                    this.lonNE, this.latNE).requiredData(requiredData)
                            .filter(filter);
        }
    }

    private boolean bboxIsNull() {
        return this.latSW == null || this.lonSW == null || this.lonNE == null
                || this.latNE == null;
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

    /**
     * @param parameter
     *            the parameter to set
     */
    public void setParameter(PublicWeatherParameter parameter) {
        this.parameter = parameter;
    }

    @Override
    protected List<WeatherStationMeasurement> pollForMeasurements(
            long sinceId) {
        return this.getNetatmo().weatherOperations()
                .getPublicWeather(this.parameter);
    }
}
