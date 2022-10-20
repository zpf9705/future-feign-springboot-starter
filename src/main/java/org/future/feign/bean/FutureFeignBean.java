package org.future.feign.bean;

import cn.hutool.aop.ProxyUtil;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.future.feign.annotaition.FutureFeignMapping;
import org.future.feign.http.FutureFeignSender;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author zpf
 * @description 注册机自动装配bean
 * @createTime 2022-10-14 16:01
 */
public class FutureFeignBean<T> implements FactoryBean<T>, InvocationHandler {

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
    @SuppressWarnings("PMD.Raw use of parameterized rawtypes all")
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        FutureFeignMapping annotation = method.getAnnotation(FutureFeignMapping.class);
        Assert.notNull(annotation, "@ApiMapping not be null ！");
        String mothodPath = annotation.path();
        RequestMethod requestMethod = annotation.method();
        Assert.isTrue(StringUtils.isNotBlank(path), "path not be null ！");
        Assert.isTrue(StringUtils.isNotBlank(uri), "path not be null ！");
        Object param = null;
        if (ArrayUtils.isNotEmpty(args)) {
            param = args[0];
        }
        Assert.notNull(param, "api param is null !");
        Object returnParam = null;
        String response = FutureFeignSender.send(requestMethod, uri + path + mothodPath,
                FutureFeignSender.gethearders(), param);
        if (StringUtils.isNotBlank(response)){
            returnParam = JSONObject.parseObject(response, method.getReturnType());
        }
        return returnParam;
    }
}
