package com.auto.test.utils;

import java.util.Arrays;

import org.apache.log4j.Logger;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.Modifier;
import javassist.NotFoundException;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;
  

public class ParamNameGetter { 
	private static final Logger logger = Logger.getLogger(ParamNameGetter.class);
	
    private ParamNameGetter(){
        throw new IllegalAccessError("can't create it");
    }
  
    public static void main(String[] args) throws Exception {   
        // 匹配静态方法   
        String[] paramNames = getMethodParamNames(ParamNameGetter.class, "main", String[].class);   
        logger.debug(Arrays.toString(paramNames));   
        // 匹配实例方法   
        paramNames = getMethodParamNames(ParamNameGetter.class, "foo", String.class);   
        logger.debug(Arrays.toString(paramNames));   
        // 自由匹配任一个重名方法   
        paramNames = getMethodParamNames(ParamNameGetter.class, "getMethodParamNames");   
        logger.debug(Arrays.toString(paramNames));   
        // 匹配特定签名的方法   
        paramNames = getMethodParamNames(ParamNameGetter.class, "getMethodParamNames", Class.class, String.class);   
        logger.debug(Arrays.toString(paramNames));   
    }
    
    
    /**  
     * 获取方法参数名称，按给定的参数类型匹配方法  
     *   
     * @param clazz  
     * @param method  
     * @param paramTypes  
     * @return  
     * @throws NotFoundException  
     *             如果类或者方法不存在  
     * @throws MissingLVException  
     *             如果最终编译的class文件不包含局部变量表信息  
     */  
    public static String[] getMethodParamNames(Class<?> clazz, String method, String... paramTypeNames)   
            throws NotFoundException, MissingLVException {   
  
        ClassPool pool = ClassPool.getDefault();   
        CtClass cc = pool.get(clazz.getName());   
        CtMethod cm = cc.getDeclaredMethod(method, pool.get(paramTypeNames));   
        return getMethodParamNames(cm);   
    }   
  
    /**  
     * 获取方法参数名称，按给定的参数类型匹配方法  
     *   
     * @param clazz  
     * @param method  
     * @param paramTypes  
     * @return  
     * @throws NotFoundException  
     *             如果类或者方法不存在  
     * @throws MissingLVException  
     *             如果最终编译的class文件不包含局部变量表信息  
     */  
    public static String[] getMethodParamNames(Class<?> clazz, String method, Class<?>... paramTypes)   
            throws NotFoundException, MissingLVException {   
  
        ClassPool pool = ClassPool.getDefault();   
        CtClass cc = pool.get(clazz.getName());   
        String[] paramTypeNames = new String[paramTypes.length];   
        for (int i = 0; i < paramTypes.length; i++)   {
            paramTypeNames[i] = paramTypes[i].getName();   
        }
        CtMethod cm = cc.getDeclaredMethod(method, pool.get(paramTypeNames));   
        return getMethodParamNames(cm);   
    }   
  
    /**  
     * 获取方法参数名称，匹配同名的某一个方法  
     *   
     * @param clazz  
     * @param method  
     * @return  
     * @throws NotFoundException  
     *             如果类或者方法不存在  
     * @throws MissingLVException  
     *             如果最终编译的class文件不包含局部变量表信息  
     */  
    public static String[] getMethodParamNames(Class<?> clazz, String method) throws NotFoundException, MissingLVException {   
  
        ClassPool pool = ClassPool.getDefault();   
        CtClass cc = pool.get(clazz.getName());   
        CtMethod cm = cc.getDeclaredMethod(method);   
        return getMethodParamNames(cm);   
    }   
  
    /**  
     * 获取方法参数名称  
     *   
     * @param cm  
     * @return  
     * @throws NotFoundException  
     * @throws MissingLVException  
     *             如果最终编译的class文件不包含局部变量表信息  
     */  
    protected static String[] getMethodParamNames(CtMethod cm) throws NotFoundException, MissingLVException {   
        CtClass cc = cm.getDeclaringClass();   
        MethodInfo methodInfo = cm.getMethodInfo();   
        String[] paramNames = new String[cm.getParameterTypes().length];  
        if(cc.isInterface()){
            for (int i = 0; i < paramNames.length; i++)   
                paramNames[i] = "arg"+i;   
        }
        else{
            CodeAttribute codeAttribute = methodInfo.getCodeAttribute(); 
            LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);   
            if (attr == null)   
                throw new MissingLVException(cc.getName());   
        
            int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;   
            for (int i = 0; i < paramNames.length; i++)   
                paramNames[i] = attr.variableName(i + pos);               
        }
        return paramNames; 
    }   
  
    /**  
     * 在class中未找到局部变量表信息<br>  
     * 使用编译器选项 javac -g:{vars}来编译源文件  
     *   
     * @author Administrator  
     *   
     */  
    public static class MissingLVException extends Exception {   
        //wjy edit at 2015-6-10
        private static final long serialVersionUID = -3046893086493554185L;
        static String msg = "class:%s 不包含局部变量表信息，请使用编译器选项 javac -g:{vars}来编译源文件。";   
  
        public MissingLVException(String clazzName) {   
            super(String.format(msg, clazzName));   
        }   
    }   
  
    static void foo() {   
    }   
  
    void foo(String bar) {   
    }   
}  