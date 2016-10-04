/**
 * 
 */
package org.springframework.integration.netatmo.dsl;

import java.util.List;

import org.springframework.social.netatmo.api.PublicWeatherParameter;

/**
 * @author Nikolai Bock
 *
 */
public abstract class Netatmo {

    public static NetatmoInboundChannelAdapterSpec inboundAdapter(
            org.springframework.social.netatmo.api.Netatmo netatmo,
            String metadataKey, PublicWeatherParameter parameter) {
        return new NetatmoInboundChannelAdapterSpec(netatmo, metadataKey,
                parameter);
    }

    public static NetatmoInboundChannelAdapterSpec inboundAdapter(
            org.springframework.social.netatmo.api.Netatmo netatmo,
            String metadataKey, double latSW, double lonSW, double latNE,
            double lonNE, List<String> requiredData, Boolean filter) {
        return new NetatmoInboundChannelAdapterSpec(netatmo, metadataKey, latSW,
                lonSW, latNE, lonNE, requiredData, filter);
    }

}
