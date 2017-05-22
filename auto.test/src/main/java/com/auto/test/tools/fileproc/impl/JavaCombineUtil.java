/**
 * Copyright(c) 2012~now, China Merchants Bank all rights reserved.
 *
 * This software is the confidential and proprietary information 
 * of China Merchants Bank. You shall not disclose such Confidential 
 * Information and shall use it only in accordance with the terms 
 * of the license agreement you entered into with China Merchants Bank.
 */
package com.auto.test.tools.fileproc.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.springframework.stereotype.Service;

import com.auto.test.tools.codeparse.JavaFileInfo;
import com.auto.test.tools.fileproc.CombineUtil;

@Service
public class JavaCombineUtil implements CombineUtil {

    public void Combine(String sbIn, File repalcedFile) throws IOException{
        
        JavaFileInfo replacedJavaFile = new JavaFileInfo(repalcedFile);
        JavaFileInfo toCombinedJavaFile = new JavaFileInfo(sbIn);
        
        replacedJavaFile.CombinConetent(toCombinedJavaFile);
        
        
        PrintStream ps = new PrintStream(new FileOutputStream(repalcedFile,false));
        ps.append(replacedJavaFile.printJavaFile());
        ps.flush();
        ps.close();
    }
    


	


}
