package org.future.feign.other;

/**
 * @author zpf
 * @description future-feign Constant
 * @createTime 2022-10-15 14:27
 */
public final class Constant {

    /**
     * FutureFeignClient scanner path name
     *
     * @see org.future.feign.annotaition.EnableFutureFeignClients
     */
    public static final String BASE_PACKAGES = "basePackages";

    /**
     * request uri name
     *
     * @see org.future.feign.annotaition.FutureFeignClient
     */
    public static final String URI = "uri";

    /**
     * future-feign interface class name
     *
     * @see org.future.feign.annotaition.FutureFeignClient
     */
    public static final String INTERFACE_CLASS = "interfaceClass";

    /**
     * proxy obj name
     *
     * @see org.future.feign.annotaition.FutureFeignClient
     */
    public static final String NAME = "name";

    /**
     * proxy obj method path name
     *
     * @see org.future.feign.annotaition.FutureFeignClient
     */
    public static final String PATH = "path";
}
