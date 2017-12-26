package com.bocom.test;

import java.awt.image.ImageProducer;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

public class PictureUtil {

	/**
	 * 获取文件夹内所有的文件名
	 * 
	 * @param pathName
	 */
	public static ArrayList<String> getFileName(String pathName) {
		// 存放遍历出来的文件名字
		ArrayList<String> nameList = new ArrayList<String>();
		File dirFile = new File(pathName);
		String[] fileList = dirFile.list();
		String string;
		File file;
		for (int i = 0; i < fileList.length; i++) {
			string = fileList[i];
			file = new File(dirFile.getPath(), string);
			nameList.add(file.getName());
		}
		return nameList;
	}

	/**
	 * 获取图片格式函数
	 * 
	 * @param file
	 * @return
	 */
	public static String getExtension(File file) {
		String format = "";
		ImageInputStream iis = null;
		try {
			iis = ImageIO.createImageInputStream(file);
			Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);
			if (iter.hasNext()) {

				format = iter.next().getFormatName();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (iis != null) {
				try {
					iis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return format;
	}

	/**
	 * 判断是否为图片函数
	 * 
	 * @param file
	 * @return
	 */
	public static boolean isImage(File resFile) {
		ImageInputStream iis = null;
		try {
			iis = ImageIO.createImageInputStream(resFile);
			Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);
			// 文件是不是图片
			if (iter.hasNext()) {
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (iis != null) {
				try {
					iis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	/**
	 * 将图片格式转换为JPG格式
	 * 
	 * @param input
	 * @param output
	 * @throws IOException
	 */
	public static void forJpg(String input, String output) throws IOException {
//		try {
//			JPGOptions options = new JPGOptions();
//			options.setQuality(72);
//			ImageProducer image = Jimi.getImageProducer(input);
//			JimiWriter writer = Jimi.createJimiWriter(output);
//			writer.setSource(image);
//			writer.setOptions(options);
//			writer.putImage(output);
//			// 转换后删除原文件
//			File f = new File(input);
//			f.delete();
//		} catch (JimiException e) {
//			System.err.println("Error: " + e);
//			e.printStackTrace();
//		}

	}

}
