package com.sbs.example.mysqlTextBoard.util;

import java.io.BufferedWriter;
import java.io.File;
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

}
