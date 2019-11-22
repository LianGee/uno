package com.bigdata.uno.common.util;

import com.bigdata.uno.common.exception.ServerException;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Map;

public class HttpUtil {
        public enum Method {
        GET, POST, PUT, DELETE, PATCH, TRACE;

        public HttpRequestBase buildMethod(String url) {
            switch (this) {
                case DELETE:
                    return new HttpDelete(url);
                case TRACE:
                    return new HttpTrace(url);
                case PATCH:
                    return new HttpPatch(url);
                case POST:
                    return new HttpPost(url);
                case PUT:
                    return new HttpPut(url);
                default:
                    return new HttpGet(url);
            }
        }
    }

    @Getter
    @Setter
    @Builder
    public static class RequestConf {
        private Method method;
        private String url;
        private String charset;
        private String body;
        private String referer;
        private String cookie;
        private HttpEntity entity;
        private Map<String, String> headers;
        private String authority;
        HttpClientContext context;
    }

    private static final int TIMEOUT = 10000;

    public static Pair<String, byte[]> requestWithContentType(RequestConf config) throws IOException {
        if (config == null || config.getMethod() == null || StringUtils.isEmpty(config.getUrl())) {
            throw new IllegalArgumentException("invalid request config");
        }
        HttpRequestBase method = config.getMethod().buildMethod(config.getUrl());
        if (config.getHeaders() != null) {
            config.getHeaders().forEach(method::addHeader);
        }
        method.addHeader("Connection", "close");
        if (config.getHeaders() == null || !config.getHeaders().containsKey("Content-Type")) {
            method.addHeader("Content-Type", "application/json;charset=UTF-8");
        }
        if (config.getHeaders() != null && config.getHeaders().get("Content-Type") == null) {
            method.removeHeaders("Content-Type");
        }
        if (config.getAuthority() != null) {
            method.addHeader("authority", config.getAuthority());
        }
        if (config.getReferer() != null) {
            method.addHeader("Referer", config.getReferer());
        }
        if (config.getCookie() != null) {
            method.addHeader("cookie", config.getCookie());
        }
        RequestConfig.Builder requestConfigBuilder = RequestConfig.custom()
                .setExpectContinueEnabled(false)
                .setSocketTimeout(TIMEOUT)
                .setConnectTimeout(TIMEOUT)
                .setConnectionRequestTimeout(TIMEOUT)
                .setCircularRedirectsAllowed(true);
        method.setConfig(requestConfigBuilder.build());

        if (method instanceof HttpEntityEnclosingRequest) {
            if (config.getBody() != null) {
                ((HttpEntityEnclosingRequest) method).setEntity(new StringEntity(config.getBody(), "UTF-8"));
            } else if (config.getEntity() != null) {
                ((HttpEntityEnclosingRequest) method).setEntity(config.getEntity());
            }
        }

        HttpClientBuilder clientBuilder = HttpClients.custom();
        CloseableHttpClient client = clientBuilder.build();
        try {
            CloseableHttpResponse response = client.execute(method, config.context);
            try {
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode < 200 || statusCode >= 300) {
                    throw new ServerException("HTTP request failed: status = " + statusCode + ","
                            + EntityUtils.toString(response.getEntity()));
                }
                String contentType = response.getEntity().getContentType() != null
                        ? response.getEntity().getContentType().getValue() : null;
                byte[] responseData = EntityUtils.toByteArray(response.getEntity());
                return Pair.of(contentType, responseData);

            } finally {
                response.close();
            }
        } catch (Exception e) {
            throw e;

        } finally {
            method.releaseConnection();
            client.close();
        }
    }

    public static String getImageBase64(RequestConf config) throws IOException {
        Pair<String, byte[]> result = HttpUtil.requestWithContentType(config);
        return String.format("data:%s;base64,%s", result.getLeft(), EncodeUtil.encodeBase64(result.getRight()));
    }

    public static String request(RequestConf config) throws IOException {
        String charset = config.getCharset() == null ? "UTF-8" : config.getCharset();
        return new String(requestWithContentType(config).getRight(), charset);
    }

    public static String get(String url) throws IOException {
        return request(RequestConf.builder()
                .method(Method.GET)
                .url(url)
                .build());
    }

    public static String post(String url, String body) throws IOException {
        return request(RequestConf.builder()
                .method(Method.POST)
                .url(url)
                .body(body)
                .build());
    }
}
