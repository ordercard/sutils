package com.spring.sutils.util;

import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashSet;


/**
 * @Auther :huiqiang
 * @Description :
 * @Date: Create in 下午5:06 2018/7/28 2018
 * @Modify:
 */
@Component
public class WeiXinImgUtil {

    private Logger logger = LoggerFactory.getLogger(WeiXinImgUtil.class);

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

    /**
    *
     *@描述  获取微信头像的请求方法

     *@参数  [url]

     *@返回值  INPUT

     *@创建人  慧强

     *@创建时间  2018/7/28

     *@修改人和其它信息

     */
    public  InputStream getwexinIMG(String url) throws IOException {
        long startTime = System.currentTimeMillis();
        String realUrl = url;

        HttpGet request = new HttpGet(realUrl);

        CloseableHttpResponse response = httpClient.execute(request);
        StatusLine statusLine = response.getStatusLine();
        InputStream inputStream = response.getEntity().getContent();
        long endTime = System.currentTimeMillis();
        if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
            logger.info("接口【{}】", realUrl);
            logger.info("耗时【{} ms】", endTime - startTime);
            return inputStream;
        } else {
            logger.error("接口【{}】返回调用，状态码：{}, 出参：{}", realUrl, statusLine.getStatusCode());
            logger.info("耗时【{} ms】", endTime - startTime);
            throw new BaseException(40010002, "接口【" + realUrl + "】调用返回失败");
        }
    }

    /**
     *
     *@描述  设置返回照片 contenttype
     *@参数 [inputStream, httpServletResponse]
     *@返回值 void
     *@创建人 慧强
     *@创建时间 2018/7/30
     *@修改人和其它信息

     */
    public  void writeImg(BufferedImage bufferedImage , HttpServletResponse httpServletResponse) throws IOException {


        httpServletResponse.setHeader("Content-Type", "image/jpeg");
         ImageIO.write(bufferedImage, "jpg", httpServletResponse.getOutputStream());
    }
/**
 public String getgetSalesmanInviteInfoImg(String url, SalesmanInviteInfoImgPo salesmanInviteInfoImgPo, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, String invitePage, String logoImg, String headPanel) throws IOException, WriterException {
 //从阿里云获取相关图片 被邀请页面 logo
 InputStream inviteInputStream = weiXinImgUtil.getwexinIMG(invitePage);
 InputStream logoInputStream = weiXinImgUtil.getwexinIMG(logoImg);
 InputStream headPanelStream = weiXinImgUtil.getwexinIMG(headPanel);
 InputStream logo2InputStream = weiXinImgUtil.getwexinIMG(logoImg);
 BufferedImage imageInvite = ImageIO.read(inviteInputStream);
 BufferedImage headPanelImg = ImageIO.read(headPanelStream);
 BufferedImage logo = ImageIO.read(logo2InputStream);
 String content = url + "/app/recruits.html?" + "salesmanId=" + salesmanInviteInfoImgPo.getSalesmanId() + "&openId=" + salesmanInviteInfoImgPo.getOpenId();
 //生成二维码
 BufferedImage qrcode = OperateImageUtil.createQrcCodeImage(content, logoInputStream, true);
 //二维码和背景进行叠加
 int x = 190;
 int y = 315;
 BufferedImage contentImg = OperateImageUtil.superposeImg(imageInvite, qrcode, x, y);
 //完成的图片显示、
 BufferedImage resultTempImg = OperateImageUtil.composeImg(contentImg, headPanelImg);
 String name = "";
 String msg = "“注册来往旅行会员，跟我一起赚钱”";
 BufferedImage resImg = null;
 if (salesmanInviteInfoImgPo.getOpenId() != null) {
 InputStream userHeadStream = weiXinImgUtil.getwexinIMG(salesmanInviteInfoImgPo.getHeadimgurl());
 BufferedImage userHeadImg = ImageIO.read(userHeadStream);
 name = salesmanInviteInfoImgPo.getWeixinName();
 name= URLDecoder.decode(name,"utf-8");
 resImg = OperateImageUtil.setwriteFontAndHeadImg(name, msg, resultTempImg, userHeadImg);
 } else {

 UcenterSalesmanInfo ucenterSalesmanInfo = ucenterSalesmanInfoMapper.selectByPrimaryKey(salesmanInviteInfoImgPo.getSalesmanId());
 String phone = ucenterSalesmanInfo.getMobile();
 name = weixinName(phone);
 resImg = OperateImageUtil.setwriteFontAndHeadImg(name, msg, resultTempImg, logo);

 }
 ByteArrayOutputStream os = new ByteArrayOutputStream();
 ImageIO.write(resImg, "jpg", os);
 String  resulturl=   upFilealiyunUtil.upFileToAliyunserver(httpServletRequest,httpServletResponse, new ByteArrayInputStream(os.toByteArray()));

 return resulturl;


 */

}
