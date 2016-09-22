/**
 * 
 */
package org.springframework.social.netatmo.api.impl;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.social.netatmo.api.Netatmo;
import org.springframework.social.netatmo.api.WeatherOperations;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author Nikolai Bock
 *
 */
public class NetatmoTemplate extends AbstractOAuth2ApiBinding
        implements Netatmo {

    public static final String BASE_URL = "https://api.netatmo.net/api/";
    private WeatherOperations weatherOperations;

    public NetatmoTemplate(String accessToken) {
        super(accessToken);
        initSubApis();

    }

    private void initSubApis() {
        this.weatherOperations = new WeatherTemplate(this, isAuthorized());
    }

    @Override
    public WeatherOperations weatherOperations() {
        return this.weatherOperations;
    }

    public <T> List<T> fetch(String subPath, Function<Map<?, ?>, T> function,
            MultiValueMap<String, String> queryParameters) {
        URI uri = UriComponentsBuilder.fromHttpUrl(BASE_URL + subPath)
                .queryParams(queryParameters).build().toUri();
        Map<?, ?> body = getRestTemplate().getForObject(uri, Map.class);
        @SuppressWarnings("unchecked")
        List<Map<?, ?>> entities = (List<Map<?, ?>>) body.get("body");
        return entities.stream().map(function).collect(Collectors.toList());

    }

}
