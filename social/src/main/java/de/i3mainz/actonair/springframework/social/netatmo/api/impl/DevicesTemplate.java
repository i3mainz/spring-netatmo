package de.i3mainz.actonair.springframework.social.netatmo.api.impl;

import org.springframework.web.client.RestTemplate;

import de.i3mainz.actonair.springframework.social.netatmo.api.DeviceOperations;

public class DevicesTemplate extends AbstractTemplate implements
        DeviceOperations {

    private String url;

    public DevicesTemplate(RestTemplate restTemplate, boolean authorized,
            String baseUrl) {
        super(restTemplate, authorized);
        this.url = baseUrl;
    }
}
