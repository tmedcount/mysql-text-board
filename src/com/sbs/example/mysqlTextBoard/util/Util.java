package com.sbs.example.mysqlTextBoard.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

public class Util {

	public static void mkdirs(String path) {
		File dir = new File(path);
		
		if(!dir.exists()) {
			dir.mkdirs();
		}
	}

	public static void writeFile(String path, String body) {
		File file = new File(path);

		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write(body);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static boolean rmdir(String path) {
		return rmdir(new File(path));
	}

	public static boolean rmdir(File dirToBeDeleted) {
	    File[] allContents = dirToBeDeleted.listFiles();
	    if (allContents != null) {
	        for (File file : allContents) {
	        	rmdir(file);
	        }
	    }
	    
	    return dirToBeDeleted.delete();
	}

	public static String getFileContents(String filePath) {
		String rs = null;
		try {
			// 바이트 단위로 파일읽기
			FileInputStream fileStream = null; // 파일 스트림

			fileStream = new FileInputStream(filePath);// 파일 스트림 생성
			// 버퍼 선언
			byte[] readBuffer = new byte[fileStream.available()];
			while (fileStream.read(readBuffer) != -1) {
			}

			rs = new String(readBuffer);

			fileStream.close(); // 스트림 닫기
		} catch (Exception e) {
			e.getStackTrace();
		}

		return rs;
	}

}
