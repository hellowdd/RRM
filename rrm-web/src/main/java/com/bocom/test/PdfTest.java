package com.bocom.test;

import java.io.FileOutputStream;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

public class PdfTest {
	
	public static void main(String[] args) throws Exception {  
		addWaterMark("H:/testsssss/Keepalived权威指南中文.pdf", "H:/testsssss/11.pdf", "wwww", 100, 100);
	}
	
	/**
     * 
     * 【功能描述：添加图片和文字水印】 【功能详细描述：功能详细描述】
     * @param srcFile 待加水印文件
     * @param destFile 加水印后存放地址
     * @param text 加水印的文本内容
     * @param textWidth 文字横坐标
     * @param textHeight 文字纵坐标
     * @throws Exception
     */
    public static void addWaterMark(String srcFile, String destFile, String text,
            int textWidth, int textHeight) throws Exception
    {
        // 待加水印的文件
        PdfReader reader = new PdfReader(srcFile);
        // 加完水印的文件
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(
                destFile));
        int total = reader.getNumberOfPages() + 1;
        PdfContentByte content;
        // 设置字体
        BaseFont font = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        // 循环对每页插入水印
        for (int i = 1; i < total; i++)
        {
            // 水印的起始
            content = stamper.getUnderContent(i);
            // 开始
            content.beginText();
            // 设置颜色 默认为蓝色
            content.setColorFill(BaseColor.BLUE);
            // content.setColorFill(Color.GRAY);
            // 设置字体及字号
            content.setFontAndSize(font, 38);
            // 设置起始位置
            // content.setTextMatrix(400, 880);
            content.setTextMatrix(textWidth, textHeight);
            // 开始写入水印
            content.showTextAligned(Element.ALIGN_LEFT, text, textWidth,
                    textHeight, 45);
            content.endText();
        }
        stamper.close();
    }

}
