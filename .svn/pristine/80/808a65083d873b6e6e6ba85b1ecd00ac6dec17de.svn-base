package com.bocom.util;

import com.bocom.util.img.GifUtil;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.*;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.poi.hslf.usermodel.HSLFSlide;
import org.apache.poi.hslf.usermodel.HSLFSlideShow;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.sl.usermodel.SlideShow;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.docx4j.dml.wordprocessingDrawing.Inline;
import org.docx4j.jaxb.Context;
import org.docx4j.model.structure.SectionWrapper;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.Part;
import org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage;
import org.docx4j.openpackaging.parts.WordprocessingML.FooterPart;
import org.docx4j.openpackaging.parts.WordprocessingML.HeaderPart;
import org.docx4j.relationships.Relationship;
import org.docx4j.wml.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigInteger;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileUtil {

    private static Logger logger = LoggerFactory.getLogger(FileUtil.class);

    private static org.docx4j.wml.ObjectFactory factory;

    private static String PATN = System.getProperty("springmvc.root.rrm");

    /**
     * 获取文件的md5值
     *
     * @param file
     * @return
     * @throws Exception
     */
    public static String getFileMD5(MultipartFile file) throws Exception {
        byte[] uploadBytes = file.getBytes();
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        byte[] digest = md5.digest(uploadBytes);
        String hashString = new BigInteger(1, digest).toString(16);
        return hashString.toUpperCase();
    }


    /**
     * 删除文件夹
     *
     * @param dir
     * @return
     */
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }

    /**
     * 将存放在sourceFilePath目录下的源文件，打包成fileName名称的zip文件，并存放到zipFilePath路径下
     *
     * @param sourceFilePath :待压缩的文件路径
     * @param zipFilePath    :压缩后存放路径
     * @param fileName       :压缩后文件的名称
     * @return
     */
    public static boolean fileToZip(String sourceFilePath, String zipFilePath, String fileName) {
        boolean flag = false;
        File sourceFile = new File(sourceFilePath);
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        FileOutputStream fos = null;
        ZipOutputStream zos = null;
        if (sourceFile.exists() == false) {
            logger.info("待压缩的文件目录：" + sourceFilePath + "不存在.");
        } else {
            try {
                File zipFile = new File(zipFilePath + "/" + fileName + ".zip");
                if (zipFile.exists()) {
                    logger.info(zipFilePath + "目录下存在名字为:" + fileName + ".zip" + "打包文件.");
                } else {
                    File[] sourceFiles = sourceFile.listFiles();
                    if (null == sourceFiles || sourceFiles.length < 1) {
                        logger.info("待压缩的文件目录：" + sourceFilePath + "里面不存在文件，无需压缩.");
                    } else {
                        fos = new FileOutputStream(zipFile);
                        zos = new ZipOutputStream(new BufferedOutputStream(fos));
                        byte[] bufs = new byte[1024 * 10];
                        for (int i = 0; i < sourceFiles.length; i++) {
                            //创建ZIP实体，并添加进压缩包
                            ZipEntry zipEntry = new ZipEntry(sourceFiles[i].getName());
                            zos.putNextEntry(zipEntry);
                            //读取待压缩的文件并写进压缩包里
                            fis = new FileInputStream(sourceFiles[i]);
                            bis = new BufferedInputStream(fis, 1024 * 10);
                            int read = 0;
                            while ((read = bis.read(bufs, 0, 1024 * 10)) != -1) {
                                zos.write(bufs, 0, read);
                            }
                        }
                        flag = true;
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            } finally {
                //关闭流
                try {
                    if (null != bis) bis.close();
                    if (null != zos) zos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        }
        return flag;
    }

    /**
     * @param srcPath
     * @param content
     * @param desFile
     */
    public static void addWaterMarkOnImage(InputStream srcPath, String content, String desFile) {

        String fontType = "宋体";
        Integer fontStyle = Font.PLAIN;


        BufferedImage bi;
        try {
            bi = ImageIO.read(srcPath);

            int hight = bi.getHeight();

            int fontSize = hight / 8;

            Font font = new Font(fontType, fontStyle, fontSize);


            Graphics2D g2 = (Graphics2D) bi.getGraphics();
            GlyphVector fontGV = font.createGlyphVector(g2.getFontRenderContext(), content);
            Rectangle size = fontGV.getPixelBounds(g2.getFontRenderContext(), 0, 0);
            double textWidth = size.getWidth();
            double textHeight = size.getHeight();
            Shape textShape = fontGV.getOutline();
            AffineTransform rotate45 = AffineTransform.getRotateInstance(Math.PI / 4d);
            Shape rotatedText = rotate45.createTransformedShape(textShape);
            g2.setPaint(new GradientPaint(0, 0, Color.LIGHT_GRAY, bi.getWidth() / 2, bi.getHeight() / 2, Color
                    .LIGHT_GRAY));
            g2.setStroke(new BasicStroke(1.0f));
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC));
            double yStep = Math.sqrt(textWidth * textWidth / 2) + 100;
            for (double x = textHeight * 3; x < bi.getWidth(); x += (textHeight * 3)) {
                double y = -yStep;
                for (; y < bi.getHeight(); y += yStep) {
                    g2.draw(rotatedText);
                    g2.translate(0, yStep);
                }
                g2.translate(textHeight * 5, -(y + yStep));
            }
            Thumbnails.of(bi).scale(1f).toFile(desFile);

        } catch (IOException e) {
            logger.error("图片打水印失败，" + e.getMessage());
            e.printStackTrace();
        }

    }

    public static void addWaterMarkOnImage(InputStream srcPath, String content, Font font,
                                           float alpha, Color color, String desFile) {


    }

    /**
     * 【功能描述：给Excel添加文字水印】
     *
     * @param srcFile           待加水印文件
     * @param destFile          加水印后存放地址
     * @param text              加水印的文本内容
     * @param startXCol         水印起始列  0
     * @param startYRow         水印起始行     10
     * @param betweenXCol       水印横向之间间隔多少列       9
     * @param betweenYRow       水印纵向之间间隔多少行       50
     * @param XCount            横向共有水印多少个      3
     * @param YCount            纵向共有水印多少个     6
     * @param waterRemarkWidth  水印图片宽度为多少列   0
     * @param waterRemarkHeight 水印图片高度为多少行   0
     */
    public static void addWaterMarkOnExcel(InputStream srcFile, String destFile, String text) {
        addWaterMarkOnExcel(srcFile, destFile, text, 0, 10, 9, 50, 3, 6, 0, 0);
    }

    public static void addWaterMarkOnExcel(InputStream srcFile, String destFile,
                                           String text, int startXCol, int startYRow, int betweenXCol,
                                           int betweenYRow, int XCount, int YCount, int waterRemarkWidth,
                                           int waterRemarkHeight) {
        try {
            OPCPackage opcPackage = OPCPackage.open(srcFile);
            XSSFWorkbook wb = new XSSFWorkbook(opcPackage);
            int sheetNum = wb.getNumberOfSheets();
            for (int i = 0; i < sheetNum; i++) {

                putWaterRemarkToExcel(wb, wb.getSheetAt(i), createWaterMark(text), startXCol, startYRow,
                        betweenXCol,
                        betweenYRow, XCount, YCount, waterRemarkWidth, waterRemarkHeight);
            }
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            try {
                wb.write(os);
            } catch (IOException e) {
                e.printStackTrace();
            }
            wb.close();
            byte[] content = os.toByteArray();

            File file1 = new File(destFile);// Excel文件生成后存储的位置。
            OutputStream fos = null;

            try {
                fos = new FileOutputStream(file1);
                fos.write(content);
                os.close();
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            logger.error("excel打水印失败，" + e.getMessage());
            e.printStackTrace();
        }

    }


    /**
     * 为Excel打上水印工具函数
     * 请自行确保参数值，以保证水印图片之间不会覆盖。
     * 在计算水印的位置的时候，并没有考虑到单元格合并的情况，请注意
     *
     * @param wb                Excel Workbook
     * @param sheet             需要打水印的Excel
     * @param waterRemarkPath   水印地址，classPath，目前只支持png格式的图片，
     *                          因为非png格式的图片打到Excel上后可能会有图片变红的问题，且不容易做出透明效果。
     *                          同时请注意传入的地址格式，应该为类似："\\excelTemplate\\test.png"
     * @param startXCol         水印起始列
     * @param startYRow         水印起始行
     * @param betweenXCol       水印横向之间间隔多少列
     * @param betweenYRow       水印纵向之间间隔多少行
     * @param XCount            横向共有水印多少个
     * @param YCount            纵向共有水印多少个
     * @param waterRemarkWidth  水印图片宽度为多少列
     * @param waterRemarkHeight 水印图片高度为多少行
     * @throws IOException
     */
    public static void putWaterRemarkToExcel(Workbook wb, Sheet sheet, BufferedImage bufferImg, int startXCol, int
            startYRow,
                                             int betweenXCol, int betweenYRow, int XCount, int YCount,
                                             int waterRemarkWidth, int waterRemarkHeight) throws IOException {
        //加载图片
        ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
        if (null == bufferImg) {
            throw new RuntimeException("向Excel上面打印水印，读取水印图片失败(2)。");
        }
        ImageIO.write(bufferImg, "png", byteArrayOut);
        //开始打水印
        Drawing drawing = sheet.createDrawingPatriarch();
        //按照共需打印多少行水印进行循环
        for (int yCount = 0; yCount < YCount; yCount++) {
            //按照每行需要打印多少个水印进行循环
            for (int xCount = 0; xCount < XCount; xCount++) {
                //创建水印图片位置
                int xIndexInteger = startXCol + (xCount * waterRemarkWidth) + (xCount * betweenXCol);
                int yIndexInteger = startYRow + (yCount * waterRemarkHeight) + (yCount * betweenYRow);
                /*
                 * 参数定义：
                 * 第一个参数是（x轴的开始节点）；
                 * 第二个参数是（是y轴的开始节点）；
                 * 第三个参数是（是x轴的结束节点）；
                 * 第四个参数是（是y轴的结束节点）；
                 * 第五个参数是（是从Excel的第几列开始插入图片，从0开始计数）；
                 * 第六个参数是（是从excel的第几行开始插入图片，从0开始计数）；
                 * 第七个参数是（图片宽度，共多少列）；
                 * 第8个参数是（图片高度，共多少行）；
                 */
                ClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0, xIndexInteger, yIndexInteger, xIndexInteger +
                        waterRemarkWidth, yIndexInteger + waterRemarkHeight);
                Picture pic = drawing.createPicture(anchor, wb.addPicture(byteArrayOut.toByteArray(), Workbook
                        .PICTURE_TYPE_PNG));
                pic.resize();
            }
        }
    }

    public static BufferedImage createWaterMark(String content) {
        return createWaterMark(content, 300, 400, 100);
    }

    /**
     * 创建水印图片
     *
     * @param content
     */
    public static BufferedImage createWaterMark(String content, Integer width, Integer height, Integer fontSize) {
//        Integer width = 300;
//        Integer height = 400;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);// 获取bufferedImage对象
        String fontType = "宋体";
        Integer fontStyle = Font.PLAIN;
//        Integer fontSize = 100;
        Font font = new Font(fontType, fontStyle, fontSize);
        Graphics2D g2d = image.createGraphics(); // 获取Graphics2d对象
        image = g2d.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
        g2d.dispose();
        g2d = image.createGraphics();
        g2d.setColor(Color.RED); //设置字体颜色和透明度
        g2d.setStroke(new BasicStroke(1)); // 设置字体
        g2d.setFont(font); // 设置字体类型  加粗 大小
        g2d.rotate(Math.toRadians(-10), (double) image.getWidth() / 2, (double) image.getHeight() / 2);//设置倾斜度

        FontRenderContext context = g2d.getFontRenderContext();
        Rectangle2D bounds = font.getStringBounds(content, context);
        double x = (width - bounds.getWidth()) / 2;
        double y = (height - bounds.getHeight()) / 2;
        double ascent = -bounds.getY();
        double baseY = y + ascent;
        // 写入水印文字原定高度过小，所以累计写水印，增加高度
        g2d.drawString(content, (int) x, (int) baseY);
        // 设置透明度
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR));
        // 释放对象
        g2d.dispose();
        return image;
    }


    /**
     * 【功能描述：给word添加文字水印】
     *
     * @param srcFile  源文件输入流
     * @param destFile 目标文件
     * @param text     水印文字
     */


    public static void addWaterMarkOnDocX(InputStream srcFile, String destFile, String text) {
        try {
            XWPFDocument xDoc = new XWPFDocument(srcFile);
            XWPFHeaderFooterPolicy xFooter = new XWPFHeaderFooterPolicy(xDoc);
            xFooter.createWatermark(text);
            OutputStream os = new FileOutputStream(destFile);
            xDoc.write(os);
        } catch (Exception e) {
            logger.error("Word打水印失败，" + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 【功能描述：给PDF添加文字水印】
     *
     * @param srcFile    待加水印文件
     * @param destFile   加水印后存放地址
     * @param text       加水印的文本内容
     * @param textWidth  文字横坐标
     * @param textHeight 文字纵坐标
     * @throws Exception
     */
    public static void addWaterMarkOnPDF(InputStream srcFile, String destFile, String text,
                                         int textWidth, int textHeight) { // 待加水印的文件
        try {
            PdfReader reader = new PdfReader(srcFile);
            // 加完水印的文件
            PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(destFile));
            int total = reader.getNumberOfPages() + 1;

            PdfContentByte content;
            // 设置字体
            BaseFont font = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            // 循环对每页插入水印
            com.itextpdf.text.Rectangle pageRect = null;
            for (int i = 1; i < total; i++) {

                pageRect = stamper.getReader().getPageSizeWithRotation(i);

                // 计算水印X,Y坐标
                float x = pageRect.getWidth() / 2;
                float y = pageRect.getHeight() / 2 - 10;

                // 计算水印X,Y坐标
                float x1 = pageRect.getWidth() / 10;
                float y1 = pageRect.getHeight() / 10 - 10;

                // 计算水印X,Y坐标
                float x2 = pageRect.getWidth() * 0.8f;
                float y2 = pageRect.getHeight() * 0.8f - 10;

                content = stamper.getOverContent(i);
                content.saveState();
                // set Transparency
                PdfGState gs = new PdfGState();
                // 设置透明度为0.2
                gs.setFillOpacity(0.3f);
                content.setGState(gs);
                // 注意这里必须调用一次restoreState 否则设置无效
                content.restoreState();
                content.beginText();
                content.setFontAndSize(font, 50);
                content.setColorFill(BaseColor.LIGHT_GRAY);

                // 水印文字成45度角倾斜
                content.showTextAligned(Element.ALIGN_CENTER
                        , text, x,
                        y, 55);
                // 水印文字成45度角倾斜
                content.showTextAligned(Element.ALIGN_CENTER
                        , text, x1,
                        y1, 55);
                // 水印文字成45度角倾斜
                content.showTextAligned(Element.ALIGN_CENTER
                        , text, x2,
                        y2, 55);
                // 添加水印文字
                content.endText();
                content.setLineWidth(1f);
                content.stroke();
            }
            stamper.close();

        } catch (Exception e) {
            logger.error("pdf打水印失败，" + e.getMessage());
            e.printStackTrace();
        }
    }


    /**
     * 获取图片的分辨率
     *
     * @param file
     * @return
     */
    public static String readImg(File file) {
        FileChannel fc = null;
        if (file.exists() && file.isFile()) {
            try {
                BufferedImage bi = null;
                bi = ImageIO.read(file);
                String width = bi.getWidth() + "";
                String height = bi.getHeight() + "";
                return width + "*" + height;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    /**
     * 保存文件到服务器
     *
     * @param stream
     * @param path     保存文件的服务器路径
     * @param savefile 需要保存的文件
     * @throws IOException
     */
    public static void saveFileFromInputStream(InputStream stream, String path, String savefile) throws Exception {
        FileOutputStream fs = new FileOutputStream(path + "/" + savefile);
        System.out.println("------------" + path + "/" + savefile);
        byte[] buffer = new byte[1024 * 1024];
        //int bytesum = 0;
        int byteread = 0;
        while ((byteread = stream.read(buffer)) != -1) {
            // bytesum += byteread;
            fs.write(buffer, 0, byteread);
            fs.flush();
        }
        fs.close();
        stream.close();
    }


    public static void addWaterMarkOnPPT2003(InputStream srcFile, String destFile, String text) {

        try (SlideShow slideShow = new HSLFSlideShow(srcFile)) {

            BufferedImage bufferedImage = createWaterMark(text);
            //加载图片
            ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", byteArrayOut);

            List<HSLFSlide> slides = slideShow.getSlides();
            org.apache.poi.sl.usermodel.PictureData pictureData = slideShow.addPicture(byteArrayOut.toByteArray(),
                    org.apache.poi.sl.usermodel.PictureData.PictureType.PNG);
            for (HSLFSlide slide : slides) {
                slide.createPicture(pictureData);
            }
            // 输出PPT文件
            slideShow.write(new FileOutputStream(new File(destFile)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addWaterMarkOnPPT2007(InputStream srcFile, String destFile, String text) {

        try (SlideShow slideShow = new XMLSlideShow(srcFile)) {

            BufferedImage bufferedImage = createWaterMark(text);
            //加载图片
            ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", byteArrayOut);

            List<XSLFSlide> slides = slideShow.getSlides();
            org.apache.poi.sl.usermodel.PictureData pictureData = slideShow.addPicture(byteArrayOut.toByteArray(),
                    org.apache.poi.sl.usermodel.PictureData.PictureType.PNG);
            for (XSLFSlide slide : slides) {
                slide.createPicture(pictureData);
            }
            // 输出PPT文件
            slideShow.write(new FileOutputStream(new File(destFile)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //word后缀名
    private static String[] wordList = {"doc", "docx"};
    //excel后缀名
    private static String[] excelList = {"xls", "xlsx"};
    //图片后缀名
    private static String[] imgList = {"png", "jpg", "jpeg", "bmp", "psd"};
    //视频后缀名
    private static String[] videoList = {"flv", "avi"};

    public static boolean addWaterMarkOnFile(byte[] srcFile, String fileName, String destFile, String text)
            throws
            Exception {
        String suffix = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();
        if (srcFile == null) {
            return true;
        }
        InputStream fileInputStream = new ByteArrayInputStream(srcFile);
        if (Arrays.asList(imgList).contains(suffix)) {
            addWaterMarkOnImage(fileInputStream, text, destFile);
            return true;
        }
        if (Arrays.asList(wordList).contains(suffix)) {
            addWaterMarkOnDocX(fileInputStream, destFile, text);
            return true;
        }
        if (Arrays.asList(excelList).contains(suffix)) {
            addWaterMarkOnExcel(fileInputStream, destFile, text);
            return true;
        }
        if (Arrays.asList(videoList).contains(suffix)) {
            BufferedImage bufferedImage = createWaterMark(text, 100, 100, 25);
            String tempImg = PATN + UUID.randomUUID().toString().replaceAll("-", "") + ".jpg";
            File tempImgFile = new File(tempImg);
            ImageIO.write(bufferedImage, "jpg", tempImgFile);
            VideoUtil.addWaterMarkOnVideo(fileName, destFile, tempImg);
            tempImgFile.delete();
            return true;
        }
        if (suffix.equalsIgnoreCase("pdf")) {
            addWaterMarkOnPDF(fileInputStream, destFile, text, 400, 500);
            return true;
        }
        if (suffix.equalsIgnoreCase("pptx")) {
            addWaterMarkOnPPT2007(fileInputStream, destFile, text);
            return true;
        }
        if (suffix.equalsIgnoreCase("ppt")) {
            addWaterMarkOnPPT2003(fileInputStream, destFile, text);
            return true;
        }
        if (suffix.equalsIgnoreCase("gif")) {
            GifUtil.addTextWatermarkToGif(fileInputStream, text, destFile);
            return true;
        }
        return false;
    }

    public static byte[] fileToByte(File file) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        BufferedInputStream in = new BufferedInputStream(file.toURI().toURL().openStream());
        byte[] b = new byte[1024];
        int len = -1;
        while ((len = in.read(b)) != -1) {
            bos.write(b, 0, len);
        }
        // 得到文件的二进制数组
        return bos.toByteArray();
    }

    public static byte[] fileToByte(InputStream file) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        BufferedInputStream in = new BufferedInputStream(file);
        byte[] b = new byte[1024];
        int len = -1;
        while ((len = in.read(b)) != -1) {
            bos.write(b, 0, len);
        }
        // 得到文件的二进制数组
        return bos.toByteArray();
    }

}
