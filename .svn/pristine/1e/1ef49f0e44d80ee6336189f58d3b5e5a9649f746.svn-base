package com.bocom.service;

import com.bocom.dto.img.FormatImageDto;
import com.bocom.dto.img.ImageListDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public interface ImageFormatService {
	
	
	/**获取图片转换后下载的压缩文件
	 * @param images
	 * @return
	 */
	public String getUrl(FormatImageDto formatImage) throws Exception;
	
	/**
	 * 下载zip压缩文件
	 * @param fileName
	 * @param request
	 * @param response
	 */
	public void downLoadZip(String fileName, HttpServletRequest request, HttpServletResponse response);
	
	/**
     * 【功能描述：给图片添加文字水印】
	 * @param srcPath 源图片路径
	 * @param content 文字内容
	 * @param font 字体
	 * @param alpha 透明度(0.1-0.9)
	 * @param color 颜色
	 */
	public void addWaterMarkOnImage(String srcPath, String content,Font font, float alpha, Color color); 
	
	/**
     * 【功能描述：给PDF添加文字水印】
     * @param srcFile 待加水印文件
     * @param destFile 加水印后存放地址
     * @param text 加水印的文本内容
     * @param textWidth 文字横坐标
     * @param textHeight 文字纵坐标
     * @throws Exception
     */
	public void addWaterMarkOnPDF(String srcFile, String destFile, String text, int textWidth, int textHeight);
	
	/**
     * 【功能描述：给Excel添加文字水印】
     * @param srcFile 待加水印文件
     * @param destFile 加水印后存放地址
     * @param text 加水印的文本内容
     * @param startXCol  水印起始列  0
     * @param startYRow  水印起始行     10
     * @param betweenXCol 水印横向之间间隔多少列       9
     * @param betweenYRow 水印纵向之间间隔多少行       52
     * @param XCount 横向共有水印多少个      3
     * @param YCount 纵向共有水印多少个     50
     * @param waterRemarkWidth 水印图片宽度为多少列   0
     * @param waterRemarkHeight 水印图片高度为多少行   0
     */
	public void addWaterMarkOnExcel(String srcFile, String destFile, String text, int startXCol, int startYRow,  
            int betweenXCol, int betweenYRow, int XCount, int YCount,
            int waterRemarkWidth, int waterRemarkHeight);
	/**
     * 【功能描述：给word添加文字水印】  只支持docx后缀的word
     * @param srcFile 待加水印文件
     * @param destFile 加水印后存放地址
     * @param text 加水印的文本内容
	 */
	public void addWaterMarkOnDocx(String srcFile, String destFile, String text);
	
	/**
     * 【功能描述：给word转化为PDF】  提供给前端显示
	 * @param sourceFile
	 *            源文件, 绝对路径. 可以是Office2003-2007全部格式的文档, Office2010的没测试. 包括.doc,
	 *            .docx, .xls, .xlsx, .ppt, .pptx等. 示例: F:\\office\\source.doc
	 * @param destFile
	 *            目标文件. 绝对路径. 示例: F:\\pdf\\dest.pdf
	 */
	public Boolean office2PDF(String srcFile, String destFile);


	public Boolean Txt2PDF(String srcFile, String destFile);

	public String downLoadFileToServer(List<ImageListDto> imagelist,String userName, boolean adminFlag)
			throws
			Exception;
		   	
	
}
