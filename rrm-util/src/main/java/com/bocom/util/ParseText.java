package com.bocom.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.hslf.extractor.PowerPointExtractor;
import org.apache.poi.hssf.extractor.ExcelExtractor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xslf.extractor.XSLFPowerPointExtractor;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xssf.extractor.XSSFExcelExtractor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

public class ParseText {
    private static int size = 100;

    public static String getTextInfo(String filePath) {
        FastDfsUtil fastDfsUtil = new FastDfsUtil();
        //获取文件的类型
        if (filePath.indexOf(".") != -1) {
            String suffix = filePath.substring(filePath.lastIndexOf(".") + 1, filePath.length());
            return parse(fastDfsUtil.downloadFile(filePath), suffix);
        }
        return "";
    }

    // 判断文档类型，调用不同的解析方法
    public static String parse(byte[] buffer, String suffix) {
        String text = "";
        switch (suffix) {
            case "doc":
                text = getTextFromWord(buffer);
                break;
            case "docx":
                text = getTextFromWord2007(buffer);
                break;
            case "xls":
                text = getTextFromExcel(buffer);
                break;
            case "xlsx":
                text = getTextFromExcel2007(buffer);
                break;
            case "ppt":
                text = getTextFromPPT(buffer);
                break;
            case "pptx":
                text = getTextFromPPT2007(buffer);
                break;
            case "pdf":
                text = getTextFormPDF(buffer);
                break;
            case "txt":
                text = getTextFormTxt(buffer);
                break;
            default:
                System.out.println("不支持解析的文档类型");
        }
        String newText = text.replaceAll("\\s*", "").replace("null", "");
        if (newText.length() > size) {
            return text.replaceAll("\\s*", "").replace("null", "").substring(0, 100);
        }
        return text.replaceAll("\\s*", "").replace("null", "").substring(0, newText.length());
    }

    // 读取Word97-2003的全部内容 doc  
    private static String getTextFromWord(byte[] file) {
        String text = "";
        InputStream fis = null;
        WordExtractor ex = null;
        try {
            // word 2003： 图片不会被读取  
            fis = new ByteArrayInputStream(file);
            ex = new WordExtractor(fis);
            text = ex.getText();
            ex.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block  
            e.printStackTrace();
        }
        return text;
    }

    // 读取Word2007+的全部内容 docx  
    private static String getTextFromWord2007(byte[] file) {
        String text = "";
        InputStream fis = null;
        XWPFDocument doc = null;
        XWPFWordExtractor workbook = null;
        try {
            fis = new ByteArrayInputStream(file);
            doc = new XWPFDocument(fis);
            workbook = new XWPFWordExtractor(doc);
            text = workbook.getText();
            workbook.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block  
            e.printStackTrace();
        }
        return text;
    }

