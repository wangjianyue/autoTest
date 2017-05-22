package com.auto.test.tools.codeparse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class FileInfo {
	private static final Logger logger = Logger.getLogger(FileInfo.class);

	private File file;
	private int lines;
	private String[] lineContents;
	
	
	
	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public int getLines() {
		return lines;
	}

	public void setLines(int lines) {
		this.lines = lines;
	}

	public String[] getLineContents() {
		return lineContents;
	}

	public void setLineContents(String[] lineContents) {
		this.lineContents = lineContents;
	}

	public FileInfo(File file){
		this.file = file;
		try {
			lineContents = getFileContents(file);
			lines = lineContents.length;
		} catch (IOException e) {
			logger.debug("获取文件异常",e);
		}
		
	}
	
	public String[] getFileContents() {
		return lineContents;
	}
	
	public String[] getFileContents(File file) throws IOException{
		BufferedReader br = null;
		String readLine = null;
		
		List<String> fileString = new ArrayList<String>();
		
		try {
		      br = new BufferedReader(
					new InputStreamReader(new FileInputStream(file)));
		
		} catch (FileNotFoundException e) {
			logger.debug("获取文件异常",e);
		} 

		 try {		 			 
			 readLine = br.readLine();
			 for(int i = 0; readLine != null; i++){
			     fileString.add(readLine);
				 readLine = br.readLine();
			  }
		} catch (IOException e) {
			logger.debug("读取文件异常",e);
		} catch (Exception e){
			logger.debug("获取文件异常",e);
		}
		
		return fileString.toArray(new String[0]);
		
	}
	
	
	
}
