/**
 * @author wjy
 * create at 2015-6-2
 */
package com.auto.test.junit.creater.code.generator;

import java.util.List;

import com.auto.test.entity.BeanInfo;
import com.auto.test.entity.MethodInfo;

/**
 * @author wjy
 * edit at 2015-6-2
 */
public interface CodeGenerator {
   
	/**
	 * 根据测试模板生成测试代码
	 * @param beanName
	 * @param method
	 * @return
	 */
    public String outputJunitCode(BeanInfo bean, List<MethodInfo> methods);

}