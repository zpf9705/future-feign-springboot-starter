package com.inspiration.future.feign.annotaition;


import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author zpf
 * @description enable future-feign annotation
 * @createTime 2022-10-14 10:59
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import({FutureFeignRegister.class})
public @interface EnableFutureFeignClients {

    /**
     * scanner packages
     */
    String[] basePackages() default {};
}
