package com.auto.test.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 待生成测试案例方法详情
 * @author Administrator
 *
 */
public class MethodTestPropValues {
	
	/**
	 * 方法名称
	 */
	private String MethodName;
	
	/**
	 * 是否为公开方法
	 */
	private boolean isPublic;
	
	/**
	 * 方法声明的异常
	 */
	private String declaredExcetpion;
	
	/**
	 * 输入参数详情
	 */
    private List<ArgProp> inputProps = new ArrayList<ArgProp>();
    
    /**
     * 输出参数详情
     */
    private ArgProp outputProp = null;

	public String getMethodName() {
		return MethodName;
	}

	public void setMethodName(String methodName) {
		MethodName = methodName;
	}

	public boolean isPublic() {
		return isPublic;
	}

	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}

	public List<ArgProp> getInputProps() {
		return inputProps;
	}

	public void setInputProps(List<ArgProp> inputProps) {
		this.inputProps = inputProps;
	}

	public ArgProp getOutputProp() {
		return outputProp;
	}

	public void setOutputProp(ArgProp outputProp) {
		this.outputProp = outputProp;
	}
	

	public String getDeclaredExcetpion() {
		return declaredExcetpion;
	}

	public void setDeclaredExcetpion(String declaredExcetpion) {
		this.declaredExcetpion = declaredExcetpion;
	}

	@Override
	public String toString() {
		return "MethodInfo [MethodName=" + MethodName + ", isPublic="
				+ isPublic + ", declaredExcetpion=" + declaredExcetpion
				+ ", inputProps=" + inputProps + ", outputProp=" + outputProp
				+ "]";
	}
    
    

}
