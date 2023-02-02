package com.inspiration.future.feign.logger;


import com.inspiration.future.feign.annotaition.LogTarget;
import com.inspiration.future.feign.exception.FutureFeignException;
import com.inspiration.future.feign.other.Constant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ServiceLoader;

/**
 * Static load logger provider
 * {@link ServiceLoader}
 * <p>
 * META-INF / services /  ... RPC_LOGGER
 * ....
 *
 * @author zpf
 * @since 1.1.0
 */
public final class FeignLogProvider {

    public static final Map<String, Logger> LOGGER_MAP = new HashMap<>();

    /**
     * rpc - loggers
     *
     * @see ServiceLoader#load(Class)
     */
    public static Logger RPC_LOGGER;

    static {
        try {
            resolverLogger();
        } finally {
            if (!CollectionUtils.isEmpty(LOGGER_MAP)){
                RPC_LOGGER = LOGGER_MAP.get(Constant.RPC_LOG);
            }else {
                // no find give default logger
                RPC_LOGGER = Logger.defaultLogger();
            }
        }
    }

    public static void resolverLogger() {
        //service load scanner
        ServiceLoader<Logger> load = ServiceLoader.load(Logger.class);
        //exchange iterator
        Iterator<Logger> iterator = load.iterator();
        //fu zhi
        iterator.forEachRemaining(v -> {
            try {
                Class<? extends Logger> aClass = v.getClass();
                //find @logTarget
                LogTarget annotation = aClass.getAnnotation(LogTarget.class);
                //find value
                String value = annotation.value();
                if (StringUtils.isNotBlank(value)) {
                    //full cache
                    LOGGER_MAP.putIfAbsent(value, aClass.newInstance());
                }
            } catch (Throwable e) {
                // to do noting
                throw new FutureFeignException("20031",
                        "logger load error", "89034", "check /META-INF/services/ have file");
            }
        });
    }
}
