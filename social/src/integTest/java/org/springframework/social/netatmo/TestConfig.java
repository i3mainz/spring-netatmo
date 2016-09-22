/**
 * 
 */
package org.springframework.social.netatmo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * @author Nikolai Bock
 *
 */
@Configuration
@PropertySource(value = { "classpath:application.properties" })
public class TestConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertiesResolver() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
