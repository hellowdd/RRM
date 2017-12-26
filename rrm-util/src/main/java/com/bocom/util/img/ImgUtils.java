package com.bocom.util.img;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.net.ssl.HttpsURLConnection;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.geom.AffineTransform;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;


/**
 * img处理工具类
 *
 * @author QY
 */
public class ImgUtils {


    private final static Logger log = LoggerFactory.getLogger(ImgUtils.class);


    public static String IMAGE_TYPE_GIF = "gif";// 图形交换格式
    public static String IMAGE_TYPE_JPG = "jpg";// 联合照片专家组
    public static String IMAGE_TYPE_JPEG = "jpeg";// 联合照片专家组
    public static String IMAGE_TYPE_BMP = "bmp";// 英文Bitmap（位图）的简写，它是Windows操作系统中的标准图像文件格式
    public static String IMAGE_TYPE_PNG = "png";// 可移植网络图形
    public static String IMAGE_TYPE_PSD = "psd";// Photoshop的专用格式Photoshop


    public static void main(String[] args) throws IOException {
        // 1-缩放图像：
        // 方法一：按比例缩放
//        ImgUtils.scale("E:\\img\\shuai.jpg", "E:\\img\\test\\abc_scale.jpg", 2, true);//测试OK
        // 方法二：按高度和宽度缩放

//        ImgUtils.scaleByWH("E:\\img\\shuai.jpg", "E:\\img\\test\\abc_scale.jpg", 500, 300, false);//测试OK

        // 2-切割图像：
        // 方法一：按指定起点坐标和宽高切割
//        ImgUtils.cutByXYHW("E:\\img\\shuai.jpg", "E:\\img\\test\\abc_cut.jpg", 0, 0, 400, 400);//测试OK
        // 方法二：指定切片的行数和列数
//        ImgUtils.cutBYRowsCols("E:\\img\\shuai.jpg", "E:\\img\\test\\", 2, 2);//测试OK
        // 方法三：指定切片的宽度和高度
//        ImgUtils.cutByHW("E:\\img\\shuai.jpg", "E:\\img\\test\\", 300, 300);//测试OK

        // 3-图像类型转换：
//        ImgUtils.convert("E:\\img\\q.png", "jpg", "E:\\img\\test\\q.jpg");//测试OK
//        ImgUtils.convert("E:\\img\\q.png", "gif", "E:\\img\\test\\q.gif");//测试OK
//        ImgUtils.convert("E:\\img\\test\\q.gif", "jpg", "E:\\img\\test\\q2.jpg");//测试OK

        // 4-彩色转黑白：
//        ImgUtils.gray("E:\\img\\shuai.jpg", "E:\\img\\test\\abc_gray.jpg");//测试OK

        // 5-给图片添加文字水印：
        // 方法一：
//        ImgUtils.pressText("我是水印文字", "E:\\img\\shuai.jpg", "E:\\img\\test\\abc_pressText.jpg", "宋体", Font.BOLD, Color
//                        .white, 80, 0, 0,
//                0.5f);//测试OK

        // 6-给图片添加图片水印：
//        ImgUtils.pressImage("E:\\img\\shuiying.png", "E:\\img\\shuai.jpg", "E:\\img\\test\\abc_pressImage.jpg", 0, 0,
//                0.5f);//测试OK
    }


    /**
     * 根据图片的路径  返回相应的输入流
     *
     * @param imgPath
     * @return InputStream
     * @throws IOException
     */
    public final static InputStream getISImg(String imgPath) throws IOException {

        URL http;
        if (imgPath.trim().startsWith("https")) {
            http = new URL(imgPath);
            HttpsURLConnection conn = (HttpsURLConnection) http.openConnection();
            conn.setRequestMethod("GET");
        } else if (imgPath.trim().startsWith("http")) {
            http = new URL(imgPath);
            HttpURLConnection conn = (HttpURLConnection) http.openConnection();
            conn.setRequestMethod("GET");
        } else {
            http = new File(imgPath).toURI().toURL();
        }
        return http.openStream();
    }

