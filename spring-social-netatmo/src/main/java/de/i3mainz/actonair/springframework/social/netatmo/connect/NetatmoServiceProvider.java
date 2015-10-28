/**
 * 
 */
package de.i3mainz.actonair.springframework.social.netatmo.connect;

import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.oauth2.OAuth2Template;

import de.i3mainz.actonair.springframework.social.netatmo.api.Netatmo;
import de.i3mainz.actonair.springframework.social.netatmo.api.impl.NetatmoTemplate;

/**
 * @author Nikolai Bock
 *
 */
public class NetatmoServiceProvider extends
        AbstractOAuth2ServiceProvider<Netatmo> {

    public NetatmoServiceProvider(String clientId, String clientSecret) {
        super(new OAuth2Template(clientId, clientSecret,
                "https://api.netatmo.net/oauth2/authorize",
                "https://api.netatmo.net/oauth2/token"));
    }

    @Override
    public Netatmo getApi(String accessToken) {
        return new NetatmoTemplate(accessToken);
    }

}
