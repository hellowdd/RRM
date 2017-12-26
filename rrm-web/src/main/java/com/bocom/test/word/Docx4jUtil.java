package com.bocom.test.word;  
  
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Transparency;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import org.docx4j.dml.wordprocessingDrawing.Inline;
import org.docx4j.jaxb.Context;
import org.docx4j.model.structure.SectionWrapper;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.Part;
import org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage;
import org.docx4j.openpackaging.parts.WordprocessingML.FooterPart;
import org.docx4j.openpackaging.parts.WordprocessingML.HeaderPart;
import org.docx4j.relationships.Relationship;
import org.docx4j.wml.Br;
import org.docx4j.wml.Drawing;
import org.docx4j.wml.FldChar;
import org.docx4j.wml.FooterReference;
import org.docx4j.wml.Ftr;
import org.docx4j.wml.HdrFtrRef;
import org.docx4j.wml.HeaderReference;
import org.docx4j.wml.P;
import org.docx4j.wml.R;
import org.docx4j.wml.STBrType;
import org.docx4j.wml.STFldCharType;
import org.docx4j.wml.SectPr;
import org.docx4j.wml.Text;
  
public class Docx4jUtil {  
      
    private Docx4jUtil(){};  
      
    private static WordprocessingMLPackage wordMLPackage;  
    private static org.docx4j.wml.ObjectFactory factory;  
      
    public static void addWatermarkAndPageInfo(String sourcePath, String targetPath, String content) throws Exception{  
        wordMLPackage = WordprocessingMLPackage.load(new File(sourcePath));  
        if(null==wordMLPackage){
        	wordMLPackage = new WordprocessingMLPackage();
        }
        factory = Context.getWmlObjectFactory();  
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(createWaterMark(content), "png", out);  
        byte[] imagebytes = out.toByteArray();  
        Relationship relationship = createFooterPart(imagebytes);  
        createFooterReference(relationship);  
        Relationship rp = createHeaderPart(imagebytes); 
        createHeaderReference(rp);   
        wordMLPackage.save(new File(targetPath));  
    }  
      
    private static void createHeaderReference(Relationship relationship) {
        List<SectionWrapper> sections =  wordMLPackage.getDocumentModel().getSections();    
        SectPr sectPr = sections.get(sections.size() - 1).getSectPr();    
        if (sectPr==null ) {    
            sectPr = factory.createSectPr();    
            wordMLPackage.getMainDocumentPart().addObject(sectPr);    
            sections.get(sections.size() - 1).setSectPr(sectPr);    
        }    
        HeaderReference headerReference = factory.createHeaderReference();
        headerReference.setId(relationship.getId());
        headerReference.setType(HdrFtrRef.DEFAULT);
        sectPr.getEGHdrFtrReferences().add(headerReference);   
	}

	public static void main(String[] args) throws Exception {  
        Docx4jUtil.addWatermarkAndPageInfo("H:/testsssss/11.docx", "H:/testsssss/22.docx", "我我我我我我我我");  
    }  
      
      
    private static Relationship createFooterPart(byte[] imagebytes) throws Exception {    
        FooterPart footerPart = new FooterPart();    
        footerPart.setPackage(wordMLPackage);    
        footerPart.getContent().add(newImage(wordMLPackage, footerPart, imagebytes));
        return wordMLPackage.getMainDocumentPart().addTargetPart(footerPart);    
    }  
    
    private static Relationship createHeaderPart(byte[] imagebytes) throws Exception {    
        HeaderPart headerPart = new HeaderPart();
        headerPart.setPackage(wordMLPackage);
        headerPart.getContent().add(newImage(wordMLPackage, headerPart, imagebytes));
        return wordMLPackage.getMainDocumentPart().addTargetPart(headerPart);    
    }    
      
