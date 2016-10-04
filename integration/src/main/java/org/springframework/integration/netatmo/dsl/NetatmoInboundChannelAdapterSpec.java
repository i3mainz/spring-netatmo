package org.springframework.integration.netatmo.dsl;

import java.util.List;

import org.springframework.integration.dsl.core.MessageSourceSpec;
import org.springframework.integration.netatmo.inbound.PublicWeatherReceivingMessageSource;
import org.springframework.social.netatmo.api.Netatmo;
import org.springframework.social.netatmo.api.PublicWeatherParameter;

public class NetatmoInboundChannelAdapterSpec extends
        MessageSourceSpec<NetatmoInboundChannelAdapterSpec, PublicWeatherReceivingMessageSource> {

    private final Netatmo template;
    private final String metadataKey;
    private final PublicWeatherParameter parameter;

    public NetatmoInboundChannelAdapterSpec(Netatmo template,
            String metadataKey, double latSW, double lonSW, double latNE,
            double lonNE, List<String> requiredData, Boolean filter) {
        this(template, metadataKey,
                new PublicWeatherParameter(lonSW, latSW, lonNE, latNE)
                        .requiredData(requiredData).filter(filter));
    }

    public NetatmoInboundChannelAdapterSpec(Netatmo template,
            String metadataKey, PublicWeatherParameter parameter) {
        this.template = template;
        this.metadataKey = metadataKey;
        this.parameter = parameter;
    }

    @Override
    protected PublicWeatherReceivingMessageSource doGet() {
        PublicWeatherReceivingMessageSource msgSource;
        msgSource = new PublicWeatherReceivingMessageSource(template,
                metadataKey);
        msgSource.setParameter(parameter);
        return msgSource;
    }
}
