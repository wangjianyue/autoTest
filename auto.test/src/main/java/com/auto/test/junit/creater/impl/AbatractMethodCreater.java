/**
 * @author wjy
 * create at 2015-6-2
 */
package com.auto.test.junit.creater.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import javassist.NotFoundException;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.auto.test.aop.TestAnnotationAspect;
import com.auto.test.entity.ArgProp;
import com.auto.test.entity.MethodInfo;
import com.auto.test.entity.Property;
import com.auto.test.junit.creater.MethodCreater;
import com.auto.test.utils.BeanNameHelper;
import com.auto.test.utils.ParamNameGetter;
import com.auto.test.utils.ParamNameGetter.MissingLVException;


/**
 * @author wjy edit at 2015-6-2
 */
public abstract class AbatractMethodCreater implements MethodCreater {
	private static final Logger logger = Logger.getLogger(TestAnnotationAspect.class);

	  
	/**
	    * 通过AOP获取调用路径上的输入参数值
	    */
	   private String argValues = "[]";
	   /**
	    * 通过AOP获取调用路径上的输出结果值
	    */
	   private String rtnValue = "{}";


	class MethodTmpParseResult{
		private Class<?> bePasrsedClass;
		private Method method;
	    private ArrayList<Property> inputProp = new ArrayList<Property>();
		private Property outputProp;
		private String[] paramNames;
		public Class<?> getBePasrsedClass() {
			return bePasrsedClass;
		}
		public void setBePasrsedClass(Class<?> bePasrsedClass) {
			this.bePasrsedClass = bePasrsedClass;
		}
		public Method getMethod() {
			return method;
		}
		public void setMethod(Method method) {
			this.method = method;
		}
		public ArrayList<Property> getInputProp() {
			return inputProp;
		}
		public void setInputProp(ArrayList<Property> inputProp) {
			this.inputProp = inputProp;
		}
		public Property getOutputProp() {
			return outputProp;
		}
		public void setOutputProp(Property outputProp) {
			this.outputProp = outputProp;
		}
		public String[] getParamNames() {
			return paramNames;
		}
		public void setParamNames(String[] paramNames) {
			this.paramNames = paramNames;
		}
		@Override
		public String toString() {
			return "MethodTmpParseResult [bePasrsedClass=" + bePasrsedClass
					+ ", method=" + method + ", inputProp=" + inputProp
					+ ", outputProp=" + outputProp + ", paramNames="
					+ Arrays.toString(paramNames) + "]";
		}
		
		
		
		
	}
    
    
    
    private MethodTmpParseResult init(Method method) {
    	MethodTmpParseResult parseResult = initProp(method);
    	specialInit(method);
    	return initPramNames(parseResult);
    }
    
    /**
     * 支持子类根据方法信息进行特殊初始化
     * @param method
     */
    protected void specialInit(Method method) {
		
	}
    
    
    /**
     * 对输入输出值进行初始化
     * @param method
     */
    public void specialInit(String argValues, String rtnValue) {
    	this.argValues = argValues;
    	this.rtnValue = rtnValue;
    	
	}


	private MethodTmpParseResult initProp(Method method) {
    	MethodTmpParseResult result = new MethodTmpParseResult();
    	result.setMethod(method);
    	result.setBePasrsedClass(method.getDeclaringClass());
    	
    	ArrayList<Property> inputProp = new ArrayList<Property>();
    	
    	
        Type[] inputTypes = method.getGenericParameterTypes();
        Class<?>[] inputClass = method.getParameterTypes();
        Type outputType = method.getGenericReturnType();
        Class<?> outputClass = method.getReturnType();
        for (int i = 0; i < inputTypes.length; i++) {
            Property prop = Property.getInstance(inputTypes[i], inputClass[i]);            
			inputProp.add(prop);
        }
        
        result.setInputProp(inputProp);
        result.setOutputProp(Property.getInstance(outputType, outputClass));
        
        return result;
    }
    
    private MethodTmpParseResult initPramNames(MethodTmpParseResult parseResult) {
    	ArrayList<Property> inputProp = parseResult.getInputProp();
    	String[] paramNames = null;
    	Class<?>[] typeClasses = new Class[inputProp.size()];
        for (int i = 0; i < inputProp.size(); i++) {
            typeClasses[i] = inputProp.get(i).getTypeClass();
        }
        try {
            paramNames = ParamNameGetter.getMethodParamNames(parseResult.getBePasrsedClass(),
            		parseResult.getMethod().getName(), typeClasses);
            for (int i = 0; i < paramNames.length; i++) {
                paramNames[i] = paramNames[i] + "_var";
            }
        } catch (NotFoundException e) {
        	logger.debug("获取参数名称报错", e);
            paramNames = new String[0];
        } catch (MissingLVException e) {
        	logger.debug("获取参数名称报错", e);
            paramNames = new String[0];
        }
        parseResult.setParamNames(paramNames);
        
        return parseResult;
        
    }


