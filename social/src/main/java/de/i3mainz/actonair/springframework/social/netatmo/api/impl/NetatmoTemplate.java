/**
 * 
 */
package de.i3mainz.actonair.springframework.social.netatmo.api.impl;

import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;

import de.i3mainz.actonair.springframework.social.netatmo.api.DeviceOperations;
import de.i3mainz.actonair.springframework.social.netatmo.api.Netatmo;

/**
 * @author nikolai
 *
 */
public class NetatmoTemplate extends AbstractOAuth2ApiBinding implements
        Netatmo {

    public static final String BASE_URL = "https://api.netatmo.net/api/";

    private DeviceOperations deviceOperations;

    public NetatmoTemplate(String accessToken) {
        super(accessToken);
        initSubApis(accessToken);

    }

    private void initSubApis(String accessToken) {
        this.deviceOperations = new DevicesTemplate(getRestTemplate(),
                isAuthorized(), BASE_URL);
    }

    @Override
    public DeviceOperations deviceOperations() {
        return this.deviceOperations;
    }

}
