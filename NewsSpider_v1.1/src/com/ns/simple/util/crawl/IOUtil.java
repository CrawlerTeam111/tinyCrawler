package com.ns.simple.util.crawl;
/**
 * 写文件工具类
 * */
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

public class IOUtil {
	public static void writeFile(String filePath, String value, String encoding) {
		FileWriter fw = null;
		try {
			fw = new FileWriter(new File(filePath),true);
			fw.write(value);
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}

}
