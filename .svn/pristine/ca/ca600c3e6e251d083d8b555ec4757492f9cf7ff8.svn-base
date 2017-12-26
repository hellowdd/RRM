package com.bocom.service.impl;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
import com.bocom.dto.img.FormatImageDto;
import com.bocom.dto.img.ImageDto;
import com.bocom.dto.img.ImageListDto;
import com.bocom.dto.session.SessionUserInfo;
import com.bocom.service.ImageFormatService;
import com.bocom.util.*;
import com.itextpdf.text.*;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.*;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;
import java.util.List;


@Service
public class ImageFormatServiceImpl implements ImageFormatService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private WordprocessingMLPackage wordMLPackage;
    private org.docx4j.wml.ObjectFactory factory;

    @Value("${myself.imageFlie.dir}")
    private String fileDir;

    @Autowired
    private HttpServletRequest request;

    @Override
    public String getUrl(FormatImageDto formatImage) throws Exception {
        String dirFilePath = downLoadFileToServer(formatImage.getImagelist(), UserUtils.getUserName(request
                .getSession()), UserUtils.isAdmin(request.getSession()));
        if (StringUtils.isNotBlank(dirFilePath) && StringUtils.isNotBlank(formatImage.getFromat())) {
            final String dirImagePath = convertImages(formatImage, dirFilePath);
            if (StringUtils.isNotBlank(dirImagePath)) {
                String dirZipPath = dirFilePath.concat(File.separator).concat("图片下载.zip");
                ZipUtil.zipFile(dirZipPath, new ArrayList<File>() {{
                    add(new File(dirImagePath));
                }});
                if ((new File(dirZipPath)).isFile()) {
                    return dirZipPath;
                }
            }
        }
        return null;
    }

    /**
     * 图片在临时目录下转换
     *
     * @param formatImage
     * @param dirFilePath
     */
    private String convertImages(FormatImageDto formatImage, String dirFilePath) {
        String writeFilePath = dirFilePath.concat(File.separator).concat(UUID.randomUUID().toString());
        //获取路径下所有文件和格式
        String[] files = getFile(dirFilePath);
        //针对文件进行分析
        List<ImageDto> imageList = handleFile(files, dirFilePath, formatImage.getFromat(), writeFilePath);
        if (CollectionUtils.isNotEmpty(imageList)) {
            File fileTemp = new File(writeFilePath);
            if (fileTemp.mkdirs()) {
                for (ImageDto imageDto : imageList) {
                    changFormat(imageDto.getReadFile(), formatImage.getFromat(), imageDto.getWriteFile());
                }
                return writeFilePath;
            } else {
                throw new RuntimeException("Cannot mkdir file : " + fileTemp.getName());
            }
        }
        return null;
    }

    /**
     * 文件下载到服务器临时目录下
     *
     * @param imagelist
     * @return
     */
