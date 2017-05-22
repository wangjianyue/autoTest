/**
 * @author wjy
 * create at 2014-8-5
 */
package com.auto.test.entity;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.auto.test.emun.TypeJsonEnum;



/**
 * @author wjy
 * edit at 2014-8-5
 */
public  abstract class Property {
    
    protected Type type;
    protected Class<?> typeClass;
    protected String fullType;
    protected TypeJsonEnum typeEnum;
    
         
    public String getStringbyType(){
        return ((Class<?>)type).getName(); 
    }
    
    public static Property getInstance(Type type){
        return getInstance(type, Object.class);
    }
    
    public static Property getInstance(Type type, Class<?> typeClass){
        if(isArray(type)){
            return new ArrayProperty(type, typeClass);
        }
        else if(isCollection(type)){
            return new CollectionProperty(type, typeClass);
        }
        else if(isBaseType(type)){
            return new BaseTypeProperty(type, typeClass);
        }
        else{
           return new ObjectTypeProperty(type, typeClass);
        }
  
    }
    
    
  //wjy edit at 2014-8-20
    private static boolean isCollection(Type type) {       
        if(type instanceof ParameterizedType){
            return true;
        }
        return false;
    }
    
    
    //wjy edit at 2014-8-20
    private static boolean isArray(Type type) {
        if(type instanceof GenericArrayType){
            return true;
        }
        else if(type instanceof Class && (((Class<?>)type).isArray())){
            return true;
        }
        return false;
    }
    
    private static boolean isBaseType(Type type){
        if(!isArray(type) && !isCollection (type) && type instanceof Class && ((Class<?>)type).isPrimitive()){
            return true;
        }
        return false;
    }
    
    
    
    public boolean isBaseType(){
        return false;
    }
    
    public boolean isArray(){
        return false;
    }
    
    public boolean isCollection(){
        return false;
    }
    
    public boolean isString(){
        return false;
    }
    
    
    public String getPackageingClassNameIfBaseType(){       
        throw new Error("can't use this function if is not base type!!");
    }
    
    public String getProTypeName(){
        return getFullType();
    }
    
    public String getFullType(){        
        return fullType;
    }
    
    public Class<?> getTypeClass(){
        return typeClass;
    }
    
    public TypeJsonEnum getTypeEnum() {
        return typeEnum;
    }

}
