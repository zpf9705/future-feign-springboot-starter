package com.inspiration.future.feign.annotaition;

import java.lang.annotation.*;

/**
 * @author zpf
 * @description log sign
 * @create: 2022-06-28 11:38
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogTarget {

    String value() default "";
}
