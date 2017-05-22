/**
 * @author wjy
 * create at 2015-6-2
 */
package com.auto.test.junit.creater.impl;

import org.springframework.stereotype.Service;

import com.auto.test.junit.creater.MethodCreater;

/**
 * @author wjy edit at 2015-6-2
 */
@Service("calledMethodJunitMethodCreater")
public class CalledMethodJunitMethodCreater extends AbatractMethodCreater implements MethodCreater {
   
	/**
    * 通过AOP获取调用路径上的输入参数值
    */
   private String argValues;
   /**
    * 通过AOP获取调用路径上的输出结果值
    */
   private String rtnValue;
   
	public String getArgValues() {
		return argValues;
	}
	public void setArgValues(String argValues) {
		this.argValues = argValues;
	}
	public String getRtnValue() {
		return rtnValue;
	}
	public void setRtnValue(String rtnValue) {
		this.rtnValue = rtnValue;
	}
	   
   
   
   
   
  


  

}
