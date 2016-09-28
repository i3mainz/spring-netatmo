package org.springframework.social.netatmo.api;

import java.time.ZonedDateTime;

public class Measurement {
    
    private ZonedDateTime date;
    private String property;
    private String uom;
    private Object value;

    /**
     * @param date
     * @param property
     * @param uom
     * @param value
     */
    public Measurement(ZonedDateTime date, String property, String uom,
            Object value) {
        super();
        this.date = date;
        this.property = property;
        this.uom = uom;
        this.value = value;
    }

    /**
     * @return the date
     */
    public ZonedDateTime getDate() {
        return date;
    }

    /**
     * @return the property
     */
    public String getProperty() {
        return property;
    }

    /**
     * @return the uom
     */
    public String getUom() {
        return uom;
    }

    /**
     * @return the value
     */
    public Object getValue() {
        return value;
    }
}
