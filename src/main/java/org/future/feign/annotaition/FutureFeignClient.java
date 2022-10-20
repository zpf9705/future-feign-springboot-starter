package org.future.feign.annotaition;

import java.lang.annotation.*;

/**
 * @author zpf
 * @description FutureFeign 客户端注解
 * @createTime 2022-10-14 10:59
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface FutureFeignClient {

    /**
     * 名称
     */
    String name() default "";

    /**
     * 接口路径
     */
    String path() default "";

    /**
     * 访问uri
     */
    String uri() default "";
}
