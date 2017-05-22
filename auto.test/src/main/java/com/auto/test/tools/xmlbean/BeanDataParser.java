package com.auto.test.tools.xmlbean;

import java.util.List;

public interface BeanDataParser {
	
	public Class<?> getClassInfo();
	
	public List<BeanEntity> getBeanEntitys();
	
	public void putBeaninToFile(BeanEntity beanEntity, Class<?> beanClass);
}
