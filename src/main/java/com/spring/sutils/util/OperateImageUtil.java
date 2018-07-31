package com.spring.sutils.util;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;

/**
 * @Auther :huiqiang
 * @Description :
 * @Date: Create in 下午5:18 2018/7/27 2018
 * @Modify:
 */
public class OperateImageUtil {

    private static final String CHARSET = "utf-8";
    private static final String FORMAT_NAME = "JPG";
    // 二维码尺寸
    private static final int QRCODE_SIZE = 370;
    // LOGO宽度
    private static final int WIDTH = 50;
    // LOGO高度
    private static final int HEIGHT = 50;

/**
 *
 *@描述创建一个二维码

 *@参数 [source, content, format, imgPath, needCompress]

 *@返回值 java.awt.image.BufferedImage

 *@创建人 慧强

 *@创建时间 2018/7/28

 *@修改人和其它信息

 */
    public  static BufferedImage createQrcCodeImage(String content, InputStream imgPath, boolean needCompress) throws WriterException, IOException {

        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        // 内容所使用字符集编码
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        // 设置图片的最大值
//      hints.put(EncodeHintType.MAX_SIZE, 350);
        // 设置图片的最小值
//      hints.put(EncodeHintType.MIN_SIZE, 100);
        // 设置二维码边的空度，非负数
        hints.put(EncodeHintType.MARGIN, 1);
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        //创建比特矩阵(位矩阵)的QR码编码的字符串
        BitMatrix byteMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE, hints);
        // 使BufferedImage勾画QRCode  (matrixWidth 是行二维码像素点)
        int matrixWidth = byteMatrix.getWidth();
        BufferedImage image = new BufferedImage(matrixWidth, matrixWidth, BufferedImage.TYPE_INT_RGB);

/*
        Graphics2D graphics =image.createGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, matrixWidth, matrixWidth);
        for (int i = 0; i < matrixWidth; i++){
            for (int j = 0; j < matrixWidth; j++){
                if (byteMatrix.get(i, j)){
                    graphics.fillRect(i-100, j-100, 1, 1);
                }
            }

 }*/

        int width = byteMatrix.getWidth();
        int height = byteMatrix.getHeight();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                //black  white
                image.setRGB(x, y, byteMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        if (imgPath == null || "".equals(imgPath)) {
            return image;
        }
        OperateImageUtil.insertImage(image, imgPath, needCompress);
        return image;


    }

/**
 *
 *@描述插入一个LOGO照片

 *@参数 [source, imgPath, needCompress]

 *@返回值 void

 *@创建人 慧强

 *@创建时间 2018/7/28

 *@修改人和其它信息

 */
    private static void insertImage(BufferedImage source, InputStream imgPath, boolean needCompress) throws IOException {

//        File file = new File(imgPath);
//        if (!file.exists()) {
//            System.err.println("" + imgPath + "   该文件不存在！");
//            return;
//        }
        Image src = ImageIO.read(imgPath);
        int width = src.getWidth(null);
        int height = src.getHeight(null);

        // 是否压缩LOGO
        if (needCompress) {
            if (width > WIDTH) {
                width = WIDTH;
            }
            if (height > HEIGHT) {
                height = HEIGHT;
            }
            //获取指定宽高缩放的图片
            Image image = src.getScaledInstance(width, height, Image.SCALE_AREA_AVERAGING);
            BufferedImage te = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics2D = te.createGraphics();
            graphics2D.drawImage(image, 0, 0, null);
            graphics2D.dispose();
            src = image;
        }
        //插入LOGO
        Graphics2D graphics2D = source.createGraphics();
        int x = (QRCODE_SIZE - width) / 2;
        int y = (QRCODE_SIZE - height) / 2;
        graphics2D.drawImage(src, x, y, width, height, null);
        Shape shape = new RoundRectangle2D.Float(x, y, width, height, 6, 6);
        graphics2D.setStroke(new BasicStroke(3f));
        graphics2D.draw(shape);
        graphics2D.dispose();


    }