//    public String downLoadFileToServer(List<ImageListDto> imagelist, String userName) {
//        if (CollectionUtils.isNotEmpty(imagelist)) {
//            File f = new File(fileDir);
//            if (!f.isDirectory()) {
//                f.mkdirs();
//            }
//            String imageDir = fileDir.concat(separator).concat(UUID.randomUUID().toString());
//            File fileTemp = new File(imageDir);
//            if (fileTemp.mkdirs()) {
//                Integer dianz;
//                String fileName;
//                Map<String, Integer> nameMap = new HashMap<String, Integer>();
//                for (ImageListDto imageListDto : imagelist) {
//                    if (null != imageListDto && StringUtils.isNotBlank(imageListDto.getUrl()) && StringUtils
//                            .isNotBlank(imageListDto.getName())) {
//                        if (nameMap.containsKey(imageListDto.getName())) {
//                            dianz = nameMap.get(imageListDto.getName()) + 1;
//                            fileName = imageListDto.getName().concat("(").concat(dianz.toString()).concat(")");
//                            nameMap.put(fileName, dianz);
//                        } else {
//                            fileName = imageListDto.getName();
//                            nameMap.put(fileName, 0);
//                        }
//
//
//                        if (!new FastDfsUtil().downloadFile(imageListDto.getUrl(), fileTemp.getPath().concat
//								(separator).concat(fileName))) {
//                            throw new RuntimeException("Cannot download imaeg : " + imageListDto.getUrl());
//                        }
//
//                        //为下载下来的文件增加水印
//
//
//                    }
//                }
//            } else {
//                throw new RuntimeException("Cannot mkdir file : " + fileTemp.getName());
//            }
//            return imageDir;
//        }
//        return null;
//    }


    /**
     * 获取文件夹内所有的文件
     *
     * @param pathName
     */
    private String[] getFile(String pathName) {
        File dirFile = new File(pathName);
        if (dirFile.isDirectory()) {
            return dirFile.list();
        }
        return null;
    }

    /**
     * 解析文件产生对象
     *
     * @param fileList
     * @return
     */
    private List<ImageDto> handleFile(String[] fileList, String dirFilePath, String format, String writeFilePath) {
        String string;
        File file;
        ImageDto imageDto;
        if (null != fileList) {
            List<ImageDto> imageList = new ArrayList<ImageDto>();
//			HashSet<String> nameSet = new HashSet<String>();
//			String fileName;
//			String fileEnd="";
//			int dianz;
            for (int i = 0; i < fileList.length; i++) {
                string = fileList[i];
                file = new File(dirFilePath, string);
                boolean isPicture;
                try {
                    isPicture = isImage(file);
                } catch (Exception e) {
                    isPicture = false;
                }
                if (isPicture) {
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

//					if(nameSet.contains(fileName)){
//						fileName=fileName.concat("(").concat(fileEnd).concat(")");
//					}
//					nameSet.add(fileName);
                    imageDto = new ImageDto();
                    imageDto.setReadFile(file);
                    imageDto.setWriteFile(new File(writeFilePath.concat(File.separator).concat(file.getName().concat
                            (".")
                            .concat(format))));
                    /**暂时不考虑 增加格式控制，主要是前端提示，有些格式的图片转换会产生损失和失败*/
					/*imageDto.setStartName(file.getName());
					imageDto.setFormat(getExtension(file));
					imageDto.setCanFormat(canFormat(imageDto.getFormat()));
					imageDto.setEndName();*/
                    imageList.add(imageDto);
                }
            }
            return imageList;
        }
        return null;
    }


    /**
     * 判断是否为图片函数
     *
     * @param file
     * @return
     */
    private boolean isImage(File resFile) {
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

    //第一个参数  原图的File对象  第二个参数  目标格式   第三个参数   输出图像的File对象
    private void changFormat(File srcFile, String format, File formatFile) {
        try {
            BufferedImage srcImg = ImageIO.read(srcFile);// 读取原图
            ImageIO.write(srcImg, format, formatFile);// 用指定格式输出到指定文件
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void downLoadZip(String fileName, HttpServletRequest request,
                            HttpServletResponse response) {
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {

            if (logger.isDebugEnabled()) {
                logger.debug("创建输入流读取文件...");
            }
            //获取输入流
            bis = new BufferedInputStream(new FileInputStream(new File(fileName)));
            //获取输出流
            if (logger.isDebugEnabled()) {
                logger.debug("创建输出流下载文件...");
            }
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("图片下载.zip", "UTF-8"));
            bos = new BufferedOutputStream(response.getOutputStream());

            //定义缓冲池大小，开始读写
            byte[] buff = new byte[2048];
            int bytesRead;
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
            //刷新缓冲，写出
            bos.flush();
            if (logger.isDebugEnabled()) {
                logger.debug("文件下载成功。");
            }
        } catch (Exception e) {
            logger.error("文件下载失败" + e.getMessage());
        } finally {
            //关闭流
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    logger.error("关闭输入流失败，" + e.getMessage());
                    e.printStackTrace();
                }
            }
            if (bis != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    logger.error("关闭输出流失败，" + e.getMessage());
                    e.printStackTrace();
                }
            }
            File zip = new File(fileName);
            if (zip.isFile()) {
                deleteDir(zip.getParentFile());
            }
        }

    }

    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     *
     * @param dir 将要删除的文件目录
     * @return boolean Returns "true" if all deletions were successful.
     * If a deletion fails, the method stops attempting to
     * delete and returns "false".
     */
    private boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

    @Override
    public void addWaterMarkOnImage(String srcPath, String content, Font font,
                                    float alpha, Color color) {
        BufferedImage bi;
        try {
            bi = ImageIO.read(new File(srcPath));

            Graphics2D g2 = (Graphics2D) bi.getGraphics();
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
            for (double x = textHeight * 3; x < bi.getWidth(); x += (textHeight * 3)) {
                double y = -yStep;
                for (; y < bi.getHeight(); y += yStep) {
                    g2.draw(rotatedText);
                    g2.translate(0, yStep);
                }
                g2.translate(textHeight * 3, -(y + yStep));
            }
            Thumbnails.of(bi).scale(1f).toFile(srcPath);

        } catch (IOException e) {
            logger.error("图片打水印失败，" + e.getMessage());
            e.printStackTrace();
        }

    }

    @Override
    public void addWaterMarkOnPDF(String srcFile, String destFile, String text,
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
            for (int i = 1; i < total; i++) {
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

        } catch (Exception e) {
            logger.error("pdf打水印失败，" + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void addWaterMarkOnExcel(String srcFile, String destFile,
                                    String text, int startXCol, int startYRow, int betweenXCol,
                                    int betweenYRow, int XCount, int YCount, int waterRemarkWidth,
                                    int waterRemarkHeight) {
        FileInputStream excelFileInputStream;
        try {
            excelFileInputStream = new FileInputStream(srcFile);
            Workbook wb = new XSSFWorkbook(excelFileInputStream);
            int sheetNum = wb.getNumberOfSheets();
            for (int i = 0; i < sheetNum; i++) {
                putWaterRemarkToExcel(wb, wb.getSheetAt(i), createWaterMark("网段"), startXCol, startYRow, betweenXCol,
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
     * 创建水印图片
     *
     * @param content
     */
    private BufferedImage createWaterMark(String content) {
        Integer width = 600;
        Integer height = 450;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);// 获取bufferedImage对象
        String fontType = "宋体";
        Integer fontStyle = Font.PLAIN;
        Integer fontSize = 100;
        Font font = new Font(fontType, fontStyle, fontSize);
        Graphics2D g2d = image.createGraphics(); // 获取Graphics2d对象
        image = g2d.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
        g2d.dispose();
        g2d = image.createGraphics();
        g2d.setColor(new Color(0, 0, 0, 50)); //设置字体颜色和透明度
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
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
        // 释放对象
        g2d.dispose();
        return image;
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
    private void putWaterRemarkToExcel(Workbook wb, Sheet sheet, BufferedImage bufferImg, int startXCol, int startYRow,
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

    @Override
    public void addWaterMarkOnDocx(String srcFile, String destFile, String text) {
        try {
            wordMLPackage = WordprocessingMLPackage.load(new File(srcFile));
            if (null == wordMLPackage) {
                wordMLPackage = new WordprocessingMLPackage();
            }
            factory = Context.getWmlObjectFactory();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ImageIO.write(createWaterMark(text), "png", out);
            byte[] imagebytes = out.toByteArray();
            Relationship relationship = createFooterPart(imagebytes);
            createFooterReference(relationship);
            Relationship rp = createHeaderPart(imagebytes);
            createHeaderReference(rp);
            wordMLPackage.save(new File(destFile));
        } catch (Exception e) {
            logger.error("Word打水印失败，" + e.getMessage());
            e.printStackTrace();
        }
    }

    private Relationship createFooterPart(byte[] imagebytes) throws Exception {
        FooterPart footerPart = new FooterPart();
        footerPart.setPackage(wordMLPackage);
        footerPart.getContent().add(newImage(wordMLPackage, footerPart, imagebytes));
        return wordMLPackage.getMainDocumentPart().addTargetPart(footerPart);
    }

    private Relationship createHeaderPart(byte[] imagebytes) throws Exception {
        HeaderPart headerPart = new HeaderPart();
        headerPart.setPackage(wordMLPackage);
        headerPart.getContent().add(newImage(wordMLPackage, headerPart, imagebytes));
        return wordMLPackage.getMainDocumentPart().addTargetPart(headerPart);
    }


    private void createFooterReference(Relationship relationship) {
        List<SectionWrapper> sections = wordMLPackage.getDocumentModel().getSections();
        SectPr sectPr = sections.get(sections.size() - 1).getSectPr();
        if (sectPr == null) {
            sectPr = factory.createSectPr();
            wordMLPackage.getMainDocumentPart().addObject(sectPr);
            sections.get(sections.size() - 1).setSectPr(sectPr);
        }
        FooterReference footerReference = factory.createFooterReference();
        footerReference.setId(relationship.getId());
        footerReference.setType(HdrFtrRef.DEFAULT);
        sectPr.getEGHdrFtrReferences().add(footerReference);
    }

    private void createHeaderReference(Relationship relationship) {
        List<SectionWrapper> sections = wordMLPackage.getDocumentModel().getSections();
        SectPr sectPr = sections.get(sections.size() - 1).getSectPr();
        if (sectPr == null) {
            sectPr = factory.createSectPr();
            wordMLPackage.getMainDocumentPart().addObject(sectPr);
            sections.get(sections.size() - 1).setSectPr(sectPr);
        }
        HeaderReference headerReference = factory.createHeaderReference();
        headerReference.setId(relationship.getId());
        headerReference.setType(HdrFtrRef.DEFAULT);
        sectPr.getEGHdrFtrReferences().add(headerReference);
    }

    private P newImage(WordprocessingMLPackage word, Part sourcePart, byte[] imagebytes) throws Exception {
        BinaryPartAbstractImage imagePart = BinaryPartAbstractImage.createImagePart(word, sourcePart, imagebytes);
        int id = (int) (Math.random() * 10000);
        Inline inline = imagePart.createImageInline("image", "image", id, id * 2, false);
        org.docx4j.wml.Drawing drawing = factory.createDrawing();
        drawing.getAnchorOrInline().add(inline);
        R r = factory.createR();
        r.getContent().add(drawing);
        P p = factory.createP();
        Br breakObj = new Br();
        breakObj.setType(STBrType.PAGE);
        p.getContent().add(breakObj);
        Ftr ftr = factory.createFtr();
        addFieldBegin(p);
        addPageNumberField(p);
        addFieldEnd(p);
        addText(p);
        addFieldBegin(p);
        addTotalPageNumberField(p);
        addFieldEnd(p);
        ftr.getContent().add(p);
        p.getContent().add(r);
        return p;
    }

    public Ftr createFooterWithPageNr() {
        Ftr ftr = factory.createFtr();
        P paragraph = factory.createP();
        addFieldBegin(paragraph);
        addPageNumberField(paragraph);
        addFieldEnd(paragraph);
        ftr.getContent().add(paragraph);
        return ftr;
    }

    private void addPageNumberField(P paragraph) {
        R run = factory.createR();
        Text txt = new Text();
        txt.setSpace("preserve");
        txt.setValue(" PAGE   \\* MERGEFORMAT ");
        run.getContent().add(factory.createRInstrText(txt));
        paragraph.getContent().add(run);
    }

    private void addTotalPageNumberField(P paragraph) {
        R run = factory.createR();
        Text txt = new Text();
        txt.setSpace("preserve");
        txt.setValue(" NUMPAGES   \\* MERGEFORMAT ");
        run.getContent().add(factory.createRInstrText(txt));
        paragraph.getContent().add(run);
    }


    private void addText(P paragraph) {
        R run = factory.createR();
        Text txt = new Text();
        txt.setSpace("preserve");
        txt.setValue("/");
        run.getContent().add(factory.createRInstrText(txt));
        paragraph.getContent().add(run);
    }

    private void addFieldBegin(P paragraph) {
        R run = factory.createR();
        FldChar fldchar = factory.createFldChar();
        fldchar.setFldCharType(STFldCharType.BEGIN);
        run.getContent().add(fldchar);
        paragraph.getContent().add(run);
    }

    private void addFieldEnd(P paragraph) {
        FldChar fldcharend = factory.createFldChar();
        fldcharend.setFldCharType(STFldCharType.END);
        R run3 = factory.createR();
        run3.getContent().add(fldcharend);
        paragraph.getContent().add(run3);
    }

    @Override
    public Boolean office2PDF(String sourceFile, String destFile) {
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

            OpenOfficeConnection connection = new SocketOpenOfficeConnection("localhost", 9666);
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

    @Override
    public Boolean Txt2PDF(String srcFile, String destFile) {
        Document document = new Document(PageSize.A4, 80, 80, 60, 30);
        BufferedReader read = null;
        try {
            PdfWriter.getInstance(document, new FileOutputStream(destFile));
            document.open();
            BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            com.itextpdf.text.Font FontChinese = new com.itextpdf.text.Font(bfChinese, 18, com.itextpdf.text.Font
                    .NORMAL);
            Paragraph t = new Paragraph(); //起一个别名，上班老板都不会发现，呵呵。
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

    @Override
    public String downLoadFileToServer(List<ImageListDto> imagelist, String userName, boolean adminFlag)
            throws Exception {
        if (CollectionUtils.isNotEmpty(imagelist)) {
            File f = new File(fileDir);
            if (!f.isDirectory()) {
                f.mkdirs();
            }
            String imageDir = fileDir.concat(File.separator).concat(UUID.randomUUID().toString());
            File fileTemp = new File(imageDir);
            if (fileTemp.mkdirs()) {
                Integer dianz;
                String fileName;
                Map<String, Integer> nameMap = new HashMap<String, Integer>();
                for (ImageListDto imageListDto : imagelist) {
                    if (null != imageListDto && StringUtils.isNotBlank(imageListDto.getUrl()) && StringUtils
                            .isNotBlank(imageListDto.getName())) {
                        if (nameMap.containsKey(imageListDto.getName())) {
                            dianz = nameMap.get(imageListDto.getName()) + 1;
                            fileName = imageListDto.getName().concat("(").concat(dianz.toString()).concat(")");
                            nameMap.put(fileName, dianz);
                        } else {
                            fileName = imageListDto.getName();
                            nameMap.put(fileName, 0);
                        }

                        String TFileName = fileTemp.getPath().concat(File.separator).concat(fileName);

                        if (!new FastDfsUtil().downloadFile(imageListDto.getUrl(), TFileName)) {
                            throw new RuntimeException("Cannot download imaeg : " + imageListDto.getUrl());
                        }
                        //为下载下来的文件增加水印
                        if (!adminFlag) {
                            FileUtil.addWaterMarkOnFile(FileUtil.fileToByte(new File(TFileName)), TFileName, TFileName,
                                    userName);
                        }
                    }
                }
            } else {
                throw new RuntimeException("Cannot mkdir file : " + fileTemp.getName());
            }
            return imageDir;
        }
        return null;
    }

}
