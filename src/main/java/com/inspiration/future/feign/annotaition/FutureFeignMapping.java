package com.inspiration.future.feign.annotaition;


import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.annotation.*;

/**
 * @author zpf
 * @description FutureFeign client annotation
 * @see org.springframework.web.bind.annotation.PostMapping
 * @see org.springframework.web.bind.annotation.GetMapping
 * @createTime 2022-10-14 10:59
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
