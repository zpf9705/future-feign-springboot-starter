package org.future.feign.annotaition;


import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author zpf
 * @description 开启future-feign注解
 * @createTime 2022-10-14 10:59
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import({FutureFeignRegister.class})
public @interface EnableFutureFeignClients {

    /**
     * 扫描路径
     */
    String[] basePackages() default {};
}
