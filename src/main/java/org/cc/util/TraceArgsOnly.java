package org.cc.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Log method arguments
 *
 * Daneel Yaitskov
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface TraceArgsOnly {
    LogLevel level() default LogLevel.INFO;
}