    /**
     * 获取图片像素
     */
    public final static String getXSImg(String srcImageFile) throws IOException {
        InputStream imgIS = getISImg(srcImageFile);
        BufferedImage bi = ImageIO.read(imgIS);
        return bi.getWidth() + "x" + bi.getHeight();
    }

    /**
     * 按比例缩放
     *
     * @param srcImageFile
     * @param result
     * @param scale
     * @param flag
     */
    public final static void scale(String srcImageFile, String result, int scale, boolean flag) throws IOException {
        InputStream imgIS = getISImg(srcImageFile);
        BufferedImage src = ImageIO.read(imgIS); // 读入文件
        int width = src.getWidth(); // 得到源图宽
        int height = src.getHeight(); // 得到源图长
        if (flag) {// 放大
            width = width * scale;
            height = height * scale;
        } else {// 缩小
            width = width / scale;
            height = height / scale;
        }
        Image image = src.getScaledInstance(width, height,
                Image.SCALE_DEFAULT);
        BufferedImage tag = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        Graphics g = tag.getGraphics();
        g.drawImage(image, 0, 0, null); // 绘制缩小后的图
        g.dispose();
        ImageIO.write(tag, "JPEG", new File(result));// 输出到文件流
    }

//
//    /**
//     * 对图片进行放大
//     *
//     * @param originalImage 原始图片
//     * @param times         放大倍数
//     * @return
//     */
//    public static BufferedImage zoomInImage(BufferedImage originalImage, Integer width, Integer height) {
//        BufferedImage newImage = new BufferedImage(width, height, originalImage.getType());
//        Graphics g = newImage.getGraphics();
//        g.drawImage(originalImage, 0, 0, height, width, null);
//        g.dispose();
//        return newImage;
//    }


    /**
     * 按高度和宽度缩放
     *
     * @param srcImageFile
     * @param result
     * @param height
     * @param width
     * @param bb
     */
    public final static void scaleByWH(String srcImageFile, String result, int height, int width, boolean bb) throws
            IOException {
        Image img = ImageIO.read(getISImg(srcImageFile));
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = bi.getGraphics();
        g.drawImage(img, 0, 0, width, height, Color.LIGHT_GRAY, null);
        g.dispose();
        // 将图片保存在原目录并加上前缀
        ImageIO.write(bi, "JPEG", new File(result));
    }


