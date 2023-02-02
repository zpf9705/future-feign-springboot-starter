package com.inspiration.future.feign.bean;

import cn.hutool.aop.ProxyUtil;
import com.inspiration.future.feign.proxy.FutureFeignProxyInvoke;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.util.Assert;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @see FactoryBean
 * @see InvocationHandler
 *
 * Using the factorybean springboot bean personalized injection
 * Then use the JDK dynamic proxy to create a proxy object method directly
 *
 * @author zpf
 * @since 1.1.0
 */
public class FutureFeignBean<T> extends FutureFeignProxyInvoke implements FactoryBean<T>, InvocationHandler {

    /**
     * uri
     */
    private String uri;

    /**
     * path
     */
    private String path;

    /**
     * Proxy bean
     */
    private Class<T> interfaceClass;

    /**
     * interface-Class-setter
     */
    public Class<T> getInterfaceClass() {
        return interfaceClass;
    }

    /**
     * interface-Class-getter
     */
    public void setInterfaceClass(Class<T> interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    /**
     * api-request-uri-setter
     */
    public void setUri(String uri) {
        this.uri = uri;
    }

    /**
     * api-request-uri-getter
     */
    public String getUri() {
        return uri;
    }

    /**
     * api-request-path-getter
     */
    public String getPath() {
        return path;
    }

    /**
     * api-request-path-getter
     */
    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public T getObject() throws Exception {
        return ProxyUtil.newProxyInstance(this, interfaceClass);
    }

    @Override
    public Class<?> getObjectType() {
        return interfaceClass;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    @SuppressWarnings("PMD.Raw use of parameterized rawtypes all")
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Assert.notNull(proxy, "proxy obj can not be null !");
        Assert.isTrue(StringUtils.isNotBlank(uri), "uri not be null ！");
        uri = StringUtils.isNotBlank(uri) ? uri + path : uri;
        return execute(proxy, method, args, uri);
    }
}
