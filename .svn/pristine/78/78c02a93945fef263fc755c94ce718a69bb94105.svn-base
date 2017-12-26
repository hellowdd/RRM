//package com.bocom.test;
//
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.Iterator;
//import java.util.List;
//import java.util.UUID;
//
//import javax.imageio.ImageIO;
//import javax.imageio.ImageReader;
//import javax.imageio.stream.ImageInputStream;
//
//import org.apache.commons.collections.CollectionUtils;
//
//import com.bocom.dto.img.ImageDto;
//
//public class ImageFormat {
//	
//	
//	static HashSet<String> houzhui = new HashSet<String>(){{add("JPEG");add("PNG");add("BMP");add("WBMP");add("GIF");}};
//	
//	public static void main(String[] args) {
////		  File file = new File("H:/testsssss/6.jpg");
////	        changFormat(file, "PNG", new File("H:/testsssss/6-1.png"));// 转为png
////	        changFormat(file, "bmp", new File("H:/testsssss/4.bmp"));// 转为bmp
////	        changFormat(file, "jpeg", new File("H:/testsssss/4.jpg"));// 转为jpg
////	        changFormat(file, "gif", new File("H:/testsssss/4.gif"));// 转为gif
//	
//		
//		
//		String dirFilePath = "H:/testsssss";
//		String format = "png";
//		
//
//		String writeFilePath = dirFilePath.concat(File.separator).concat(UUID.randomUUID().toString());
//		//获取路径下所有文件和格式
//		String [] files = getFile(dirFilePath);
//		//针对文件进行分析
//		List<ImageDto> imageList= handleFile(files, dirFilePath, format, writeFilePath);
//		if(CollectionUtils.isNotEmpty(imageList)){
//			File fileTemp = new File(writeFilePath);
//			if (fileTemp.mkdirs()) {
//				for (ImageDto imageDto : imageList) {
//					changFormat(imageDto.getReadFile(), format ,imageDto.getWriteFile());	
//				}
//			}else{
//				throw new RuntimeException("Cannot mkdir file : " + fileTemp.getName());
//			}
//		}
//		
//		
////		System.out.println(JsonUtil.toJSon(imageList));
//		
//	}
//	
//    //第一个参数  原图的File对象  第二个参数  目标格式   第三个参数   输出图像的File对象
//    public static void changFormat(File srcFile, String format, File formatFile) {
//        try {
//            BufferedImage srcImg = ImageIO.read(srcFile);// 读取原图
//            ImageIO.write(srcImg, format, formatFile);// 用指定格式输出到指定文件
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//    
//	/**
//	 * 获取文件夹内所有的文件
//	 * @param pathName
//	 */
//	public static String[] getFile (String pathName) {
//		File dirFile = new File(pathName);
//		if(dirFile.isDirectory()){
//			return dirFile.list();
//		}
//		return null;
//	}
//	
//	/**
//	 * 判断是否标准的输入输出
//	 * @param format
//	 * @return
//	 */
//	public static Boolean canFormat (String format) {
//		return houzhui.contains(format.toUpperCase());
//	}
//	
//	/**
//	 * 解析文件产生对象
//	 * @param fileList
//	 * @return
//	 */
//	public static List <ImageDto> handleFile(String [] fileList, String dirFilePath, String format, String writeFilePath) {
//		String string;
//		File file;
//		ImageDto imageDto;
//		if(null!= fileList){
//			List<ImageDto> imageList = new ArrayList<ImageDto>();
//			HashSet<String> nameSet = new HashSet<String>();
//			String fileName;
//			String fileEnd="";
//			int dianz;
//			for (int i = 0; i < fileList.length; i++) {
//				string = fileList[i];
//				file = new File(dirFilePath, string);
//				boolean isPicture;
//				try {
//					isPicture = isImage(file);
//				} catch (Exception e) {
//					isPicture = false;
//				}
//				if(isPicture){
//					fileName = file.getName();
//					dianz=fileName.lastIndexOf(".");
//					if(dianz>0){
//						if(fileName.length()>dianz+1){
//							fileEnd=fileName.substring(dianz+1);
//						}else{
//							fileEnd="";
//						}
//						fileName=fileName.substring(0,dianz);
//					}
//					
//					if(nameSet.contains(fileName)){
//						fileName=fileName.concat("(").concat(fileEnd).concat(")");
//					}
//					nameSet.add(fileName);
//					imageDto = new ImageDto();
//					imageDto.setReadFile(file);
//					imageDto.setWriteFile(new File(writeFilePath.concat(File.separator).concat(fileName.concat(".").concat(format))));
//					/**暂时不考虑 增加格式控制，主要是前端提示，有些格式的图片转换会产生损失和失败*/
//					/*imageDto.setStartName(file.getName());
//					imageDto.setFormat(getExtension(file));
//					imageDto.setCanFormat(canFormat(imageDto.getFormat()));
//					imageDto.setEndName();*/
//					imageList.add(imageDto);
//				}
//			}
//			return imageList;
//		}
//		return null;
//	}
//	
//	/**
//	 * 获取图片格式函数
//	 * 
//	 * @param file
//	 * @return
//	 */
//	public static String getExtension(File file) {
//		String format = "";
//		ImageInputStream iis = null;
//		try {
//			iis = ImageIO.createImageInputStream(file);
//			Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);
//			if (iter.hasNext()) {
//				format = iter.next().getFormatName();
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			if (iis != null) {
//				try {
//					iis.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//		return format;
//	}
//
//	
//
//	/**
//	 * 判断是否为图片函数
//	 * 
//	 * @param file
//	 * @return
//	 */
//	public static boolean isImage(File resFile) {
//		ImageInputStream iis = null;
//		try {
//			iis = ImageIO.createImageInputStream(resFile);
//			Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);
//			// 文件是不是图片
//			if (iter.hasNext()) {
//				return true;
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			if (iis != null) {
//				try {
//					iis.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//		return false;
//	}
//}