    /**
     * 按指定起点坐标和宽高切割
     *
     * @param srcImageFile
     * @param result
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public final static void cutByXYHW(String srcImageFile, String result,
                                       int x, int y, int width, int height) throws IOException {
        // 读取源图像
        InputStream imgIS = getISImg(srcImageFile);
        BufferedImage bi = ImageIO.read(imgIS);
        int srcWidth = bi.getHeight(); // 源图宽度
        int srcHeight = bi.getWidth(); // 源图高度
        if (srcWidth > 0 && srcHeight > 0) {
            Image image = bi.getScaledInstance(srcWidth, srcHeight,
                    Image.SCALE_DEFAULT);
            // 四个参数分别为图像起点坐标和宽高
            // 即: CropImageFilter(int x,int y,int width,int height)
            ImageFilter cropFilter = new CropImageFilter(x, y, width, height);
            Image img = Toolkit.getDefaultToolkit().createImage(
                    new FilteredImageSource(image.getSource(),
                            cropFilter));
            BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            g.drawImage(img, 0, 0, width, height, null); // 绘制切割后的图
            g.dispose();
            // 输出为文件
            ImageIO.write(tag, "JPEG", new File(result));
        }
    }

    /**
     * 指定切片的行数和列数
     *
     * @param srcImageFile
     * @param descDir
     * @param rows
     * @param cols
     */
    public final static void cutBYRowsCols(String srcImageFile, String descDir,
                                           int rows, int cols) throws IOException {
        if (rows <= 0 || rows > 20) rows = 2; // 切片行数
        if (cols <= 0 || cols > 20) cols = 2; // 切片列数
        // 读取源图像
        InputStream imgIS = getISImg(srcImageFile);
        BufferedImage bi = ImageIO.read(imgIS);
        int srcWidth = bi.getHeight(); // 源图宽度
        int srcHeight = bi.getWidth(); // 源图高度
        if (srcWidth > 0 && srcHeight > 0) {
            Image img;
            ImageFilter cropFilter;
            Image image = bi.getScaledInstance(srcWidth, srcHeight, Image.SCALE_DEFAULT);
            int destWidth = srcWidth; // 每张切片的宽度
            int destHeight = srcHeight; // 每张切片的高度
            // 计算切片的宽度和高度
            if (srcWidth % cols == 0) {
                destWidth = srcWidth / cols;
            } else {
                destWidth = (int) Math.floor(srcWidth / cols) + 1;
            }
            if (srcHeight % rows == 0) {
                destHeight = srcHeight / rows;
            } else {
                destHeight = (int) Math.floor(srcWidth / rows) + 1;
            }
            // 循环建立切片
            // 改进的想法:是否可用多线程加快切割速度
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    // 四个参数分别为图像起点坐标和宽高
                    // 即: CropImageFilter(int x,int y,int width,int height)
                    cropFilter = new CropImageFilter(j * destWidth, i * destHeight,
                            destWidth, destHeight);
                    img = Toolkit.getDefaultToolkit().createImage(
                            new FilteredImageSource(image.getSource(),
                                    cropFilter));
                    BufferedImage tag = new BufferedImage(destWidth,
                            destHeight, BufferedImage.TYPE_INT_RGB);
                    Graphics g = tag.getGraphics();
                    g.drawImage(img, 0, 0, null); // 绘制缩小后的图
                    g.dispose();
                    // 输出为文件
                    ImageIO.write(tag, "JPEG", new File(descDir
                            + "_r" + i + "_c" + j + ".jpg"));
                }
            }
        }
    }


    /**
     * 指定切片的宽度和高度
     *
     * @param srcImageFile
     * @param descDir
     * @param destWidth
     * @param destHeight
     */
    public final static void cutByHW(String srcImageFile, String descDir,
                                     int destWidth, int destHeight) throws IOException {
        if (destWidth <= 0) destWidth = 200; // 切片宽度
        if (destHeight <= 0) destHeight = 150; // 切片高度
        // 读取源图像
        InputStream imgIS = getISImg(srcImageFile);
        BufferedImage bi = ImageIO.read(imgIS);
        int srcWidth = bi.getHeight(); // 源图宽度
        int srcHeight = bi.getWidth(); // 源图高度
        if (srcWidth > destWidth && srcHeight > destHeight) {
            Image img;
            ImageFilter cropFilter;
            Image image = bi.getScaledInstance(srcWidth, srcHeight, Image.SCALE_DEFAULT);
            int cols = 0; // 切片横向数量
            int rows = 0; // 切片纵向数量
            // 计算切片的横向和纵向数量
            if (srcWidth % destWidth == 0) {
                cols = srcWidth / destWidth;
            } else {
                cols = (int) Math.floor(srcWidth / destWidth) + 1;
            }
            if (srcHeight % destHeight == 0) {
                rows = srcHeight / destHeight;
            } else {
                rows = (int) Math.floor(srcHeight / destHeight) + 1;
            }
            // 循环建立切片
            // 改进的想法:是否可用多线程加快切割速度
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    // 四个参数分别为图像起点坐标和宽高
                    // 即: CropImageFilter(int x,int y,int width,int height)
                    cropFilter = new CropImageFilter(j * destWidth, i * destHeight,
                            destWidth, destHeight);
                    img = Toolkit.getDefaultToolkit().createImage(
                            new FilteredImageSource(image.getSource(),
                                    cropFilter));
                    BufferedImage tag = new BufferedImage(destWidth,
                            destHeight, BufferedImage.TYPE_INT_RGB);
                    Graphics g = tag.getGraphics();
                    g.drawImage(img, 0, 0, null); // 绘制缩小后的图
                    g.dispose();
                    // 输出为文件
                    ImageIO.write(tag, "JPEG", new File(descDir
                            + "_r" + i + "_c" + j + ".jpg"));
                }
            }
        }
    }


    /**
     * 图像类型转换
     *
     * @param srcImageFile  源文件
     * @param formatName    转换的类型名称
     * @param destImageFile 目标文件路径以及文件名称
     */
    public final static void convert(String srcImageFile, String formatName, String destImageFile) throws IOException {
        InputStream imgIS = getISImg(srcImageFile);
        BufferedImage src = ImageIO.read(imgIS);
        ImageIO.write(src, formatName, new File(destImageFile));
    }


    /**
     * 彩色转黑白
     *
     * @param srcImageFile
     * @param destImageFile
     */
    public final static void gray(String srcImageFile, String destImageFile) throws IOException {
        InputStream imgIS = getISImg(srcImageFile);
        BufferedImage src = ImageIO.read(imgIS);
        ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
        ColorConvertOp op = new ColorConvertOp(cs, null);
        src = op.filter(src, null);
        ImageIO.write(src, "JPEG", new File(destImageFile));
    }


    /**
     * 给图片添加文字水印
     *
     * @param pressText
     * @param srcImageFile
     * @param destImageFile
     * @param fontName
     * @param fontStyle
     * @param color
     * @param fontSize
     * @param x
     * @param y
     * @param alpha
     */
    public final static void pressText(String pressText,
                                       String srcImageFile, String destImageFile, String fontName,
                                       int fontStyle, Color color, int fontSize, int x,
                                       int y, float alpha) throws IOException {
        InputStream imgIS = getISImg(srcImageFile);
        Image src = ImageIO.read(imgIS);
        int width = src.getWidth(null);
        int height = src.getHeight(null);
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        g.drawImage(src, 0, 0, width, height, null);
        g.setColor(color);
        g.setFont(new Font(fontName, fontStyle, fontSize));
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
                alpha));
        // 在指定坐标绘制水印文字
        g.drawString(pressText, (width - (getLength(pressText) * fontSize))
                / 2 + x, (height - fontSize) / 2 + y);
        g.dispose();
        ImageIO.write((BufferedImage) image, "JPEG", new File(destImageFile));// 输出到文件流
    }


    /**
     * 给图片添加图片水印
     *
     * @param pressImg
     * @param srcImageFile
     * @param destImageFile
     * @param x
     * @param y
     * @param alpha
     */
    public final static void pressImage(String pressImg, String srcImageFile, String destImageFile,
                                        int x, int y, float alpha) throws IOException {
        InputStream imgIS = getISImg(srcImageFile);
        Image src = ImageIO.read(imgIS);
        int wideth = src.getWidth(null);
        int height = src.getHeight(null);
        BufferedImage image = new BufferedImage(wideth, height,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        g.drawImage(src, 0, 0, wideth, height, null);
        // 水印文件
        Image src_biao = ImageIO.read(new File(pressImg));
        int wideth_biao = src_biao.getWidth(null);
        int height_biao = src_biao.getHeight(null);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
                alpha));
        g.drawImage(src_biao, (wideth - wideth_biao) / 2,
                (height - height_biao) / 2, wideth_biao, height_biao, null);
        // 水印文件结束
        g.dispose();
        ImageIO.write((BufferedImage) image, "JPEG", new File(destImageFile));
    }

    private final static int getLength(String text) {
        int length = 0;
        for (int i = 0; i < text.length(); i++) {
            if (new String(text.charAt(i) + "").getBytes().length > 1) {
                length += 2;
            } else {
                length += 1;
            }
        }
        return length / 2;
    }

}
