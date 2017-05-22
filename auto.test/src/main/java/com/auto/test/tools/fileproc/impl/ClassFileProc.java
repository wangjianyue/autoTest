/**
 * @author wjy
 * create at 2015-6-10
 */
package com.auto.test.tools.fileproc.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auto.test.aop.TestAnnotationAspect;
import com.auto.test.tools.fileproc.CombineUtil;
import com.auto.test.tools.fileproc.OutPutFileProc;

/**
 * @author wjy edit at 2015-6-10
 */
@Service
public class ClassFileProc implements OutPutFileProc{
	private static final Logger logger = Logger.getLogger(ClassFileProc.class);

	@Autowired
	private CombineUtil combineUtil;


    public void classFileFilling(String fileFullPath, String fileOutputString){
    	try {
            File testFile = new File(fileFullPath);
            if (testFile.exists()) {
            	combineUtil.Combine(fileOutputString, testFile);
            } else {
                createFile(fileFullPath, fileOutputString);
            }

        } catch (IOException e) {
        	logger.debug("读取class文件信息报错", e);
        }
    	logger.debug(fileOutputString);
    }

	public void setCombineUtil(CombineUtil combineUtil) {
		this.combineUtil = combineUtil;
	}

	private void createFile(String fileName, String fileOutputString)
            throws IOException {
        createDirIfNotExeist(fileName);
        BufferedWriter output = new BufferedWriter(new FileWriter(fileName));
        output.write(fileOutputString);
        output.close();
    }

	 private void createDirIfNotExeist(String fileName) {
        File file = new File(fileName);

        if (!file.getParentFile().exists()) {
        	logger.debug("目标文件所在路径不存在，准备创建。。。");
            if (!file.getParentFile().mkdirs()) {
            	logger.debug("创建目录文件所在的目录失败！");
            }

        }
    }

}