    public static void mkdirs(String destPath) {
        File file = new File(destPath);
        // 当文件夹不存在时，mkdirs会自动创建多层目录，区别于mkdir。(mkdir如果父目录不存在则会抛出异常)
        if (!file.exists() && !file.isDirectory()) {
            file.mkdirs();
        }
    }

    public static String decode(File file) throws Exception {
        BufferedImage image;
        image = ImageIO.read(file);
        if (image == null) {
            return null;
        }
        BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(image);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        Result result;
        Hashtable hints = new Hashtable();
        hints.put(DecodeHintType.CHARACTER_SET, CHARSET);
        result = new MultiFormatReader().decode(bitmap, hints);
        String resultStr = result.getText();
        return resultStr;
    }

    /**
     * 读二维码并输出携带的信息
     */
    public static void readQrCode(InputStream inputStream) throws IOException {
        //从输入流中获取字符串信息
        BufferedImage image = ImageIO.read(inputStream);
        //将图像转换为二进制位图源
        LuminanceSource source = new BufferedImageLuminanceSource(image);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        QRCodeReader reader = new QRCodeReader();
        Result result = null;
        try {
            result = reader.decode(bitmap);
        } catch (ReaderException e) {
            e.printStackTrace();
        }
        System.out.println(result.getText());
    }



    /**
     *
     *@描述 二维码进行解析

     *@参数 [path]

     *@返回值 java.lang.String

     *@创建人 慧强

     *@创建时间 2018/7/28

     *@修改人和其它信息

     */
    public static String decode(String path) throws Exception {
        return OperateImageUtil.decode(new File(path));
    }
    /**
     *
     *@描述二维码进行解析 qrc

     *@参数 [path]

     *@返回值 void

     *@创建人 慧强

     *@创建时间 2018/7/28

     *@修改人和其它信息

     */
    public static void decodeQRC(String path) throws Exception {
         File file =new File(path);
         OperateImageUtil.readQrCode(new FileInputStream(file));
    }
    /**
     *
     *@描述
     *@参数 [source, target] 【底图  二维码】
     *@返回值 java.awt.image.BufferedImage
     *@创建人 慧强
     *@创建时间 2018/7/30
     *@修改人和其它信息

     */
    public  static  BufferedImage superposeImg(BufferedImage source,BufferedImage target,int x,int y){

         int winth=target.getWidth();
         int height=target.getHeight();
         Graphics2D  graphics2D= source.createGraphics();
         graphics2D.drawImage(target,x,y,winth,height,null);
         graphics2D.dispose();
         return  source;
    }

    public static BufferedImage composeImg(BufferedImage source,BufferedImage target) {
        int winth=target.getWidth();
        int height=target.getHeight();
        int winth2=source.getWidth();
        int height2=source.getHeight();
        BufferedImage  bufferedImage= new BufferedImage(winth,height+height2,BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D= bufferedImage.createGraphics();
        graphics2D.drawImage(target,0,0,winth,height,null);
        graphics2D.drawImage(source,0,height,winth2,height2,null);
        graphics2D.dispose();
         //将绘制的图像生成至输出流
         // 一JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
         // 二 encoder.encode(tag);
        return bufferedImage;

    }

    public static BufferedImage setwriteFontAndHeadImg( String name,String msg, BufferedImage resultTempImg,BufferedImage headImg) {
       Graphics2D graphics2D = resultTempImg.createGraphics();
       graphics2D.drawImage(headImg,20,30,80,80,null);
          Font f = new Font("PingFangSC-Regular", Font.PLAIN, 25);
           Color mycolor = Color.white;
           graphics2D.setColor(mycolor);
           graphics2D.setFont(f);
            graphics2D.drawString(name,130,50);
            graphics2D.drawString(msg,130,84);
            graphics2D.dispose();

            return resultTempImg;
    }
}
