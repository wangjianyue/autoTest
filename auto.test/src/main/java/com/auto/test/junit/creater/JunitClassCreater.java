package com.auto.test.junit.creater;

import com.auto.test.entity.BeanInfo;


public interface JunitClassCreater {
		 
	public BeanInfo createBeanInfoByClassName(String sourceClassName);
	
	 public void anaylseAndCreateTestJunitFileIfNeeded(BeanInfo bean);
}
