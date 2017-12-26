package com.bocom.util;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
@Component
public class File2PdfUtil {

    @Value("${openffice.ip}")
    private String ip;

    @Value("${openffice.port}")
    private String port;


    private  Logger logger = LoggerFactory.getLogger(File2PdfUtil.class);


    public  boolean  file2Pdf(String sourceFile, String destFile) {
        if (sourceFile.endsWith("txt")) {
            return Txt2PDF(sourceFile, destFile);
        } else {
            return office2PDF(sourceFile, destFile);
        }
    }

    /**
     * 【功能描述：给word转化为PDF】  提供给前端显示
     *
     * @param sourceFile 源文件, 绝对路径. 可以是Office2003-2007全部格式的文档, Office2010的没测试. 包括.doc,
     *                   .docx, .xls, .xlsx, .ppt, .pptx等. 示例: F:\\office\\source.doc
     * @param destFile   目标文件. 绝对路径. 示例: F:\\pdf\\dest.pdf
     */
    public  Boolean office2PDF(String sourceFile, String destFile) {
        try {
            File inputFile = new File(sourceFile);
            if (!inputFile.exists()) {
                return false;// 找不到源文件, 则返回-1
            }
            // 如果目标路径不存在, 则新建该路径
            File outputFile = new File(destFile);
            if (!outputFile.getParentFile().exists()) {
                outputFile.getParentFile().mkdirs();
            }

            OpenOfficeConnection connection = new SocketOpenOfficeConnection(ip, Integer.valueOf(port));
            connection.connect();
            DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
            converter.convert(inputFile, outputFile);
            connection.disconnect();
            return true;
        } catch (Exception e) {
            logger.error("文件转化为PDF失败，" + e.getMessage());
            e.printStackTrace();

        }

        return false;
    }


    /**
     * txt转化为pdf
     *
     * @param srcFile
     * @param destFile
     * @return
     */
    public  Boolean Txt2PDF(String srcFile, String destFile) {
        Document document = new Document(PageSize.A4, 80, 80, 60, 30);
        BufferedReader read = null;
        try {
            PdfWriter.getInstance(document, new FileOutputStream(destFile));
            document.open();
            BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            com.itextpdf.text.Font FontChinese = new com.itextpdf.text.Font(bfChinese, 18, com.itextpdf.text.Font
                    .NORMAL);
            Paragraph t = new Paragraph();
            t.setAlignment(Element.ALIGN_CENTER);
            t.setLeading(30.0f);
            document.add(t);
            FontChinese = new com.itextpdf.text.Font(bfChinese, 11, com.itextpdf.text.Font.NORMAL);
            read = new BufferedReader(new InputStreamReader(new FileInputStream(srcFile), EncodingDetect
                    .getJavaEncode(srcFile)));
            String line = null;
            while ((line = read.readLine()) != null) {
                t = new Paragraph(line, FontChinese);
                t.setAlignment(Element.ALIGN_LEFT);
                t.setLeading(20.0f);
                document.add(t);
            }
            return true;
        } catch (Exception e) {
            logger.error("TXT文件转化为PDF失败，" + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                read.close();
                document.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }


}
