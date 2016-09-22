/**
 * 
 */
package org.springframework.social.netatmo.api;

import java.util.List;

/**
 * @author Nikolai Bock
 *
 */
public class PublicWeatherParameter {

    private double latSW;
    private double lonSW;
    private double latNE;
    private double lonNE;
    private List<String> requiredData;
    private Boolean filter;

    public PublicWeatherParameter(double lonSW, double latSW, double lonNE,
            double latNE) {
        this.latSW = latSW;
        this.lonSW = lonSW;
        this.latNE = latNE;
        this.lonNE = lonNE;
    }

    /**
     * @param requiredData
     * @return
     */
    public PublicWeatherParameter requiredData(List<String> requiredData) {
        this.requiredData = requiredData;
        return this;
    }

    /**
     * @param filter
     * @return
     */
    public PublicWeatherParameter filter(Boolean filter) {
        this.filter = filter;
        return this;
    }

    /**
     * @return the latSW
     */
    public double getLatSW() {
        return latSW;
    }

    /**
     * @return the lonSW
     */
    public double getLonSW() {
        return lonSW;
    }

    /**
     * @return the latNE
     */
    public double getLatNE() {
        return latNE;
    }

    /**
     * @return the lonNE
     */
    public double getLonNE() {
        return lonNE;
    }

    /**
     * @return the required_data
     */
    public List<String> getRequiredData() {
        return requiredData;
    }

    /**
     * @return the filter
     */
    public Boolean getFilter() {
        return filter;
    }

}
