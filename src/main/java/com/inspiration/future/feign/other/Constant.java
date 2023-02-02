package com.inspiration.future.feign.other;



/**
 * future-feign Constant
 *
 * @author zpf
 * @since 1.1.0
 */
public final class Constant {

    /**
     * FutureFeignClient scanner path name
     *
     * @see com.inspiration.future.feign.annotaition.EnableFutureFeignClients
     */
    public static final String BASE_PACKAGES = "basePackages";

    /**
     * request uri name
     *
     * @see com.inspiration.future.feign.annotaition.EnableFutureFeignClients
     */
    public static final String URI = "uri";

    /**
     * future-feign interface class name
     *
     * @see com.inspiration.future.feign.annotaition.FutureFeignClient
     */
    public static final String INTERFACE_CLASS = "interfaceClass";

    /**
     * proxy obj name
     *
     * @see com.inspiration.future.feign.annotaition.FutureFeignClient
     */
    public static final String NAME = "name";

    /**
     * proxy obj method path name
     *
     * @see com.inspiration.future.feign.annotaition.FutureFeignClient
     */
    public static final String PATH = "path";

    /**
     * rpc - log
     * @see com.inspiration.future.feign.logger.RpcRecordLog
     */
    public static final String RPC_LOG = "RPC_LOG";
}
