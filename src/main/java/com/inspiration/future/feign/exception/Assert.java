package com.inspiration.future.feign.exception;

import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Objects;

/**
 * Assert error of this project
 *
 * @author zpf
 * @since 1.1.0
 */
public abstract class Assert {

    /**
     * check no null
     * obj ok pass
     * obj is null {@link #filed(String)}
     */
    public static void noNull(Object o, @NonNull String msg) {
        if (Objects.isNull(o)) {
            filed(msg);
        }
    }

    /**
     * check no blank
     * str no blank or empty string pass
     * if blank or empty {@link #filed(String)}
     */
    public static void noBlank(String s, @NonNull String msg) {
        if (StringUtils.isBlank(s)) {
            filed(msg);
        }
    }

    /**
     * check no empty
     * collection ok pass
     * collection empty or null {@link #filed(String)}
     */
    public static <T> void noEmpty(Collection<T> collection, @NonNull String msg) {
        if (CollectionUtils.isEmpty(collection)) {
            filed(msg);
        }
    }

    /**
     * check no true
     * expression == true pass,
     * expression == false {@link #filed(String)}
     */
    public static void isTrue(boolean expression, @NonNull String msg) {
        if (!expression) {
            filed(msg);
        }
    }

    /**
     * failed throw method
     */
    public static void filed(@NonNull String msg) {
        throw new FutureFeignException(
                "check error", msg, "sub-error", "assert full error");
    }
}
