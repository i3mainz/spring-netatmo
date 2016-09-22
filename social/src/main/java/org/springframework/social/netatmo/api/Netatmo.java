/**
 * 
 */
package org.springframework.social.netatmo.api;

import org.springframework.social.ApiBinding;

/**
 * @author Nikolai Bock
 *
 */
public interface Netatmo extends ApiBinding {
    
    WeatherOperations weatherOperations();

}
