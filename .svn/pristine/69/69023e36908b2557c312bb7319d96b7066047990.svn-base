package com.bocom.util.img;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import com.gif4j.GifDecoder;
import com.gif4j.GifEncoder;
import com.gif4j.GifImage;
import com.gif4j.GifTransformer;
import com.gif4j.TextPainter;
import com.gif4j.Watermark;


public class GifUtil {

    private GifUtil() {

    }

    public static void makeGif(File src, File dest, int width, int height)

            throws IOException {

        GifImage gifImage = GifDecoder.decode(src);// 创建一个GifImage对象.

        GifImage resizeIMG = GifTransformer.resize(gifImage, width, height,

                true);

        GifEncoder.encode(resizeIMG, dest);

    }


    public static void makeGif(String src, String dest, int width, int height)

            throws IOException {

        GifImage gifImage = GifDecoder.decode(new File(src));// 创建一个GifImage对象.

        makeGif(new File(src), new File(dest), gifImage.getScreenWidth() / 2,

                gifImage.getScreenHeight() / 2);

    }


    public static void makeGif(File src, File dest) throws IOException {

        GifImage gifImage = GifDecoder.decode(src);// 创建一个GifImage对象.

        makeGif(src, dest, gifImage.getScreenWidth() / 2, gifImage

                .getScreenHeight() / 2);

    }


    public static void makeGif(String src, String dest) throws IOException {

        makeGif(new File(src), new File(dest));

    }


    public static void addTextWatermarkToGif(File src, String watermarkText, File dest) throws IOException {
        //图片对象
        GifImage gf = GifDecoder.decode(src);
        addTextWatermarkToGif(watermarkText, dest, gf);
    }

    public static void addTextWatermarkToGif(InputStream src, String watermarkText, String dest) throws IOException {

        //图片对象
        GifImage gf = GifDecoder.decode(src);
        addTextWatermarkToGif(watermarkText, new File(dest), gf);
    }

    private static void addTextWatermarkToGif(String watermarkText, File dest, GifImage gf) throws IOException {
        //水印初始化、设置（字体、样式、大小、颜色）

        TextPainter textPainter = new TextPainter(new Font("黑体", Font.ITALIC, 24));

        textPainter.setOutlinePaint(Color.RED);

        BufferedImage renderedWatermarkText = textPainter.renderString(watermarkText, true);

        //获取图片大小
        int iw = gf.getScreenWidth();
        int ih = gf.getScreenHeight();


        //获取水印大小
        int tw = renderedWatermarkText.getWidth();
        int th = renderedWatermarkText.getHeight();


        //水印位置
        Point p = new Point();
        p.x = iw - tw - 5;
        p.y = ih - th - 4;


        //加水印
        Watermark watermark = new Watermark(renderedWatermarkText, p);
        gf = watermark.apply(gf, true);

        //输出
        GifEncoder.encode(gf, dest);
    }

    public static void main(String[] arg) throws Exception {

        String path = "F:\\11.gif";
        System.out.println(path + "\\ok\\");
        GifUtil.addTextWatermarkToGif(new File(path), "天真", new File("F:\\test\\test.gif"));

    }

}