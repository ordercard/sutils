package com.spring.sutils.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.spring.sutils.db.OssTokenVo;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashSet;

/**
 * @Auther :huiqiang
 * @Description :
 * @Date: Create in 上午11:46 2018/7/31 2018
 * @Modify:
 */
@Component
public class UpFilealiyunUtil {
    @Autowired
    HttpClientMethod httpClientMethod;
    @Value("${aliyun.url}")
    private String alyunUrl;
    @Value("${oss.url}")
    private String ossUrl;
    private Logger logger = LoggerFactory.getLogger(UpFilealiyunUtil.class);

    private static final String CHARSET_UTF8 = "UTF-8";
    private static final String GENERAL_CONTENT_TYPE = "application/x-www-form-urlencoded;charset=UTF-8";
    private static final String JSON_CONTENT_TYPE = "application/json;charset=UTF-8";
    private static final String FORM_DATA = "multipart/form-data";
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




   public String  upFileToAliyunserver(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, InputStream inputStream ) throws IOException {

       OssTokenVo ossTokenVo = getOssToken(httpServletRequest);
       HttpPost request = new HttpPost(ossTokenVo.getAddress());
       MultipartEntityBuilder builder = MultipartEntityBuilder.create();
       builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
       StringBody oSSAccessKeyId = new StringBody(ossTokenVo.getOSSAccessKeyId(), ContentType.create("multipart/form-data", Consts.UTF_8));
       builder.addPart("OSSAccessKeyId",oSSAccessKeyId);
       StringBody signature = new StringBody(ossTokenVo.getSignature(), ContentType.create("multipart/form-data", Consts.UTF_8));
       builder.addPart("Signature",signature);
       StringBody key = new StringBody(ossTokenVo.getKey(), ContentType.create("multipart/form-data", Consts.UTF_8));
       builder.addPart("key",key);
       StringBody policy = new StringBody(ossTokenVo.getPolicy(), ContentType.create("multipart/form-data", Consts.UTF_8));
       builder.addPart("policy",policy);
       builder.addBinaryBody("file",inputStream, ContentType.create("multipart/form-data", Consts.UTF_8),"a.jpg");
       HttpEntity entity = builder.build();
       request.setEntity(entity);
       CloseableHttpResponse response = httpClient.execute(request);
       StatusLine statusLine = response.getStatusLine();
     //  String ret = EntityUtils.toString(response.getEntity());
       if (statusLine.getStatusCode() == 204) {
           logger.info("接口【{}】", ossTokenVo.getAddress());
          // logger.info("返回【{}】", ret);
           return ossTokenVo.getPath() ;
       } else {
           logger.error("接口【{}】返回调用，状态码：{}, 出参：{}", ossTokenVo.getAddress(), statusLine.getStatusCode());
           throw new BaseException(LwReturnCodeEnum.COM_EXCEPTION_API_RETURN_FAIL);
       }

   }




    private OssTokenVo   getOssToken(HttpServletRequest request){
        String ip = NetworkUtil.getIpAddress(request);
        String res = StringUtil.obj2Str(httpClientMethod.GET(alyunUrl+"/passport/v1.0/e/ossToken?clientIp="+ip));
        JSONObject jsonObject = JSONObject.parseObject(res);
        OssTokenVo ossTokenVo = new OssTokenVo();
        ossTokenVo.setError(1);
        if (200 == jsonObject.getIntValue("status")) {
            JSONObject data = jsonObject.getJSONObject("data");
            JSONObject param = data.getJSONObject("param");
            JSONArray jsonArray = data.getJSONArray("keys");
            JSONObject keys = JSONObject.parseObject(jsonArray.get(0).toString());
            ossTokenVo.setAddress(data.getString("address"));
            ossTokenVo.setOSSAccessKeyId(param.getString("OSSAccessKeyId"));
            ossTokenVo.setSignature(param.getString("Signature"));
            ossTokenVo.setPolicy(param.getString("policy"));
            ossTokenVo.setPath(ossUrl+ keys.getString("path"));
            ossTokenVo.setKey(keys.getString("key"));
            ossTokenVo.setError(0);
        }
        return ossTokenVo;
    }
}
