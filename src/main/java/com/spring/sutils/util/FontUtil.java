package com.spring.sutils.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * @Auther :huiqiang
 * @Description :
 * @Date: Create in 下午6:16 2018/7/31 2018
 * @Modify:
 */
public class FontUtil {


    /*


    Class.getResource(String path)
path不以’/'开头时，默认是从此类所在的包下取资源；
path  以’/'开头时，则是从ClassPath根下获取；

Class.getClassLoader（）.getResource(String path)
path不能以’/'开头时；
path是从ClassPath根下获取；

Class.getClassLoader（）.getResource和Class.getClassLoader（）.getResourceAsStream在使用时，路径选择上也是一样的。

TestMain.class.getResource("/") == t.getClass().getClassLoader().getResource("")
     */
    private  static Logger logger = LoggerFactory.getLogger(WeiXinImgUtil.class);

    public static Font getFont(int fontFormat,float size) {
        String pathString = FontUtil.class.getClassLoader().getResource("SIMSUN.TTC").getFile();
        try {
            Font dynamicFont = Font.createFont(fontFormat, new File(pathString));
            dynamicFont = dynamicFont.deriveFont(size);
            return dynamicFont;
        } catch (FontFormatException ex) {
            logger.info("字体【{}】FontFormatException==》"+ ex);
        } catch (IOException ex) {

            logger.info( "IOException===》"+ex);
        }

        return null;
    }


}
