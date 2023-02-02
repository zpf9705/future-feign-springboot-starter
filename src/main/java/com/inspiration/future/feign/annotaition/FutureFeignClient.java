package com.inspiration.future.feign.annotaition;

import java.lang.annotation.*;

/**
 * FutureFeign client annotation
 *
 * @author zpf
 * @since 1.1.0
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface FutureFeignClient {

    /**
     * name
     */
    String name() default "";

    /**
     * path
     */
    String path() default "";

    /**
     * uri
     */
    String uri() default "";
}
