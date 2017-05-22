/**
 * @author wjy
 * create at 2015-6-2
 */
package com.auto.test.junit.creater.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.auto.test.entity.BeanInfo;
import com.auto.test.junit.creater.JunitClassCreater;
import com.auto.test.junit.creater.code.generator.CodeGenerator;
import com.auto.test.tools.fileproc.TestClassFileProc;
import com.auto.test.utils.BeanNameHelper;

/**
 * @author wjy edit at 2015-6-2
 */
@Service("aopJunitClassCreater")
public class AopJunitClassCreaterImpl implements JunitClassCreater {
	private static final Logger logger = Logger.getLogger(AopJunitClassCreaterImpl.class);
 
    @Autowired
    @Qualifier("beanClassJunitCodeGenerator")
    private CodeGenerator beanClassJunitCodeGenerator;
    
    @Autowired
    @Qualifier("mavenTestClassFileProc")
    private TestClassFileProc mavenTestClassFileProc;    



    public BeanInfo createBeanInfoByClassName(String sourceClassName){
    	 Class<?> bePasrsedClass = null;
         try {
             bePasrsedClass = Thread.currentThread().getContextClassLoader()
                     .loadClass(sourceClassName);
         } catch (ClassNotFoundException e) {
        	 logger.debug("获取类文件报错",e);
         }
         
         BeanInfo bean = new BeanInfo();
         bean.setBeanFullClass(bePasrsedClass.getTypeName());
         bean.setBeanPackage(BeanNameHelper.getPackageString(bePasrsedClass.getTypeName()));
         bean.setBeanClass(BeanNameHelper.getShortName(bePasrsedClass.getTypeName()));
         bean.setBeanName(BeanNameHelper.lowercaseFirst(bePasrsedClass.getSimpleName()));
        
         
         return bean;
         
    }
    
   
    public void anaylseAndCreateTestJunitFileIfNeeded(BeanInfo bean){
    	String result = "";
    	

		result = beanClassJunitCodeGenerator.outputJunitCode(bean, bean.getMethods());
 
        
    	mavenTestClassFileProc.createTestJunitFile(bean, result);

    }


}
