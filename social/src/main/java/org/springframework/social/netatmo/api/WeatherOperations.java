/**
 * 
 */
package org.springframework.social.netatmo.api;

import java.util.List;

/**
 * @author Nikolai Bock
 *
 */
public interface WeatherOperations {
    
    public List<WeatherStationMeasurement> getPublicWeather(PublicWeatherParameter parameter);

    public List<WeatherStationMeasurement> getPublicWeather(double latSW, double lonSW, double latNE,
            double lonNE, List<String> required_data, Boolean filter);

}
