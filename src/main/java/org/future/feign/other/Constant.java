package org.future.feign.other;

/**
 * @author zpf
 * @description future-feign 常量
 * @createTime 2022-10-15 14:27
 */
public final class Constant {

    /**
     * 客户端扫描路径
     *
     * @see org.future.feign.annotaition.EnableFutureFeignClients
     */
    public static final String BASE_PACKAGES = "basePackages";

    /**
     * 方法访问uri
     *
     * @see org.future.feign.annotaition.FutureFeignClient
     */
    public static final String URI = "uri";

    /**
     * future-feign接口名称
     *
     * @see org.future.feign.annotaition.FutureFeignClient
     */
    public static final String INTERFACE_CLASS = "interfaceClass";

    /**
     * 代理对象名称
     *
     * @see org.future.feign.annotaition.FutureFeignClient
     */
    public static final String NAME = "name";

    /**
     * 方法访问路径
     *
     * @see org.future.feign.annotaition.FutureFeignClient
     */
    public static final String PATH = "path";
}
