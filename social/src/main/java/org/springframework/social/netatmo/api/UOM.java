/**
 * 
 */
package org.springframework.social.netatmo.api;

/**
 * @author Nikolai Bock
 *
 */
public enum UOM {

    TEMPERATURE("°C"), HUMIDITY("%"), PRESSURE("mbar");

    private final String uom;

    private UOM(String uom) {
        this.uom = uom;
    }

    public String getUOM() {
        return uom;
    }

}
