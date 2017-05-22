/**
 * @author wjy
 * create at 2015-6-2
 */
package com.auto.test.junit.creater;

import java.lang.reflect.Method;

import com.auto.test.entity.MethodInfo;


/**
 * @author wjy edit at 2015-6-2
 */
public interface MethodCreater {

	 /**
     * 对输入输出值进行初始化
     * @param method
     */
    public void specialInit(String argValues, String rtnValue);
    
    public MethodInfo buildMethodInfo(Method method);
 
}
