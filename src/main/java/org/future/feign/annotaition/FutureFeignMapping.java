package org.future.feign.annotaition;


import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FutureFeignMapping {

    /**
     * api 访问路径
     */
    String path() default "";

    /**
     * api访问方法
     */
    RequestMethod method() default RequestMethod.POST;
}
