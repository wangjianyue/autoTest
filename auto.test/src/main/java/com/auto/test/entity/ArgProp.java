/**
 * @author wjy
 * create at 2014-8-5
 */
package com.auto.test.entity;



/**
 * @author wjy
 * edit at 2014-8-5
 */
public class ArgProp {
    
    /**
     * 参数全类型
     */
    private String fullType;
    /**
     * 参数名称
     */
    private String argName;
    /**
     * 参数值表达式
     */
    private String valueExpression;
    
    /**
     * 比较方法
     */
    private String compareMethod;
    
	public String getFullType() {
		return fullType;
	}
	public void setFullType(String fullType) {
		this.fullType = fullType;
	}
	public String getArgName() {
		return argName;
	}
	public void setArgName(String argName) {
		this.argName = argName;
	}
	public String getValueExpression() {
		return valueExpression;
	}
	public void setValueExpression(String valueExpression) {
		this.valueExpression = valueExpression;
	}
	
	public String getCompareMethod() {
		return compareMethod;
	}
	public void setCompareMethod(String compareMethod) {
		this.compareMethod = compareMethod;
	}
	@Override
	public String toString() {
		return "ArgProp [fullType=" + fullType + ", argName=" + argName
				+ ", valueExpression=" + valueExpression + ", compareMethod="
				+ compareMethod + "]";
	}
    
         
   

}
