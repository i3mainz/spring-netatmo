/**
 * 
 */
package org.springframework.social.netatmo.api.impl;

/**
 * @author Nikolai Bock
 *
 */
public class AbstractTemplate {

    protected final NetatmoTemplate netatmo;
    protected boolean isAuthorized;

    public AbstractTemplate(NetatmoTemplate restTemplate,
            boolean isAuthorized) {
        super();
        this.netatmo = restTemplate;
        this.isAuthorized = isAuthorized;
    }

}
