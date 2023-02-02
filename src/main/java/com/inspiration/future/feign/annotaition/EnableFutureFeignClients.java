package com.inspiration.future.feign.annotaition;


import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Enable future-feign register annotation
 *
 * @see FutureFeignRegister
 * @see FutureFeignClient
 * @see FutureFeignMapping
 *
 * @author zpf
 * @since 1.1.0
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
