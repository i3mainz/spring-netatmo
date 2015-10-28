/**
 * 
 */
package de.i3mainz.actonair.springframework.social.netatmo.api.impl;

import org.springframework.web.client.RestTemplate;

/**
 * @author Nikolai Bock
 *
 */
public class AbstractTemplate {

    protected final RestTemplate restTemplate;
    protected boolean isAuthorized;
    
    public AbstractTemplate(RestTemplate restTemplate, boolean isAuthorized) {
        super();
        this.restTemplate = restTemplate;
        this.isAuthorized = isAuthorized;
    }
    
    
}
