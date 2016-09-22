/**
 * 
 */
package org.springframework.social.netatmo.connect;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.MultiValueMap;

/**
 * @author Nikolai Bock
 *
 */
public class NetatmoOAuth2Template extends OAuth2Template {

    public NetatmoOAuth2Template(String clientId, String clientSecret,
            String authorizeUrl, String accessTokenUrl) {
        super(clientId, clientSecret, authorizeUrl, accessTokenUrl);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.social.oauth2.OAuth2Template#postForAccessGrant(java.
     * lang.String, org.springframework.util.MultiValueMap)
     */

    @Override
    @SuppressWarnings("unchecked")
    protected AccessGrant postForAccessGrant(String accessTokenUrl,
            MultiValueMap<String, String> parameters) {
        return extractAccessGrant(getRestTemplate()
                .postForObject(accessTokenUrl, parameters, Map.class));
    }

    private AccessGrant extractAccessGrant(Map<String, Object> result) {
        String accessToken = (String) result.get("access_token");
        @SuppressWarnings("unchecked")
        String scope = ((List<String>) result.get("scope")).stream()
                .collect(Collectors.joining(", "));
        String refreshToken = (String) result.get("refresh_token");
        Number expiresIn = (Number) result.get("expires_in");
        return createAccessGrant(accessToken, scope, refreshToken,
                expiresIn.longValue(), result);
    }

}
