/**
 * @author wjy
 * create at 2014-8-6
 */
package com.auto.test.tools.fileproc;

import java.lang.reflect.Field;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.auto.test.tools.fileproc.impl.ClassFileProc;

/**
 * @author wjy
 * edit at 2014-8-6
 */
public class CompareTools {
	private static final Logger logger = Logger.getLogger(CompareTools.class);

    
    public static boolean CompareObject(Object aObject,Object bObject){
        if (null == aObject || null == bObject){
            if(null == aObject && null == bObject){
                return true;
            }
            return false;
        }
        logger.debug(JSONObject.toJSON(aObject));
        logger.debug(JSONObject.toJSON(bObject));
        return JSONObject.toJSON(aObject).equals( JSONObject.toJSON(bObject));


    }
    
    public static boolean CompareArrays(Object aObject,Object bObject){
        if (null == aObject || null == bObject){
            if(null == aObject && null == bObject){
                return true;
            }
            return false;
        }
        logger.debug(JSONArray.toJSON(aObject));
        logger.debug(JSONArray.toJSON(bObject));
        return JSONArray.toJSON(aObject).equals( JSONArray.toJSON(bObject));


    }
    
    public static boolean ComparePrimitive(Object aObject,Object bObject){
        return aObject == bObject;
    }
    
    public static boolean CompareString(Object aObject,Object bObject){
        if (null == aObject || null == bObject){
            if(null == aObject && null == bObject){
                return true;
            }
            return false;
        }
        return ((String)aObject).equals((String) bObject);
    }
    
    public static boolean Compare(Class<?> aClass, Object aObject, Class<?> bClass, Object bObject){
        if (null == aObject || null == bObject){
            if(null == aObject && null == bObject){
                return true;
            }
            return false;
        }
        if (!aClass.getName().equals(bClass.getName())){
            return false;
        }
        return Serialize(aObject, aClass).equals(Serialize(bObject, bClass));


    }
    
    public static String Serialize(Object obj, Class<?> objClass)
    {
        if (null == obj){
            return "";
        }
        Field[] fields = objClass.getDeclaredFields();
        StringBuilder objString = new StringBuilder();
        for(Field field : fields)
        {
            objString.append(field.getName() + ":");
            field.setAccessible(true);
            Object filedValue = null;
            try {
                filedValue = field.get(obj);
            } catch (IllegalArgumentException e) {
            	logger.debug("比较方法报错",e);
            } catch (IllegalAccessException e) {
            	logger.debug("比较方法报错",e);
            }
            Class<?> fildType = field.getType();
            if (null != filedValue){
                if(fildType.isPrimitive()){
                    objString.append(filedValue);
                }
                else if("java.lang.String".equals(fildType.getName())){
                    objString.append(filedValue);
                }
                else{
                    objString.append(Serialize(field, fildType));
                }
                
            }
            objString.append(";");
        }
        return objString.toString();
    }

}
