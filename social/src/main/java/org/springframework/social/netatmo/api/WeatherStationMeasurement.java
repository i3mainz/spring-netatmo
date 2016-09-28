/**
 * 
 */
package org.springframework.social.netatmo.api;

import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.List;

import com.vividsolutions.jts.geom.Point;

/**
 * @author Nikolai Bock
 *
 */
public class WeatherStationMeasurement {

    private String id;
    private Point location;
    private List<Measurement> measurements;

    /**
     * @param location
     * @param date
     * @param measurements
     */
    public WeatherStationMeasurement(String prefix, Point location,
            List<Measurement> measurements) {
        super();
        this.id = prefix + "||" + measurements.stream()
                .map(Measurement::getDate)
                .max(Comparator.comparingLong(ZonedDateTime::toEpochSecond))
                .get().toEpochSecond();
        this.location = location;
        this.measurements = measurements;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @return the location
     */
    public Point getLocation() {
        return location;
    }

    /**
     * @return the measurements
     */
    public List<Measurement> getMeasurements() {
        return measurements;
    }

}
