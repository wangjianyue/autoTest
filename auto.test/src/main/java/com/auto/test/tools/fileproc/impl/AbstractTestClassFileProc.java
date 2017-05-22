package com.auto.test.tools.fileproc.impl;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auto.test.entity.BeanInfo;
import com.auto.test.tools.fileproc.OutPutFileProc;
import com.auto.test.tools.fileproc.TestClassFileProc;
import com.auto.test.utils.BeanNameHelper;

@Service("abstractTestClassFileProc")
public class AbstractTestClassFileProc implements TestClassFileProc {
	
	@Autowired
	private OutPutFileProc classFileProc;

	
	
    public void createTestJunitFile(BeanInfo bean, String fileOutputString) {
		String fileFullPath = getFileFullPath(bean.getBeanFullClass());
		classFileProc.classFileFilling(fileFullPath, fileOutputString);

    }
    
    protected String getFileFullPath(String fullClassName){
    	return System.getProperty("user.dir") + "/test/"+ BeanNameHelper.getFileDirFromClassName(fullClassName) + BeanNameHelper.getShortName(fullClassName) + "Test.java";
    }
    
    /**
	 * 判断测试文件是否已经存在
	 * @return
	 */
	public boolean isTestFileExists(String fullClassName){
		File testFile = new File(getFileFullPath(fullClassName));
		return testFile.exists(); 
	}

	
	

}
