package com.inspiration.future.feign.rpc;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.inspiration.future.feign.logger.FeignLogProvider;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.lang.NonNull;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * Pseudo Rpc client send
 *
 * @author zpf
 * @since 1.1.0
 */
public final class PseudoRpcUtils {

    private PseudoRpcUtils() {
        super();
    }

    /**
     * do http request switch
     *
     * @param requestMethod {@link RequestMethod}
     * @param url           current request url
     * @param param         request params
     * @return json return
     */
    public static String doRequestSwitch(RequestMethod requestMethod, String url, Object param) {
        String result;
        switch (requestMethod) {
            case GET:
                result = get(url, param);
                break;
            case POST:
                result = post(url, param);
                break;
            default:
                throw new UnsupportedOperationException("Unsupported Operation request type" + requestMethod.name());
        }
        return result;
    }

    /**
     * request get method
     *
     * @param url   current request url
     * @param param request params
     * @return json return
     */
    private static String get(String url, Object param) {
        return doRequest(new HttpGet(getRequestURI(url,
                param != null ? BeanUtil.beanToMap(param, new LinkedHashMap<>(),
                        false, true) : null)), getCurrentAllHeaders(), null);
    }

    /**
     * request post method
     *
     * @param url   current request url
     * @param param request params
     * @return json return
     */
    private static String post(String url, Object param) {
        return doRequest(new HttpPost(url), getCurrentAllHeaders(), JSON.toJSONString(param));
    }

    /**
     * real do request
     *
     * @param requestBase {@link HttpRequestBase}
     * @param headers     all headers
     * @param json        request json params
     * @return json return
     */
    private static String doRequest(@NonNull HttpRequestBase requestBase,
                                    Map<String, String> headers,
                                    String json) {
        CloseableHttpClient client = HttpClients.custom().build();
        CloseableHttpResponse response = null;
        String result = null;
        try {
            addHeaders(headers, requestBase);
            setEntity(json, requestBase);
            response = client.execute(requestBase);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity, StandardCharsets.UTF_8);
        } catch (Exception e) {
            FeignLogProvider.RPC_LOGGER.inPutErrorLog(e.getMessage(), e);
            e.printStackTrace(System.err);
        } finally {
            IOUtils.closeQuietly(response);
            IOUtils.closeQuietly(client);
        }
        return result;
    }

    /**
     * set application/json params
     *
     * @param json        json params
     * @param requestBase {@link HttpRequestBase}
     */
    private static void setEntity(String json, @NonNull HttpRequestBase requestBase) {
        if (StringUtils.isNotBlank(json) && requestBase instanceof HttpEntityEnclosingRequestBase) {
            StringEntity stringEntity = new StringEntity(json, ContentType.APPLICATION_JSON);
            HttpEntityEnclosingRequestBase base = (HttpEntityEnclosingRequestBase) requestBase;
            base.setEntity(stringEntity);
        }
    }

    /**
     * add current request headers
     *
     * @param headers     all headers
     * @param requestBase {@link HttpRequestBase}
     */
    private static void addHeaders(Map<String, String> headers, @NonNull HttpRequestBase requestBase) {
        if (!CollectionUtils.isEmpty(headers)) {
            for (Map.Entry<String, String> header : headers.entrySet()) {
                requestBase.addHeader(header.getKey(), header.getValue());
            }
        }
    }

    /**
     * get all current request
     *
     * @return {@link Map}
     */
    private static Map<String, String> getCurrentAllHeaders() {
        Map<String, String> headers = new HashMap<>();
        ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return headers;
        }
        HttpServletRequest request = attributes.getRequest();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headName = headerNames.nextElement();
            headers.put(headName, request.getHeader(headName));
        }
        return headers;
    }

    /**
     * get request uri {@link URI}
     *
     * @param url    current request url
     * @param params request params
     * @return {@link URI}
     */
    private static URI getRequestURI(String url, Map<String, Object> params) {
        URI uri = null;
        try {
            URIBuilder uriBuilder = new URIBuilder(url);
            if (!CollectionUtils.isEmpty(params)) {
                for (String paramKey : params.keySet()) {
                    uriBuilder.addParameter(paramKey, String.valueOf(params.get(paramKey)));
                }
            }
            uri = uriBuilder.build();
        } catch (Exception e) {
            FeignLogProvider.RPC_LOGGER.inPutErrorLog(e.getMessage(), e);
            e.printStackTrace(System.err);
        }
        return uri;
    }
}
