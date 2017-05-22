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
public class BaseTypeProperty extends Property{
    
    public BaseTypeProperty(Type type, Class<?> typeClass){
        this.type = type;
        this.typeClass = typeClass;
        setTypeEnum();
        fullType = getStringbyType();
    }

    public boolean isBaseType(){
        return true;
    }
    private void setTypeEnum(){
        if("java.lang.Boolean".equals(getStringbyType()) || "boolean".equals(getStringbyType())){
            typeEnum = TypeJsonEnum.BOOLEAN;             
        }else{
            typeEnum = TypeJsonEnum.NUM;
        }
    }
    
    public String getPackageingClassNameIfBaseType(){
        if(Integer.TYPE == ((Class<?>)type)){
            return "Integer";
        }
        else if(Double.TYPE== ((Class<?>)type)){
            return "Double";
        }
        else if(Float.TYPE== ((Class<?>)type)){
            return "Float";
        }
        else if(Long.TYPE== ((Class<?>)type)){
            return "Long";
        }
        else if(Short.TYPE== ((Class<?>)type)){
            return "Short";
        }
        else if(Boolean.TYPE== ((Class<?>)type)){
            return "Boolean";
        }
        else if(Byte.TYPE== ((Class<?>)type)){
            return "Byte";
        }
        else if(Character.TYPE== ((Class<?>)type)){
            return "Character";
        }
        else{
            return "";
        }
    }
    
}
