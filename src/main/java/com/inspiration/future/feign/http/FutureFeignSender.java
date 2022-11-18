package com.inspiration.future.feign.http;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import lombok.SneakyThrows;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


@SuppressWarnings("all")
public final class FutureFeignSender {

    private FutureFeignSender() {
        throw new AssertionError("No " + FutureFeignSender.class.getName() + " instances for you!");
    }

    @SneakyThrows
    public static String sendAction(RequestMethod requestMethod, String url, Map<String, String> headers,
                              Object param) {
        String result = null;
        switch (requestMethod) {
            case GET:
                result = sendGet(url, headers, BeanUtil.beanToMap(param, new LinkedHashMap<>(),
                        false, true));
                break;
            case POST:
                result = sendPostJson(url, headers, JSON.toJSONString(param));
                break;
            default:
                Assert.isTrue(false, "不支持的请求类型！");
                break;
        }
        return result;
    }

    public static String sendGet(String url, Map<String, String> headers, Map<String, Object> params) throws Exception {
        CloseableHttpClient client = HttpClients.custom().build();
        CloseableHttpResponse response = null;
        String result = "";
        try {
            URIBuilder uriBuilder = getURIBuilder(url, params);
            HttpGet httpGet = new HttpGet(uriBuilder.build());
            if (!CollectionUtils.isEmpty(headers)) {
                for (Map.Entry<String, String> header : headers.entrySet()) {
                    httpGet.addHeader(header.getKey(), header.getValue());
                }
            }
            response = client.execute(httpGet);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity, "utf-8");
        } finally {
            IOUtils.closeQuietly(response);
            IOUtils.closeQuietly(client);
        }
        return result;
    }


    public static String sendPostJson(String url, Map<String, String> headers, String json) throws Exception {
        CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String result = null;
        try {
            HttpPost httpPost = new HttpPost(url);
            //设置头部
            if (!CollectionUtils.isEmpty(headers)) {
                for (Map.Entry<String, String> header : headers.entrySet()) {
                    httpPost.addHeader(header.getKey(), header.getValue());
                }
            }
            if (!StringUtils.isEmpty(json)) {
                StringEntity stringEntity = new StringEntity(json, ContentType.APPLICATION_JSON);
                httpPost.setEntity(stringEntity);
            }
            response = client.execute(httpPost);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity, "utf-8");
        } finally {
            IOUtils.closeQuietly(response);
            IOUtils.closeQuietly(client);
        }
        return result;
    }

    public static Map<String, String> gethearders() {
        Map<String, String> hearders = new HashMap<>();
        ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headName = headerNames.nextElement();
            hearders.put(headName, request.getHeader(headName));
        }
        return hearders;
    }

    public static String changeHttpEntity(CloseableHttpResponse response) throws IOException {
        Assert.notNull(response, "CloseableHttpResponse no be null!");
        HttpEntity entity = response.getEntity();
        return EntityUtils.toString(entity, "utf-8");
    }

    private static URIBuilder getURIBuilder(String url, Map<String, Object> params) throws URISyntaxException {
        URIBuilder uriBuilder = new URIBuilder(url);
        if (!CollectionUtils.isEmpty(params)) {
            for (String paramKey : params.keySet()) {
                uriBuilder.addParameter(paramKey, String.valueOf(params.get(paramKey)));
            }
        }
        return uriBuilder;
    }
}
