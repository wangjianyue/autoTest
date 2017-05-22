package com.auto.test.tools.fileproc;

import com.auto.test.entity.BeanInfo;

public interface TestClassFileProc {
	
	/**
	 * 判断测试文件是否已经存在
	 * @return
	 */
	public boolean isTestFileExists(String fullClassName);
	
	/**
	 * 生成测试案例，若文件已存在则追加，否则新增测试文件
	 * @param fileOutputString
	 */
	public void createTestJunitFile(BeanInfo bean, String fileOutputString);
	

}
