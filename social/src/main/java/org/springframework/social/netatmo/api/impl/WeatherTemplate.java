package org.springframework.social.netatmo.api.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.social.netatmo.api.PublicWeatherParameter;
import org.springframework.social.netatmo.api.WeatherOperations;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class WeatherTemplate extends AbstractTemplate
        implements WeatherOperations {

    private final ObjectMapper mapper = new ObjectMapper();

    public WeatherTemplate(NetatmoTemplate netatmo, boolean authorized) {
        super(netatmo, authorized);
    }

    @Override
    public List<String> getPublicWeather(PublicWeatherParameter parameter) {
        return getPublicWeather(parameter.getLatSW(), parameter.getLonSW(),
                parameter.getLatNE(), parameter.getLonNE(),
                parameter.getRequiredData(), parameter.getFilter());
    }

    @Override
    public List<String> getPublicWeather(double latSW, double lonSW,
            double latNE, double lonNE, List<String> required_data,
            Boolean filter) {

        MultiValueMap<String, String> queryVariables = new LinkedMultiValueMap<>();
        queryVariables.set("lat_ne", String.valueOf(latNE));
        queryVariables.set("lon_ne", String.valueOf(lonNE));
        queryVariables.set("lat_sw", String.valueOf(latSW));
        queryVariables.set("lon_sw", String.valueOf(lonSW));
        if (required_data != null && !required_data.isEmpty()) {
            queryVariables.set("required_data",
                    required_data.stream().collect(Collectors.joining(", ")));
        }
        if (filter != null) {
            queryVariables.set("filter", String.valueOf(filter));
        }

        return this.netatmo.fetch("getpublicdata", a -> {
            try {
                return mapper.writeValueAsString(a);
            } catch (JsonProcessingException e) {
                return null;
            }
        }, queryVariables);
    }
}
