package com.bocom.test;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;

public class tt {

	public static void main(String[] args) {
		pressText("警务云", "H:/testsssss/55.jpeg", "Serif", "#dd0017", 20, 0.9f, true) ;

	}

	/**
	 * 斜水印,重复水印,文字
	 * 
	 * @param pressText
	 *            文字
	 * @param targetImg
	 *            目标图片
	 * @param fontName
	 *            字体名称
	 * @param colorStr
	 *            字体颜色字符串，格式如：#29944f
	 * @param fontSize
	 *            字体大小
	 * @param alpha
	 *            透明度(0.1-0.9)
	 * @param carelessness
	 *            true为字体实心,false为字体空心
	 * @return
	 */
	public static BufferedImage pressText(String pressText, String targetImg,
			String fontName, String colorStr, int fontSize, float alpha,
			boolean carelessness) {
		try {

			File file = new File(targetImg);

			Image src = ImageIO.read(file);

			// 图片宽度
			int width = src.getWidth(null);
			// 图片高度
			int height = src.getHeight(null);

			BufferedImage image = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);

			Graphics2D g2d = image.createGraphics();
			// 绘原图
			g2d.drawImage(src, 0, 0, width, height, null);
			// 比例
			g2d.scale(1, 1);

			g2d.addRenderingHints(new RenderingHints(
					RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON));
			g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
					RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

			// 颜色
			Color color = Color.decode(colorStr);

			// 字体
			Font font = new Font(fontName, Font.PLAIN, fontSize);

			GlyphVector fontGV = font.createGlyphVector(
					g2d.getFontRenderContext(), pressText);
			Rectangle size = fontGV.getPixelBounds(g2d.getFontRenderContext(),
					0, 0);
			Shape textShape = fontGV.getOutline();
			double textWidth = size.getWidth();
			double textHeight = size.getHeight();
			AffineTransform rotate45 = AffineTransform
					.getRotateInstance(Math.PI / 4d);
			Shape rotatedText = rotate45.createTransformedShape(textShape);
			// use a gradient that repeats 4 times
			g2d.setPaint(new GradientPaint(0, 0, color, image.getWidth() / 2,
					image.getHeight() / 2, color));

			// 透明度
			g2d.setStroke(new BasicStroke(alpha));

			// step in y direction is calc'ed using pythagoras + 5 pixel padding
			double yStep = Math.sqrt(textWidth * textWidth / 2) + 5;
			// step over image rendering watermark text
			for (double x = -textHeight * 3; x < image.getWidth(); x += (textHeight * 3)) {
				double y = -yStep;
				for (; y < image.getHeight(); y += yStep) {
					g2d.draw(rotatedText);
					if (carelessness)// 字体实心
					{
						g2d.fill(rotatedText);
					}
					g2d.translate(0, yStep);
				}
				g2d.translate(textHeight * 3, -(y + yStep));
			}

			g2d.dispose();

			return image;
		} catch (IIOException e) {
			// logger.warn(e);
		} catch (Exception e) {
			// logger.warn(e);
		}
		return null;
	}
}
