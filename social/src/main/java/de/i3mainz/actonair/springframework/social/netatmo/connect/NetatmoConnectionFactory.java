/**
 * 
 */
package de.i3mainz.actonair.springframework.social.netatmo.connect;

import org.springframework.social.connect.support.OAuth2ConnectionFactory;

import de.i3mainz.actonair.springframework.social.netatmo.api.Netatmo;

/**
 * @author Nikolai Bock
 *
 */
public class NetatmoConnectionFactory extends OAuth2ConnectionFactory<Netatmo> {

    public NetatmoConnectionFactory(String clientId, String clientSecret) {
        super("netatmo", new NetatmoServiceProvider(clientId, clientSecret),
                new NetatmoAdapter());
    }

}
