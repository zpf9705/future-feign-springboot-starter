package com.inspiration.future.feign.other;

import com.inspiration.future.feign.annotaition.EnableFutureFeignClients;
import com.inspiration.future.feign.annotaition.FutureFeignClient;

/**
 * @author zpf
 * @description future-feign Constant
 * @createTime 2022-10-15 14:27
 */
public final class Constant {

    /**
     * FutureFeignClient scanner path name
     *
     * @see EnableFutureFeignClients
     */
    public static final String BASE_PACKAGES = "basePackages";

    /**
     * request uri name
     *
     * @see FutureFeignClient
     */
    public static final String URI = "uri";

    /**
     * future-feign interface class name
     *
     * @see FutureFeignClient
     */
    public static final String INTERFACE_CLASS = "interfaceClass";

    /**
     * proxy obj name
     *
     * @see FutureFeignClient
     */
    public static final String NAME = "name";

    /**
     * proxy obj method path name
     *
     * @see FutureFeignClient
     */
    public static final String PATH = "path";

    /**
     * rpc - log
     * @see com.inspiration.future.feign.logger.RpcRecordLog
     */
    public static final String RPC_LOG = "RPC_LOG";
}
