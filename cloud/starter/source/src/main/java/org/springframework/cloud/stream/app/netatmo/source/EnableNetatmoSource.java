/**
 * 
 */
package org.springframework.cloud.stream.app.netatmo.source;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

/**
 * @author Nikolai Bock
 *
 */
@Documented
@Retention(RUNTIME)
@Target(TYPE)
@Inherited
@Import(NetatmoSourceConfiguration.class)
public @interface EnableNetatmoSource {

}
