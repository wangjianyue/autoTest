/**
 * @author wjy
 * create at 2015-6-12
 */
package com.auto.test.aop;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.auto.test.annotation.Mock;
import com.auto.test.entity.BeanInfo;
import com.auto.test.entity.MethodInfo;
import com.auto.test.entity.Property;
import com.auto.test.entity.ReflectTypeReference;
import com.auto.test.junit.creater.JunitClassCreater;
import com.auto.test.junit.creater.MethodCreater;
import com.auto.test.tools.codeparse.FileInfo;

  
@Aspect  
public class TestAnnotationAspect {  
	private static final Logger logger = Logger.getLogger(TestAnnotationAspect.class);
	
	private static final String TEST_JSON_FILE = System.getProperty("user.dir") + "/src/test/resources/json/";
  
	@Autowired
    @Qualifier("aopJunitClassCreater")
    private JunitClassCreater aopJunitClassCreater; 
	
    @Autowired
    @Qualifier("calledMethodJunitMethodCreater")
    private MethodCreater calledMethodJunitMethodCreater; 
	
    @Pointcut("execution(public * com.htffund..*.*(..))")  
    private void pointCutMethod() {  
    }  

    //声明环绕通知
    @Around("pointCutMethod()")  
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable { 
        
     
    	logger.debug("进入方法---环绕通知");  
        Object[] args = pjp.getArgs();
        MethodSignature sig = (MethodSignature)pjp.getSignature();
        Method method = sig.getMethod();
        logger.debug("-------------"+sig.getDeclaringTypeName()+"."+method.getName()+"输入参数值 start--------------");
        for(Object arg : args){
        	logger.debug("参数 : " + arg.toString());
        }
        Mock mockMethod = method.getAnnotation(Mock.class);
        if(mockMethod != null){
        	if(mockMethod.recordReplay()){
        		Object result = replayIfHaveBeenRecode(method, args);
        		if((!"void".equals(method.getReturnType().getName())) && result == null){
        			 result = pjp.proceed(); 
        			 recordResult(method, args, result);
        		}
        		return result;
        	}
        	
        }
        
        logger.debug("-------------输入参数值 end--------------");
        Object o = pjp.proceed();  
        
        BeanInfo bean = aopJunitClassCreater.createBeanInfoByClassName(sig.getDeclaringTypeName());
        List<MethodInfo> methods = new ArrayList<MethodInfo>();
        
        String rtnValue = JSON.toJSONString(o);
		JSONArray argValues = new JSONArray();
		for(Object arg : args){
			 argValues.add(JSONObject.toJSONString(arg));
	    }

		calledMethodJunitMethodCreater.specialInit(argValues.toJSONString(), rtnValue);
        MethodInfo methodInfo = calledMethodJunitMethodCreater.buildMethodInfo(method);
        methods.add(methodInfo);
        bean.setMethods(methods);
        
        aopJunitClassCreater.anaylseAndCreateTestJunitFileIfNeeded(bean);
        
        logger.debug("返回值为：" + o);
        logger.debug("退出方法---环绕通知");  
        return o;  
    }
    
	private void recordResult(Method method, Object[] args, Object result) {
		try {
			String fileName = TEST_JSON_FILE+method.getDeclaringClass().getName();
			createDirIfNotExeist(fileName);
			PrintWriter printWriter = new PrintWriter(new FileWriter(fileName, true));
		
			StringBuilder sb = new StringBuilder();
			sb.append(method.getName()).append("#^");
			for(Object arg : args){
				sb.append(JSON.toJSONString(arg)).append("?^");
			}
			sb.append("#^");
			sb.append(JSON.toJSONString(result));
			
			printWriter.println(sb.toString());
			printWriter.close();
		
		} catch (IOException e) {
			 logger.debug("进行mock录制报错：", e);
		}
		
	}

	private Object replayIfHaveBeenRecode(Method method, Object[] args) {
		FileInfo classFile = new FileInfo(new File(TEST_JSON_FILE+method.getDeclaringClass().getName()));
		String[] lines = classFile.getFileContents();
		String expectInputString = "";
		for(Object arg : args){
			expectInputString += JSON.toJSONString(arg)+"?^";
		}
		
		for(String line : lines){
			String[] result = line.split("\\#\\^");
			if(method.getName().equals(result[0]) && expectInputString.equals(result[1])){
		        Type outputType = method.getGenericReturnType();
		        Class<?> outputClass = method.getReturnType();
		        Property rtn = Property.getInstance(outputType, outputClass);
		        if(rtn.isCollection()){
		        	return JSON.parseObject(result[2], new ReflectTypeReference<Object>(outputType){});
		        }
				return JSON.parseObject(result[2], method.getReturnType());
			}
		}		
		return null;
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