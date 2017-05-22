/**
 * @author wjy
 * create at 2015-6-2
 */
package com.auto.test.junit.creater.code.generator.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.auto.test.aop.TestAnnotationAspect;
import com.auto.test.entity.BeanInfo;
import com.auto.test.entity.MethodInfo;
import com.auto.test.junit.creater.code.generator.CodeGenerator;
import com.github.pfmiles.minvelocity.TemplateUtil;

/**
 * @author wjy edit at 2015-6-2
 */
@Service("methodJunitCodeGenerator")
public class MethodJunitCodeGenerator implements CodeGenerator {
	private static final Logger logger = Logger.getLogger(MethodJunitCodeGenerator.class);

    public String outputJunitCode(BeanInfo bean, List<MethodInfo> methodInfos){
    	  InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("config/spring_junit_method_template.vm");
    	  BufferedReader reader = new BufferedReader(new InputStreamReader(in));
    	  StringBuilder sb = new StringBuilder();           
    	  
	       String line = null;      
		   try {      
		       while ((line = reader.readLine()) != null) {      
		    	   sb.append(line + "\n");       
		        }      
		    } catch (IOException e) {      
		    	logger.debug("获取测试方法生成模板异常",e);      
		    } finally {      
		       try {      
		            in.close();      
		        } catch (IOException e) {      
		        	logger.debug("获取test类生成模板异常",e);    
		        }      
	        }      
		   String templateString = sb.toString();
	       Map<String, Object> ctxPojo = new HashMap<String, Object>();
	       
	       
	       
	       
	       StringWriter out = new StringWriter();
	       
	       for(MethodInfo methodInfo : methodInfos){
	    	   ctxPojo.put("method", methodInfo);
		       ctxPojo.put("beanName", bean.getBeanName());
	    	   TemplateUtil.renderString(templateString, ctxPojo, out);
	       }
	       return out.toString();

    }

    
}
