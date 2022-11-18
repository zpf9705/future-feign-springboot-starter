package com.inspiration.future.feign.logger;

import com.inspiration.future.feign.annotaition.LogTarget;
import com.inspiration.future.feign.exception.FutureFeignException;
import com.inspiration.future.feign.other.Constant;
import org.apache.commons.lang3.StringUtils;

import java.util.Iterator;
import java.util.Objects;
import java.util.ServiceLoader;

/**
 * @author zpf
 * @description logger provider
 * @createTime 2022-11-18 14:59
 */
public abstract class FeignLogProvider {

    /**
     * rpc - loggers
     * @see ServiceLoader#load(Class)
     */
    public static Logger RPC_LOGGER = null;

    static {
        //service load scanner
        ServiceLoader<Logger> load = ServiceLoader.load(Logger.class);
        //exchange iterator
        Iterator<Logger> iterator = load.iterator();
        //fu zhi
        iterator.forEachRemaining(v -> {
            Class<? extends Logger> aClass = v.getClass();
            //find @logTarget
            LogTarget annotation = aClass.getAnnotation(LogTarget.class);
            if (StringUtils.isNotBlank(annotation.value())) {
                if (Objects.equals(annotation.value(), Constant.RPC_LOG)) {
                    try {
                        RPC_LOGGER = aClass.newInstance();
                    } catch (Throwable e) {
                        // to do noting
                        throw new FutureFeignException("20031",
                                "logger load error","89034","check /META-INF/services/ have file");
                    }
                }
            }
        });
    }
}
