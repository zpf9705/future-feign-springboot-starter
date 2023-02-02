package com.inspiration.future.feign.annotaition;


import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.annotation.*;

/**
 * FutureFeign client annotation
 * @see org.springframework.web.bind.annotation.PostMapping
 * @see org.springframework.web.bind.annotation.GetMapping
 *
 * @author zpf
 * @since 1.1.0
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FutureFeignMapping {

    /**
     * api value
     */
    String value() default "";

    /**
     * api method
     */
    RequestMethod method() default RequestMethod.POST;
}
