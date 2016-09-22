/**
 * 
 */
package org.springframework.social.netatmo.connect;

import org.springframework.social.netatmo.api.Netatmo;
import org.springframework.social.netatmo.api.impl.NetatmoTemplate;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;

/**
 * @author Nikolai Bock
 *
 */
public class NetatmoServiceProvider extends
        AbstractOAuth2ServiceProvider<Netatmo> {

    public NetatmoServiceProvider(String clientId, String clientSecret) {
        super(new NetatmoOAuth2Template(clientId, clientSecret,
                "https://api.netatmo.net/oauth2/authorize",
                "https://api.netatmo.net/oauth2/token"));
    }

    @Override
    public Netatmo getApi(String accessToken) {
        return new NetatmoTemplate(accessToken);
    }

}
