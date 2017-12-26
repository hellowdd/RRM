package com.bocom.test.word;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;

public class Office2PDF {
	/**
	 * 将Office文档转换为PDF. 运行该函数需要用到OpenOffice, OpenOffice下载地址为
	 * http://www.openoffice.org/ 方法示例: String sourcePath =
	 * "F:\\office\\source.doc"; String destFile = "F:\\pdf\\dest.pdf";
	 * Converter.office2PDF(sourcePath, destFile);
	 * 
	 * @param sourceFile
	 *            源文件, 绝对路径. 可以是Office2003-2007全部格式的文档, Office2010的没测试. 包括.doc,
	 *            .docx, .xls, .xlsx, .ppt, .pptx等. 示例: F:\\office\\source.doc
	 * @param destFile
	 *            目标文件. 绝对路径. 示例: F:\\pdf\\dest.pdf
	 * @return 操作成功与否的提示信息. 如果返回 -1, 表示找不到源文件, 或url.properties配置错误; 如果返回 0,
	 *         则表示操作成功; 返回1, 则表示转换失败
	 */

	public static int office2PDF(String sourceFile, String destFile) {

		try {

			File inputFile = new File(sourceFile);
			if (!inputFile.exists()) {
				return -1;// 找不到源文件, 则返回-1
			}

			// 如果目标路径不存在, 则新建该路径
			File outputFile = new File(destFile);
			if (!outputFile.getParentFile().exists()) {
				outputFile.getParentFile().mkdirs();
			}

			// 启动OpenOffice的服务
			String command = "C:/Program Files (x86)/OpenOffice 4/program/soffice.exe -headless -accept=\"socket,host=127.0.0.1,port=9666;urp;\"";

			Process pro = Runtime.getRuntime().exec(command);

			// connect to an OpenOffice.org instance running on port 8100
			OpenOfficeConnection connection = new SocketOpenOfficeConnection("127.0.0.1", 9666);
			connection.connect();

			// convert
			DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
			converter.convert(inputFile, outputFile);
			// close the connection
			connection.disconnect();
			// 关闭OpenOffice服务的进程
			pro.destroy();
			return 0;

		} catch (FileNotFoundException e) {

			e.printStackTrace();

			return -1;

		} catch (IOException e) {

			e.printStackTrace();

		}

		return 1;

	}

	public static void main(String[] args) {
		String source = "H:/testsssss/11.doc";
		String dest = "H:/testsssss/1.pdf";
		int result = office2PDF(source, dest);
		System.out.println(result);
	}

}
