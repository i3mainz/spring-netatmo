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
    
    public List<String> getPublicWeather(PublicWeatherParameter parameter);

    public List<String> getPublicWeather(double latSW, double lonSW, double latNE,
            double lonNE, List<String> required_data, Boolean filter);

}
