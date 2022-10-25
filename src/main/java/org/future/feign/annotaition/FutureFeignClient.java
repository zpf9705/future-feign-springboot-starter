package org.future.feign.annotaition;

import java.lang.annotation.*;

/**
 * @author zpf
 * @description FutureFeign client annotation
 * @createTime 2022-10-14 10:59
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
