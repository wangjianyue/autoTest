/**
 * @author wjy
 * create at 2015-6-11
 */
package com.auto.test.entity;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;

import com.auto.test.emun.TypeJsonEnum;


/**
 * @author wjy
 * edit at 2015-6-11
 */
public class ArrayProperty extends Property {
    Property proType;
    
    public ArrayProperty(Type type, Class<?> typeClass){
        this.type = type;
        this.typeClass = typeClass;
        
        Type subType = null;
        if(type instanceof GenericArrayType){
            GenericArrayType itType = (GenericArrayType) type; 
            subType  = itType.getGenericComponentType();
        }
        else{
            subType = ((Class<?>)type).getComponentType();
        }
        proType = Property.getInstance(subType);
        
        typeEnum = TypeJsonEnum.ARRAY;
        
        fullType = getStringbyType();
        
    }
    
    public String getStringbyType(){
        return proType == null ? null : proType.getStringbyType()+"[]";
    }
    
    public boolean isArray(){
        return true;
    }
    
    public String getProTypeName(){
        return proType.getFullType();
    }
}
