package com.spring.sutils.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.Consts;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.util.*;

@Component
public class HttpClientMethod {

    private Logger logger = LoggerFactory.getLogger(HttpClientMethod.class);

    private static final String CHARSET_UTF8 = "UTF-8";
    private static final String GENERAL_CONTENT_TYPE = "application/x-www-form-urlencoded;charset=UTF-8";
    private static final String JSON_CONTENT_TYPE = "application/json;charset=UTF-8";

    private static final int maxConnectionsPerHost = 100;
    private static final int maxTotalConnections = 100;
    private static final int socketTimeout = 20000;
    private static final int connectionTimeout = 30000;
    private static final String userAgentString = "Chrome/21.0.1180.89";
    private static Collection<BasicHeader> defaultHeaders = new HashSet<>();

    protected static CloseableHttpClient httpClient;
    protected static PoolingHttpClientConnectionManager connectionManager;

    static {
        RequestConfig requestConfig = RequestConfig.custom() // .setStaleConnectionCheckEnabled(Boolean.TRUE)
                .setConnectionRequestTimeout(connectionTimeout).setConnectTimeout(connectionTimeout)
                .setSocketTimeout(socketTimeout).build();

        connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(maxTotalConnections);
        connectionManager.setDefaultMaxPerRoute(maxConnectionsPerHost);

        HttpClientBuilder clientBuilder = HttpClientBuilder.create();
        clientBuilder.setDefaultRequestConfig(requestConfig);
        clientBuilder.setConnectionManager(connectionManager);
        clientBuilder.setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy());
        clientBuilder.setConnectionReuseStrategy(new DefaultConnectionReuseStrategy());
        clientBuilder.setUserAgent(userAgentString);
        clientBuilder.setDefaultHeaders(defaultHeaders);
        httpClient = clientBuilder.build();
        HttpClients.createDefault();
    }

    public Object GET(String url) {
        return GET(url, null, null);
    }

    public Object GET(String url, Map<String, Object> params) {
        return GET(url, null, params);
    }

    public Object GET(String url, Map<String, Object> headers, Map<String, Object> params) {
        long startTime = System.currentTimeMillis();
        String realUrl = url;
        try {
            if (null != params && 0 < params.size()) {
                String paramStr = "";
                for (Map.Entry<String, Object> entry : params.entrySet()) {
                    if (!StringUtil.isEmptyOrNull(entry.getValue())) {
                        paramStr += entry.getKey() + "=" + URLEncoder.encode(entry.getValue().toString(), CHARSET_UTF8)
                                + "&";
                    }
                }
                realUrl = url.indexOf("?") >= 1 ? url + paramStr : url + "?" + paramStr;
            }

            HttpGet request = new HttpGet(realUrl);
            if (null != headers && 0 < headers.size()) {
                request = convertHeaders(request, headers);
            }

            CloseableHttpResponse response = httpClient.execute(request);
            StatusLine statusLine = response.getStatusLine();
            String ret = EntityUtils.toString(response.getEntity());
            long endTime = System.currentTimeMillis();
            if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                logger.info("接口【{}】", realUrl);
                logger.info("返回【{}】", ret);
                logger.info("耗时【{} ms】", endTime - startTime);
                return ret;
            } else {
                logger.error("接口【{}】返回调用，状态码：{}, 出参：{}", realUrl, statusLine.getStatusCode(), ret);
                logger.info("耗时【{} ms】", endTime - startTime);
                throw new BaseException(LwReturnCodeEnum.COM_EXCEPTION_API_RETURN_FAIL);
            }
        } catch (Exception e) {
            logger.error("接口【" + realUrl + "】调用异常", e);
            throw new BaseException(LwReturnCodeEnum.COM_EXCEPTION_API_OCCUR_ERROR);
        }
    }

    public Object GET(String url, Object bean) {
        return GET(url, null, null, bean);
    }

    public Object POST(String url, Map<String, Object> headers, Map<String, Object> params) {
        return POST(url, headers, params, null, null);
    }

    public Object POST(String url, Map<String, Object> headers, String json) {
        return POST(url, headers, null, json, null);
    }

    public Object POST(String url, Map<String, Object> headers, Object bean) {
        return POST(url, headers, null, null, bean);
    }

    public Object POST(String url, Map<String, Object> params) {
        return POST(url, null, params, null, null);
    }

    public Object POST(String url, String json) {
        return POST(url, null, null, json, null);
    }

    public Object POST(String url, Object bean) {
        return POST(url, null, null, null, bean);
    }

    public Object GET(String url, Map<String, Object> headers, Map<String, Object> params, Object bean) {
        long startTime = System.currentTimeMillis();
        String realUrl = url;
        String queryString = "";
        try {
            if (null != params && 0 < params.size()) {
                for (Map.Entry<String, Object> entry : params.entrySet()) {
                    if (!StringUtil.isEmptyOrNull(entry.getValue())) {
                        queryString += entry.getKey() + "="
                                + URLEncoder.encode(entry.getValue().toString(), CHARSET_UTF8)
                                + "&";
                    }
                }
            }

            if (null != bean) {
                queryString += BeanUtil.convertBeanToQueryString(bean, false);
            }

            realUrl = url.indexOf("?") >= 1 ? url + "&" + queryString : url + "?" + queryString;

            HttpGet request = new HttpGet(realUrl);
            if (null != headers && 0 < headers.size()) {
                request = convertHeaders(request, headers);
            }

            CloseableHttpResponse response = httpClient.execute(request);
            StatusLine statusLine = response.getStatusLine();
            String ret = EntityUtils.toString(response.getEntity());
            long endTime = System.currentTimeMillis();
            if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                logger.info("接口【{}】", realUrl);
                logger.info("返回【{}】", ret);
                logger.info("耗时【{} ms】", endTime - startTime);
                return ret;
            } else {
                logger.error("接口【{}】返回调用，状态码：{}, 出参：{}", realUrl, statusLine.getStatusCode(), ret);
                logger.info("耗时【{} ms】", endTime - startTime);
                throw new BaseException(40010002, "接口【" + realUrl + "】调用返回失败");
            }
        } catch (Exception e) {
            logger.error("接口【" + realUrl + "】调用异常", e);
            throw new BaseException(40010001, "接口【" + realUrl + "】调用异常");
        }
    }

    public Object POST(String url, Map<String, Object> headers, Map<String, Object> params, String json, Object bean) {
        long startTime = System.currentTimeMillis();
        logger.info("! -> url : {}", url);
        if(null != headers) {
            logger.info("! -> headers : {}", JSONObject.toJSONString(headers));
        }
        if(null != params) {
            logger.info("! -> params : {}", JSONObject.toJSONString(params));
        }
        if(null != json) {
            logger.info("! -> json : {}", json);
        }
        if(null != bean) {
            logger.info("! -> bean : {}", JSONObject.toJSONString(bean));
        }
        try {
            HttpPost request = new HttpPost(url);
            if (null != headers && 0 < headers.size()) {
                request = convertHeaders(request, headers);
            }
            if (!StringUtil.isEmptyOrNull(json)) {
                request = convertJson(request, json);
            } else if (null != params && 0 < params.size()) {
                request = convertParams(request, params);
            } else if (null != bean) {
                request = convertBean(request, bean);
            }

            CloseableHttpResponse response = httpClient.execute(request);
            StatusLine statusLine = response.getStatusLine();
            String ret = EntityUtils.toString(response.getEntity());
            long endTime = System.currentTimeMillis();
            if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                logger.info("接口【{}】", url);
                logger.info("返回【{}】", ret);
                logger.info("耗时【{} ms】", endTime - startTime);
                return ret;
            } else {
                logger.error("接口【{}】返回调用，状态码：{}, 出参：{}", url, statusLine.getStatusCode(), ret);
                logger.info("耗时【{} ms】", endTime - startTime);
                throw new BaseException(LwReturnCodeEnum.COM_EXCEPTION_API_RETURN_FAIL);
            }
        } catch (Exception e) {
            logger.error("接口【" + url + "】调用异常", e);
            throw new BaseException(LwReturnCodeEnum.COM_EXCEPTION_API_OCCUR_ERROR);
        }
    }

    private HttpGet convertHeaders(HttpGet request, Map<String, Object> headers) {
        if (null != headers && 0 < headers.size()) {
            for (Map.Entry<String, Object> entry : headers.entrySet()) {
                if (StringUtil.isEmptyOrNull(request.getFirstHeader(entry.getKey()))) {
                    request.addHeader(entry.getKey(), String.valueOf(entry.getValue()));
                } else {
                    request.setHeader(entry.getKey(), String.valueOf(entry.getValue()));
                }
            }
        }
        return request;
    }

    private HttpPost convertHeaders(HttpPost request, Map<String, Object> headers) {
        if (null != headers && 0 < headers.size()) {
            for (Map.Entry<String, Object> entry : headers.entrySet()) {
                if (StringUtil.isEmptyOrNull(request.getFirstHeader(entry.getKey()))) {
                    request.addHeader(entry.getKey(), String.valueOf(entry.getValue()));
                } else {
                    request.setHeader(entry.getKey(), String.valueOf(entry.getValue()));
                }
            }
        }

        return request;
    }

    private HttpPost convertParams(HttpPost request, Map<String, Object> params) {
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            NameValuePair pair = new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue()));
            nameValuePairs.add(pair);
        }
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(nameValuePairs, Consts.UTF_8);
        entity.setContentEncoding(CHARSET_UTF8);
        entity.setContentType(GENERAL_CONTENT_TYPE);
        request.setEntity(entity);
        return request;
    }

    private HttpPost convertBean(HttpPost request, Object bean)
            throws IllegalAccessException, IntrospectionException, InvocationTargetException {
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(BeanUtil.convertBeanToNVP(bean, false), Consts.UTF_8);
        entity.setContentEncoding(CHARSET_UTF8);
        entity.setContentType(GENERAL_CONTENT_TYPE);
        request.setEntity(entity);
        return request;
    }

    private HttpPost convertJson(HttpPost request, String json) {
        StringEntity entity = new StringEntity(json, Consts.UTF_8);
        entity.setContentEncoding(CHARSET_UTF8);
        entity.setContentType(JSON_CONTENT_TYPE);
        request.setEntity(entity);
        return request;
    }

}
