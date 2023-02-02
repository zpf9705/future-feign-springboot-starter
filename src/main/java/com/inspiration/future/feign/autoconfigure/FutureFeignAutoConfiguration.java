package com.inspiration.future.feign.autoconfigure;


import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnJava;
import org.springframework.boot.system.JavaVersion;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 * AutoConfiguration Automatic assembly of things temporarily no need
 *
 * @author zpf
 * @since 1.1.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnJava(JavaVersion.EIGHT)
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
public class FutureFeignAutoConfiguration {

    //BUT NO
}
