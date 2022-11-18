package com.inspiration.future.feign.proxy;

import cn.hutool.core.util.ArrayUtil;
import com.alibaba.fastjson.JSONObject;
import com.inspiration.future.feign.annotaition.FutureFeignMapping;
import com.inspiration.future.feign.exception.Assert;
import com.inspiration.future.feign.exception.FutureFeignException;
import com.inspiration.future.feign.http.FutureFeignSender;
import com.inspiration.future.feign.logger.FeignLogProvider;
import com.inspiration.future.feign.other.P;
import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableOnSubscribe;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Objects;

/**
 * @author zpf
 * @description future-feign-client-Proxy-Invoke
 * @createTime 2022-10-23 18:42
 */
public abstract class FutureFeignProxyInvoke {

    private static final RequestMethod DEFAULT_REQUEST_METHOD = RequestMethod.POST;

    public FutureFeignProxyInvoke() {
    }


    /**
     * execute and after throw exception
     * showing retry 2 times
     * when first success break;
     * but failed execute while retry
     *
     * @see java.lang.reflect.InvocationHandler
     * @see Flowable#create(FlowableOnSubscribe, BackpressureStrategy)
     */
    public Object execute(Object proxy, Method method, Object[] args, String uri) {
        return Flowable.create(click -> {
                    click.onNext(proxyInvokeHttpSendResponse(proxy, method, args, uri));
                    click.onComplete();
                }, BackpressureStrategy.LATEST)
                .retry(2)
                .ofType(String.class)
                .map(data -> exchangeEntity(method, data))
                .blockingSingle();
    }

    /**
     * proxy Invoke HttpSend Response
     */
    public String proxyInvokeHttpSendResponse(Object proxy, Method method, Object[] args,
                                              String uri) throws FutureFeignException {
        return invokeHttpSendResponse(proxy, method, args, uri);
    }

    /**
     * invoke HttpSend Response
     *
     * @include url check
     * @include @annotation check
     * @include method check
     */
    public String invokeHttpSendResponse(Object proxy, Method method, Object[] args,
                                         String uri) throws FutureFeignException {
        Assert.noNull(proxy, "proxy obj can not be null !");
        Annotation[] annotations = method.getAnnotations();
        Assert.isTrue(ArrayUtil.isNotEmpty(annotations), "proxy obj method no find @Annotation !");
        boolean match = Arrays.stream(annotations).anyMatch(this::match);
        Assert.isTrue(match, "mapping @Annotation only be Annotation is @FutureFeignMapping or @GetMapping " +
                "or @PostMapping");
        /*check Annotations*/
        Annotation one = checkAnnotations(annotations);
        /*get url http-method*/
        P<RequestMethod, String> annotationValue = getAnnotationValue(one, method, uri);
        /*get param*/
        Object param = getParamObj(args);
        return FutureFeignSender.sendAction(annotationValue.getLeft(),
                annotationValue.getRight(), FutureFeignSender.gethearders(),
                param);
    }

    /**
     * get request param
     */
    private Object getParamObj(Object[] args) {
        if (ArrayUtil.isEmpty(args)) {
            return new Object();
        }
        return args[0];
    }

    /**
     * get Annotation Value
     * to sure request path and method
     */
    private P<RequestMethod, String> getAnnotationValue(Annotation annotation, Method method,
                                                        String uri) {
        RequestMethod requestMethod = null;
        String value = null;
        if (annotation.annotationType() == FutureFeignMapping.class) {
            FutureFeignMapping futureFeignMapping = method.getAnnotation(FutureFeignMapping.class);
            value = futureFeignMapping.value();
            requestMethod = Objects.nonNull(futureFeignMapping.method()) ?
                    futureFeignMapping.method() : DEFAULT_REQUEST_METHOD;
        } else {
            if (annotation.annotationType() == PostMapping.class) {
                PostMapping postMapping = method.getAnnotation(PostMapping.class);
                requestMethod = RequestMethod.POST;
                value = ArrayUtil.isNotEmpty(postMapping.value())
                        ? postMapping.value()[0] : null;
            } else if (annotation.annotationType() == GetMapping.class) {
                GetMapping getMapping = method.getAnnotation(GetMapping.class);
                requestMethod = RequestMethod.GET;
                value = ArrayUtil.isNotEmpty(getMapping.value())
                        ? getMapping.value()[0] : null;
            }
        }
        String url = checkFinalUrl(uri, value);
        return P.of(requestMethod, url);
    }

    /**
     * after match = true
     * only @FutureFeignClient or @GetMapping or @PostMapping
     * if have many only get one
     * other no same in
     * and default Annotation
     *
     * @see FutureFeignMapping
     */
    private Annotation checkAnnotations(Annotation[] annotations) {
        Annotation checkAnnotation = null;
        if (ArrayUtil.isEmpty(annotations)) {
            checkAnnotation = new Annotation() {
                @Override
                public Class<? extends Annotation> annotationType() {
                    return FutureFeignMapping.class;
                }
            };
        } else {
            checkAnnotation = annotations[0];
        }
        return checkAnnotation;
    }


    /**
     * match future feign client annotation
     *
     * @see FutureFeignMapping
     * @see GetMapping
     * @see PostMapping
     */
    private boolean match(Annotation annotation) {
        Class<? extends Annotation> aClass = annotation.annotationType();
        return Objects.equals(aClass, FutureFeignMapping.class) ||
                Objects.equals(aClass, GetMapping.class) ||
                Objects.equals(aClass, PostMapping.class);
    }

    /**
     * check http response status is success
     */
    private boolean isSuccess(CloseableHttpResponse closeableHttpResponse) {
        return Objects.equals(closeableHttpResponse.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
    }

    /**
     * check request url
     */
    private String checkFinalUrl(String uri, String value) {
        String url = null;
        if (StringUtils.isNotBlank(value)) {
            url = uri + value;
        } else {
            url = uri;
        }
        Exception ex = null;
        try {
            new URI(url);
        } catch (URISyntaxException e) {
            FeignLogProvider.RPC_LOGGER.inPutErrorLog(
                    "url Syntax error {}", url);
            ex = e;
        }
        Assert.isTrue(Objects.isNull(ex), "URISyntaxException to check url");
        return url;
    }

    /**
     * when retry thread sleep
     * wait this solve io
     *
     * @see Thread
     */
    private void sleepWithoutInterrupt(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            FeignLogProvider.RPC_LOGGER.inPutErrorLog(
                    "thread sleep error current thread interrupt "
            );
            Thread.currentThread().interrupt();
        }

    }

    /**
     * after response
     * and sure this time the is success
     * so can exchange this response to method return type
     */
    private Object exchangeEntity(Method method, String rsp) {
        Object response = null;
        try {
            response = JSONObject.parseObject(rsp, method.getReturnType());
        } catch (Exception e) {
            //logger and throw
            FeignLogProvider.RPC_LOGGER.inPutErrorLog("json parse failed return type error:{}",
                    e.getMessage());
            throw new FutureFeignException("json parse failed return type");
        }
        return response;
    }
}
