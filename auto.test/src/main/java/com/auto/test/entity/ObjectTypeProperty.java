/**
 * @author wjy
 * create at 2015-6-11
 */
package com.auto.test.entity;

import java.lang.reflect.Type;

import com.auto.test.emun.TypeJsonEnum;

/**
 * @author wjy
 * edit at 2015-6-11
 */
public class ObjectTypeProperty extends Property{
    
    public ObjectTypeProperty(Type type, Class<?> typeClass){
        this.type = type;
        this.typeClass = typeClass;
        
        if(isString()){
            typeEnum = TypeJsonEnum.STRING;
        }
        else{
            typeEnum = TypeJsonEnum.OBJECT;
        }
        fullType = getStringbyType();
    }
    
    
    public boolean isString(){
        if("java.lang.String".equals(getStringbyType())){
            return true;
        }
        return false;
    }
}
