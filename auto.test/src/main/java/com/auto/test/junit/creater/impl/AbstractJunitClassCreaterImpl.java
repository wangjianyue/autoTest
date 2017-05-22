/**
 * @author wjy
 * create at 2015-6-2
 */
package com.auto.test.junit.creater.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.stereotype.Service;

import com.auto.test.annotation.Test;
import com.auto.test.aop.TestAnnotationAspect;
import com.auto.test.entity.BeanInfo;
import com.auto.test.entity.MethodInfo;
import com.auto.test.junit.creater.JunitClassCreater;
import com.auto.test.junit.creater.MethodCreater;
import com.auto.test.junit.creater.code.generator.CodeGenerator;
import com.auto.test.tools.fileproc.TestClassFileProc;
import com.auto.test.utils.BeanNameHelper;

/**
 * @author wjy edit at 2015-6-2
 */
@Service("abstractJunitClassCreater")
public class AbstractJunitClassCreaterImpl implements JunitClassCreater {
	private static final Logger logger = Logger.getLogger(AbstractJunitClassCreaterImpl.class);

	
	
    private List<MethodCreater> methodCreaters = new ArrayList<MethodCreater>();
    
    @Autowired
    @Qualifier("annotationMethodCreater")
    private MethodCreater annotationMethodCreater; 
    
    @Autowired
    @Qualifier("beanClassJunitCodeGenerator")
    private CodeGenerator beanClassJunitCodeGenerator;
    @Autowired
    @Qualifier("methodJunitCodeGenerator")
    private CodeGenerator methodJunitCodeGenerator;
    
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
         
         List<MethodInfo> methodInfos = new ArrayList<MethodInfo>();
         if (bePasrsedClass != null) {
             Method[] methods = bePasrsedClass.getDeclaredMethods();
             for(Method method : methods){
            	 if(method.getAnnotation(Test.class) != null){
            		 MethodInfo methodInfo = annotationMethodCreater.buildMethodInfo(method);
            		 methodInfos.add(methodInfo);
            	 }
             }
         } 
         bean.setMethods(methodInfos);
         
         return bean;
         
    }
    
    
    public static void main(String[] args) {
        String className = "com.example.aop.BService";
        if(args.length > 1){
            className = args[1];
        }
        
        GenericXmlApplicationContext context = new GenericXmlApplicationContext();  
        context.setValidating(false);  
        context.load("classpath*:spring/spring-code-create.xml");  
        context.refresh();  
        JunitClassCreater junitClassCreater = context.getBean(AbstractJunitClassCreaterImpl.class);  
        
        BeanInfo bean = junitClassCreater.createBeanInfoByClassName(className);
        junitClassCreater.anaylseAndCreateTestJunitFileIfNeeded(bean);
    }

    public void anaylseAndCreateTestJunitFileIfNeeded(BeanInfo bean){
    	String result = "";
		result = beanClassJunitCodeGenerator.outputJunitCode(bean, bean.getMethods());
     	mavenTestClassFileProc.createTestJunitFile(bean, result);

    }


}
