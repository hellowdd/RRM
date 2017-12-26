package com.bocom.test;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import net.coobird.thumbnailator.Thumbnails;

public class TestT {

	 
	/** 水印透明度 */
	private static float alpha = 0.5f;
	/** 水印图片旋转角度 */
	private static double degree = 0f;
	private static int interval = 0;
	 
	public static void main(String[] args) throws Exception {
		/*// 图片旋转
		Thumbnails.of("H:/testsssss/4.png").scale(1f).rotate(90)
				.toFile("H:/testsssss/44.png");*/
		// Thumbnails.of("H:/testsssss/4.png").scale(1f).outputFormat("png").toFile("H:/testsssss/44.png");
		// Thumbnails.of("H:/testsssss/4.png").scale(1f).watermark(
		// Positions.TOP_LEFT,
		// Thumbnails.of("H:/testsssss/2.png").size(316, 117).asBufferedImage(),
		// 0.5f)
		// .outputQuality(1f).toFile("H:/testsssss/44.png");
		
		/*waterMarkByImg("H:/testsssss/2.png","H:/testsssss/5.jpeg","H:/testsssss/55.png",-30f);*/
		
//		Thumbnails.of(createImage("警务云", new Font("Serif", Font.BOLD, 11), 100, 100)).scale(1f).toFile("H:/testsssss/44.png");
//		waterMarkByImg(createImage("警务云", new Font("Serif", Font.BOLD, 11), 100, 100),"H:/testsssss/5.jpeg","H:/testsssss/55.png",0f);
		WriteFontOnImage("H:/testsssss/5.jpeg","警务云", new Font("Serif", Font.BOLD, 40), 0.1f, Color.WHITE);
	}

	public static void waterMarkByImg(BufferedImage waterImgPath, String srcImgPath,
			String targerPath, double degree) throws Exception {
		OutputStream os = null;
		try {
			Image srcImg = ImageIO.read(new File(srcImgPath));

			BufferedImage buffImg = new BufferedImage(srcImg.getWidth(null), srcImg.getHeight(null), BufferedImage.TYPE_INT_RGB);

			// 1、得到画笔对象
			Graphics2D g = buffImg.createGraphics();

			// 2、设置对线段的锯齿状边缘处理
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g.drawImage(srcImg.getScaledInstance(srcImg.getWidth(null), srcImg.getHeight(null), Image.SCALE_SMOOTH), 0, 0, null);
			// 3、设置水印旋转
			if (0 != degree) {
				g.rotate(Math.toRadians(degree), (double) buffImg.getWidth() / 2, (double) buffImg.getHeight() / 2);
			}

			// 4、水印图片的路径 水印图片一般为gif或者png的，这样可设置透明度
			ImageIcon imgIcon = new ImageIcon(waterImgPath);

			// 5、得到Image对象。
			Image img = imgIcon.getImage();

			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));

			// 6、水印图片的位置
			for (int height = interval + imgIcon.getIconHeight(); height < buffImg.getHeight(); height = height + interval + imgIcon.getIconHeight()) {
				for (int weight = interval + imgIcon.getIconWidth(); weight < buffImg
						.getWidth(); weight = weight + interval
						+ imgIcon.getIconWidth()) {
					g.drawImage(img, weight - imgIcon.getIconWidth(), height
							- imgIcon.getIconHeight(), null);
				}
			}
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
			// 7、释放资源
			g.dispose();

			// 8、生成图片
			os = new FileOutputStream(targerPath);
			ImageIO.write(buffImg, "JPG", os);

			System.out.println("图片完成添加水印图片");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != os)
					os.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
    private static BufferedImage createImage(String content,Font font,Integer width,Integer height){    
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);     
        Graphics2D g2 = (Graphics2D)bi.getGraphics();     
        g2.setBackground(Color.WHITE);     
        g2.clearRect(0, 0, width, height);     
        g2.setPaint(Color.BLACK);     
             
        FontRenderContext context = g2.getFontRenderContext();     
        Rectangle2D bounds = font.getStringBounds(content, context);     
        double x = (width - bounds.getWidth()) / 2;     
        double y = (height - bounds.getHeight()) / 2;     
        double ascent = -bounds.getY();     
        double baseY = y + ascent;     
             
        g2.drawString(content, (int)x, (int)baseY);    
          
        return bi;  
 }  
    
    

    private static void WriteFontOnImage(String srcPath, String content,Font font, float alpha, Color color) throws IOException{    
		BufferedImage bi = ImageIO.read(new File(srcPath));
        Graphics2D g2 = (Graphics2D)bi.getGraphics();     
//        g2.setPaint(Color.RED);    
        
        
        GlyphVector fontGV = font.createGlyphVector(g2.getFontRenderContext(), content);
        Rectangle size = fontGV.getPixelBounds(g2.getFontRenderContext(), 0, 0);
		double textWidth = size.getWidth();
		double textHeight = size.getHeight();
		Shape textShape = fontGV.getOutline();
        AffineTransform rotate45 = AffineTransform.getRotateInstance(Math.PI / 4d);
		Shape rotatedText = rotate45.createTransformedShape(textShape);
		g2.setPaint(new GradientPaint(0, 0, color, bi.getWidth() / 2, bi.getHeight() / 2, Color.RED));
		g2.setStroke(new BasicStroke(alpha));
		double yStep = Math.sqrt(textWidth * textWidth / 2) + 5;
		for (double x = interval + textHeight* 3; x < bi.getWidth();  x += (textHeight * 3)) {
			double y = -yStep;
			for (; y < bi.getHeight(); y += yStep) {
				g2.draw(rotatedText);
				g2.translate(0, yStep);
			}
			g2.translate(textHeight * 3, -(y + yStep));
	}
		
		
//		for (int h = interval + height; h < bi.getHeight(); h = h + interval + height) {
//			for (int weight = interval + width; weight < bi.getWidth(); weight = weight + interval + width) {
//				g2.drawString(content, weight - width, h - height);
//			}
//		}
        
          
        Thumbnails.of(bi).scale(1f).toFile("H:/testsssss/44.jpeg");
    }
    
    
    
    

}
