package com.inspiration.future.feign.annotaition;

import java.lang.annotation.*;

/**
 * logger sign annotation
 *
 * @author zpf
 * @since 1.1.0
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogTarget {

    String value() default "";
}
