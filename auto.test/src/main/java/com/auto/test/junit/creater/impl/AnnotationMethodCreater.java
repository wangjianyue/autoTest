/**
 * @author wjy
 * create at 2015-6-2
 */
package com.auto.test.junit.creater.impl;

import java.lang.reflect.Method;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.auto.test.annotation.Test;

/**
 * @author wjy edit at 2015-6-2
 */
@Service("annotationMethodCreater")
public class AnnotationMethodCreater extends AbatractMethodCreater {
	
	private  Test methodTest;
	
    protected void specialInit(Method method) {
    	methodTest = method.getAnnotation(Test.class);
	}


    /**
     * 获取输入输出结果表达式{input:[参数值1；参数值2：参数值3];output:值}
     * @return
     */
    protected JSONObject getMethodExpectValue(){
    	JSONObject expectedValueJson = new JSONObject();
    	expectedValueJson.put("input", methodTest.input());
    	expectedValueJson.put("output", methodTest.output());
    	
    	return expectedValueJson;
    }


   
   

}