    public MethodInfo buildMethodInfo(Method method) {
    	MethodTmpParseResult tmpParse = init(method);
        if (isNeedCreate(tmpParse)) {
        	MethodInfo result = new MethodInfo();
        	result.setMethodName(method.getName());
        	result.setPublic(Modifier.isPublic(method.getModifiers()));
        	result.setDeclaredExcetpion(getDeclaredExcetpionIfHave(method));
        	JSONObject expectedValue = getMethodExpectValue();
        	String input = expectedValue.getString("input");
        	JSONArray inputJsonArray = JSONArray.parseArray(input); 
        	Object[] inutJsonString = inputJsonArray.toArray();
        	List<ArgProp> inputProps = new ArrayList<ArgProp>();
        	for(int i = 0; i < tmpParse.getInputProp().size(); i++){
        		ArgProp arg = new ArgProp();
        		arg.setFullType(tmpParse.getInputProp().get(i).getFullType());
        		if(i < tmpParse.getParamNames().length - 1){
        			arg.setArgName(tmpParse.getParamNames()[i]);
        		}
        		else{
        			arg.setArgName("var_" + i);
        		}
        		
        		if(i < inutJsonString.length){
        			String inputValueExpression = getValueJsonExpression(tmpParse.getInputProp().get(i), inutJsonString[i].toString());
        			arg.setValueExpression(inputValueExpression);
        		}
        		else{
        			arg.setValueExpression(tmpParse.getInputProp().get(i).getTypeEnum().getDefalutValue());
        		}
        		inputProps.add(arg);    		
        	}
        	result.setInputProps(inputProps);
        	
        	ArgProp output = new ArgProp();
        	output.setArgName("exceptResult");
        	output.setFullType(tmpParse.getOutputProp().getFullType());
        	output.setValueExpression(getValueJsonExpression(tmpParse.getOutputProp(),expectedValue.getString("output")));  
        	output.setCompareMethod(tmpParse.getOutputProp().getTypeEnum().getCompareMethod());
        	result.setOutputProp(output);
        	
			return result;
        }

        return null;
    }
    

	 private String getValueJsonExpression(Property property, String JsonValueString) {
		return property.getTypeEnum().getJsonParseExpression(JsonValueString, property);
	}

	/**
     * 获取输入输出结果表达式{input:{参数名：参数值；参数名：参数值};output:值}
     * @return
     */
    protected JSONObject getMethodExpectValue(){
    	JSONObject expectedValueJson = new JSONObject();
    	expectedValueJson.put("input", argValues);
    	expectedValueJson.put("output", rtnValue);
    	
    	return expectedValueJson;
    }


	protected boolean isNeedCreate(MethodTmpParseResult tmpParse) {
    	if ("main".equals(tmpParse.getMethod().getName()) || isBeanGetSetMethod(tmpParse)) {
            return false;
        }
    	return true;  		
    }
    
    private boolean isBeanGetSetMethod(MethodTmpParseResult tmpParse){
        Field[] filds = tmpParse.getBePasrsedClass().getDeclaredFields();
        Set<String> getSetFieldNames = new HashSet<String>();
        for(Field field : filds){
            getSetFieldNames.addAll(getMethodFild(field));
            getSetFieldNames.add(setMethodFild(field));
        }
        return getSetFieldNames.contains(tmpParse.getMethod().getName());
    }

    private Set<String> getMethodFild(Field field) {
    	Set<String> getMethod = new HashSet<String>();
        if(field.getType() == boolean.class || field.getType() == Boolean.class){
        	getMethod.add("is"+BeanNameHelper.upercaseFirst(field.getName()));
        }
        getMethod.add("get"+BeanNameHelper.upercaseFirst(field.getName()));
        return getMethod;
    }
    
    private String setMethodFild(Field field) {
        return "set"+BeanNameHelper.upercaseFirst(field.getName());
    }


    // wjy edit at 2014-9-2
    public String getDeclaredExcetpionIfHave(Method method) {
        StringBuilder fileOutputString = new StringBuilder();
        Type[] exceptions = method.getGenericExceptionTypes();
        if (exceptions != null && exceptions.length > 0) {
            for (int i = 0; i < exceptions.length; i++) {
                if (i == 0) {
                    fileOutputString
                            .append(" throws "
                                    + Property.getInstance(exceptions[i])
                                            .getFullType());
                } else {
                    fileOutputString
                            .append(", "
                                    + Property.getInstance(exceptions[i])
                                            .getFullType());
                }
            }
        }
        return fileOutputString.toString();
    }
    

}