    private static P newImage(WordprocessingMLPackage word,Part sourcePart, byte[] imagebytes) throws Exception {  
        BinaryPartAbstractImage imagePart = BinaryPartAbstractImage.createImagePart(word, sourcePart, imagebytes);  
        int id = (int) (Math.random() * 10000);  
        Inline inline = imagePart.createImageInline("image", "image", id, id * 2, false);  
        Drawing drawing = factory.createDrawing();  
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
     
    public static Ftr createFooterWithPageNr() {    
        Ftr ftr = factory.createFtr();    
        P paragraph = factory.createP();    
        addFieldBegin(paragraph);    
        addPageNumberField(paragraph);  
        addFieldEnd(paragraph);    
        ftr.getContent().add(paragraph);    
        return ftr;    
    }    
     
    private static void addPageNumberField(P paragraph) {    
        R run = factory.createR();    
        Text txt = new Text();    
        txt.setSpace("preserve");    
        txt.setValue(" PAGE   \\* MERGEFORMAT ");    
        run.getContent().add(factory.createRInstrText(txt));    
        paragraph.getContent().add(run);    
    }    
      
    private static void addTotalPageNumberField(P paragraph){  
        R run = factory.createR();    
        Text txt = new Text();    
        txt.setSpace("preserve");    
        txt.setValue(" NUMPAGES   \\* MERGEFORMAT ");    
        run.getContent().add(factory.createRInstrText(txt));    
        paragraph.getContent().add(run);    
    }  
      
      
    private static void addText(P paragraph) {    
        R run = factory.createR();    
        Text txt = new Text();    
        txt.setSpace("preserve");  
        txt.setValue("/");    
        run.getContent().add(factory.createRInstrText(txt));    
        paragraph.getContent().add(run);    
    }    
      
    private static void addFieldBegin(P paragraph) {    
        R run = factory.createR();    
        FldChar fldchar = factory.createFldChar();    
        fldchar.setFldCharType(STFldCharType.BEGIN);    
        run.getContent().add(fldchar);    
        paragraph.getContent().add(run);    
    }    
     
    private static void addFieldEnd(P paragraph) {    
        FldChar fldcharend = factory.createFldChar();    
        fldcharend.setFldCharType(STFldCharType.END);    
        R run3 = factory.createR();    
        run3.getContent().add(fldcharend);    
        paragraph.getContent().add(run3);    
    }    
     
    private static void createFooterReference(Relationship relationship){    
        List<SectionWrapper> sections =  wordMLPackage.getDocumentModel().getSections();    
        SectPr sectPr = sections.get(sections.size() - 1).getSectPr();    
        if (sectPr==null ) {    
            sectPr = factory.createSectPr();    
            wordMLPackage.getMainDocumentPart().addObject(sectPr);    
            sections.get(sections.size() - 1).setSectPr(sectPr);    
        }    
        FooterReference footerReference = factory.createFooterReference();    
        footerReference.setId(relationship.getId());    
        footerReference.setType(HdrFtrRef.DEFAULT);    
        sectPr.getEGHdrFtrReferences().add(footerReference);    
    }  
    
	/**
	 * 
	 * @param content
	 * @throws IOException
	 * createby lyt
	 */
	private static BufferedImage createWaterMark(String content){
		Integer width = 400;
		Integer height = 50;
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);// 获取bufferedImage对象
		String fontType = "宋体";
		Integer fontStyle = Font.PLAIN;
		Integer fontSize = 30;
		Font font = new Font(fontType, fontStyle, fontSize);
		Graphics2D g2d = image.createGraphics(); // 获取Graphics2d对象
		image = g2d.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
		g2d.dispose();
		g2d = image.createGraphics();
		g2d.setColor(new Color(0, 0, 0, 50)); //设置字体颜色和透明度
		g2d.setStroke(new BasicStroke(1)); // 设置字体
		g2d.setFont(font); // 设置字体类型  加粗 大小
		g2d.rotate(Math.toRadians(-10),(double) image.getWidth() / 2, (double) image.getHeight() / 2);//设置倾斜度
		
		FontRenderContext context = g2d.getFontRenderContext();
		Rectangle2D bounds = font.getStringBounds(content, context);
		double x = (width - bounds.getWidth()) / 2;
		double y = (height - bounds.getHeight()) / 2;
		double ascent = -bounds.getY();
		double baseY = y + ascent;
		// 写入水印文字原定高度过小，所以累计写水印，增加高度
		g2d.drawString(content, (int)x, (int)baseY);
		// 设置透明度
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
		// 释放对象
		g2d.dispose();
		return image;
	}
     
}  