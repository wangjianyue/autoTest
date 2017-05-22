package com.auto.test.entity;

import java.util.ArrayList;
import java.util.List;


/**
 * 待生成测试案例Bean详情
 * @author Administrator
 *
 */
public class BeanInfo {
	
	/**
	 * Bean的类型全称
	 */
	private String beanFullClass;
	
	/**
	 * Bean的packageInfo
	 */
	private String beanPackage;
	
	/**
	 * Bean的类型简称
	 */
	private String beanClass;
	
	/**
	 * Bean声明名称
	 */
	private String beanName;
	
	/**
	 * Bean下可以测试方法
	 */
	 private List<MethodInfo> methods = new ArrayList<MethodInfo>();
	

	public String getBeanFullClass() {
		return beanFullClass;
	}



	public void setBeanFullClass(String beanFullClass) {
		this.beanFullClass = beanFullClass;
	}



	public String getBeanClass() {
		return beanClass;
	}



	public void setBeanClass(String beanClass) {
		this.beanClass = beanClass;
	}



	public String getBeanName() {
		return beanName;
	}



	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}



	public List<MethodInfo> getMethods() {
		return methods;
	}



	public void setMethods(List<MethodInfo> methods) {
		this.methods = methods;
	}


	

	public String getBeanPackage() {
		return beanPackage;
	}



	public void setBeanPackage(String beanPackage) {
		this.beanPackage = beanPackage;
	}



	@Override
	public String toString() {
		return "BeanInfo [beanFullClass=" + beanFullClass + ", beanPackage="
				+ beanPackage + ", beanClass=" + beanClass + ", beanName="
				+ beanName + ", methods=" + methods + "]";
	}
    
    

}
