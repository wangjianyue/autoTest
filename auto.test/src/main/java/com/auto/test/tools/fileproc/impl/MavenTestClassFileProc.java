package com.auto.test.tools.fileproc.impl;

import org.springframework.stereotype.Service;

import com.auto.test.tools.fileproc.TestClassFileProc;
import com.auto.test.utils.BeanNameHelper;

@Service("mavenTestClassFileProc")
public class MavenTestClassFileProc extends AbstractTestClassFileProc implements TestClassFileProc {
	
    protected String getFileFullPath(String fullClassName){
    	return System.getProperty("user.dir") + "/src/test/java/"+ BeanNameHelper.getFileDirFromClassName(fullClassName) + BeanNameHelper.getShortName(fullClassName) + "Test.java";
    }
	


}