    // 读取Excel97-2003的全部内容 xls  
    private static String getTextFromExcel(byte[] file) {
        InputStream is = null;
        HSSFWorkbook wb = null;
        String text = "";
        try {
            is = new ByteArrayInputStream(file);
            wb = new HSSFWorkbook(new POIFSFileSystem(is));
            ExcelExtractor extractor = new ExcelExtractor(wb);
            extractor.setFormulasNotResults(false);
            extractor.setIncludeSheetNames(false);
            text = extractor.getText();
            extractor.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }

    // 读取Excel2007+的全部内容 xlsx  
    private static String getTextFromExcel2007(byte[] file) {
        InputStream is = null;
        XSSFWorkbook workBook = null;
        String text = "";
        try {
            is = new ByteArrayInputStream(file);
            workBook = new XSSFWorkbook(is);
            XSSFExcelExtractor extractor = new XSSFExcelExtractor(workBook);
            extractor.setIncludeSheetNames(false);
            text = extractor.getText();
            extractor.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }

    // 读取Powerpoint97-2003的全部内容 ppt  
    private static String getTextFromPPT(byte[] file) {
        String text = "";
        InputStream fis = null;
        PowerPointExtractor ex = null;
        try {
            // word 2003： 图片不会被读取  
            fis = new ByteArrayInputStream(file);
            ex = new PowerPointExtractor(fis);
            text = ex.getText();
            ex.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block  
            e.printStackTrace();
        }
        return text;
    }

    // 抽取幻灯片2007+全部内容 pptx  
    private static String getTextFromPPT2007(byte[] file) {
        InputStream is = null;
        XMLSlideShow slide = null;
        String text = "";
        try {
            is = new ByteArrayInputStream(file);
            slide = new XMLSlideShow(is);
            XSLFPowerPointExtractor extractor = new XSLFPowerPointExtractor(slide);
            text = extractor.getText();
            extractor.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }

    // 读取pdf文件全部内容 pdf  
    private static String getTextFormPDF(byte[] file) {
        String text = "";
        PDDocument pdfdoc = null;
        InputStream is = null;
        try {
            is = new ByteArrayInputStream(file);
            pdfdoc = PDDocument.load(is);
            PDFTextStripper stripper = new PDFTextStripper();
            text = stripper.getText(pdfdoc);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pdfdoc != null) {
                    pdfdoc.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block  
                e.printStackTrace();
            }
        }
        return text;
    }

    // 读取txt文件全部内容 txt  
    private static String getTextFormTxt(byte[] file) {
        String text = "";
        try {
            String encoding = get_charset(file);
            text = new String(file, encoding);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return text;
    }

    // 获得txt文件编码方式  
    private static String get_charset(byte[] file) throws IOException {
        String charset = "GBK";
        byte[] first3Bytes = new byte[3];
        InputStream bis = null;
        try {
            boolean checked = false;
            bis = new ByteArrayInputStream(file);
            bis.mark(0);
            int read = bis.read(first3Bytes, 0, 3);
            if (read == -1)
                return charset;
            if (first3Bytes[0] == (byte) 0xFF && first3Bytes[1] == (byte) 0xFE) {
                charset = "UTF-16LE";
                checked = true;
            } else if (first3Bytes[0] == (byte) 0xFE && first3Bytes[1] == (byte) 0xFF) {
                charset = "UTF-16BE";
                checked = true;
            } else if (first3Bytes[0] == (byte) 0xEF && first3Bytes[1] == (byte) 0xBB
                    && first3Bytes[2] == (byte) 0xBF) {
                charset = "UTF-8";
                checked = true;
            }
            bis.reset();
            if (!checked) {
                while ((read = bis.read()) != -1) {
                    if (read >= 0xF0)
                        break;
                    if (0x80 <= read && read <= 0xBF) // 单独出现BF以下的，也算是GBK  
                        break;
                    if (0xC0 <= read && read <= 0xDF) {
                        read = bis.read();
                        if (0x80 <= read && read <= 0xBF) // 双字节 (0xC0 - 0xDF)  
                            // (0x80 - 0xBF),也可能在GB编码内  
                            continue;
                        else
                            break;
                    } else if (0xE0 <= read && read <= 0xEF) {// 也有可能出错，但是几率较小  
                        read = bis.read();
                        if (0x80 <= read && read <= 0xBF) {
                            read = bis.read();
                            if (0x80 <= read && read <= 0xBF) {
                                charset = "UTF-8";
                                break;
                            } else
                                break;
                        } else
                            break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                bis.close();
            }
        }
        return charset;
    }

    private static final int CACHE_SIZE = 1024;

    /*****
     * 功能：文件转换为二进制数组
     * 创建人：donghongguang
     * 创建时间：2017年12月5日 下午3:28:08
     * @param filePath 文件路径
     * @return
     * @version 1.0.0
     */
    public static byte[] fileToByte(String filePath) throws Exception {
        byte[] data = new byte[0];
        File file = new File(filePath);
        if (file.exists()) {
            FileInputStream in = new FileInputStream(file);
            ByteArrayOutputStream out = new ByteArrayOutputStream(2048);
            byte[] cache = new byte[CACHE_SIZE];
            int nRead = 0;
            while ((nRead = in.read(cache)) != -1) {
                out.write(cache, 0, nRead);
                out.flush();
            }
            out.close();
            in.close();
            data = out.toByteArray();
        }
        return data;
    }

    /*****
     * 功能：通过url读取流生成文件
     * 创建人：donghongguang
     * 创建时间：2017年12月5日 下午3:27:55
     * @param
     * @return
     * @version 1.0.0
     */
    public static void downUrlTxt(String fileName, String fileUrl, String downPath) {
        File savePath = new File(downPath);
        if (!savePath.exists()) {
            savePath.mkdir();
        }
        String[] urlname = fileUrl.split("/");
        int len = urlname.length - 1;
        String uname = urlname[len];//获取文件名
        try {
            File file = new File(savePath + "/" + uname);//创建新文件
            if (file != null && !file.exists()) {
                file.createNewFile();
            }
            OutputStream oputstream = new FileOutputStream(file);
            URL url = new URL(fileUrl);
            HttpURLConnection uc = (HttpURLConnection) url.openConnection();
            uc.setDoInput(true);//设置是否要从 URL 连接读取数据,默认为true
            uc.connect();
            InputStream iputstream = uc.getInputStream();
            System.out.println("file size is:" + uc.getContentLength());//打印文件长度
            byte[] buffer = new byte[4 * 1024];
            int byteRead = -1;
            while ((byteRead = (iputstream.read(buffer))) != -1) {
                oputstream.write(buffer, 0, byteRead);
            }
            oputstream.flush();
            iputstream.close();
            oputstream.close();
        } catch (Exception e) {
            System.out.println("读取失败！");
            e.printStackTrace();
        }
        System.out.println("生成文件路径：" + downPath + fileName);
    }

    public static boolean exists(String URLName) {
        try {
            //设置此类是否应该自动执行 HTTP 重定向（响应代码为 3xx 的请求）。
            HttpURLConnection.setFollowRedirects(false);
            //到 URL 所引用的远程对象的连接
            HttpURLConnection con = (HttpURLConnection) new URL(URLName).openConnection();
            /* 设置 URL 请求的方法， GET POST HEAD OPTIONS PUT DELETE TRACE 以上方法之一是合法的，具体取决于协议的限制。*/
            con.setRequestMethod("HEAD");
            //从 HTTP 响应消息获取状态码
            return (con.getResponseCode() == HttpURLConnection.HTTP_OK);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
         }
     }

    public static void main(String[] args) {
        try {
//            String context = parse(fileToByte("F:\\附件二：新智认知2017年体检项目.pdf"), "pdf");
//            String context = parse(fileToByte("http:\\\\\\\\test.pc
// .fastdfs:8080/group1/M00/02/02/CiWVZlomSEeAR_kfAAAAHMUgOhI768.txt"), "pdf");
//            System.out.println(context);

//            downUrlTxt("1112.txt", "http://test.pc.fastdfs:8080\\group1/M02/05/04/CiWVZln5lLSAKR1RAADmj3o2apo355.jpg",
//                    "F:/test/");
            
            System.out.println(exists("http://stable.pc.com/FASTDFS2/group1/M00/00/01/CiWmdFozwWaATf0IAATuTvYPtjk50.docx"));
            System.out.println(exists("http://stable.pc.com/FASTDFS2\\group1/M00/00/01/CiWmdFozwWaATf0IAATuTvYPtjk50.docx"));
        } catch (Exception e) {
            e.printStackTrace();
        }


        //远程读取文件内容（必须共享文件才可以，如：fastdfs的存储文件共享，通过访问ip下的文件夹即可访问）
        /*try
        {
            File remoteFile = new File("//test.pc.fastdfs:8080/group1/M00/02/02/CiWVZlomSEeAR_kfAAAAHMUgOhI768.txt");
            // 192.168.7.146是对方机器IP，test是对方那个共享文件夹名字，如果没有共享是访问不到的
            //远程文件其实主要是地址，地址弄对了就和本地文件没什么区别 ，windows里面//或者\\\\开头就表示这个文件是网络路径了其实这个地址就像我们再windows里面，点击开始
            //然后点击运行，然后输入 \\192.168.7.146/test/1.txt访问远程文件一样的
             
            BufferedReader br = new BufferedReader(new FileReader(remoteFile));
            String str;
            while ((str = br.readLine()) != null) {
                System.out.println(str);
            }
            br.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }*/

    }
}