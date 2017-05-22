package com.auto.test.tools.xmlbean;

public class BeanEntity {
	
	private final String beanId;
	private final Object beanObject;
	
	public BeanEntity(String beanId, Object beanObject){
		this.beanId = beanId;
		this.beanObject = beanObject;
	}

	public String getBeanId() {
		return beanId;
	}


	public Object getBeanObject() {
		return beanObject;
	}
	
	

}
