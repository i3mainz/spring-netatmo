/**
 * 
 */
package org.springframework.cloud.stream.app.netatmo.source;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.app.trigger.TriggerConfiguration;
import org.springframework.cloud.stream.app.trigger.TriggerPropertiesMaxMessagesDefaultUnlimited;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.netatmo.dsl.Netatmo;
import org.springframework.integration.netatmo.dsl.NetatmoInboundChannelAdapterSpec;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.social.netatmo.connect.NetatmoServiceProvider;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Operations;

/**
 * @author Nikolai Bock
 *
 */
@Configuration
@EnableConfigurationProperties(value = { NetatmoSourceProperties.class,
        TriggerPropertiesMaxMessagesDefaultUnlimited.class })
@EnableBinding(Source.class)
@Import(TriggerConfiguration.class)
public class NetatmoSourceConfiguration {

    @Autowired
    private NetatmoSourceProperties properties;

    @Autowired
    @Qualifier("defaultPoller")
    PollerMetadata defaultPoller;

    @Bean
    public IntegrationFlow flow() {

        NetatmoInboundChannelAdapterSpec messageSourceSpec = Netatmo
                .inboundAdapter(netatmo(), "netatmo", properties.getLatSW(),
                        properties.getLonSW(), properties.getLatNE(),
                        properties.getLonNE(), properties.getRequiredData(),
                        properties.getFilter());

        return IntegrationFlows
                .from(messageSourceSpec, spec -> spec.poller(defaultPoller))
                .channel(Source.OUTPUT).get();
    }

    @Bean
    public org.springframework.social.netatmo.api.Netatmo netatmo() {
        NetatmoServiceProvider provider = new NetatmoServiceProvider(
                properties.getAppId(), properties.getAppSecret());
        OAuth2Operations oauth = provider.getOAuthOperations();
        AccessGrant clientGrant = oauth.exchangeCredentialsForAccess(
                properties.getUsername() + "@" + properties.getUserhost(),
                properties.getPassword(), null);
        return provider.getApi(clientGrant.getAccessToken());
    }

}
