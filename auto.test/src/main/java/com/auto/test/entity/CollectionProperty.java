/**
 * @author wjy
 * create at 2015-6-11
 */
package com.auto.test.entity;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.auto.test.emun.TypeJsonEnum;


/**
 * @author wjy
 * edit at 2015-6-11
 */
public class CollectionProperty  extends Property{

    Property proType;
    List<Property> patternTypes = new ArrayList<Property>();
    
    
    public CollectionProperty(Type type, Class<?> typeClass){
        this.type = type;
        this.typeClass = typeClass;
  
        ParameterizedType itType = (ParameterizedType)type;           
        proType = Property.getInstance(itType.getRawType());        
        Type[] parmClass = itType.getActualTypeArguments();
        for(Type subType :parmClass){
            patternTypes.add(Property.getInstance(subType));
        } 
        
        if("java.util.Map".equals(proType.fullType)){
            typeEnum = TypeJsonEnum.MAP;
        }
        else{
            typeEnum = TypeJsonEnum.COLLECTION;
        }
        fullType = getStringbyType();
     }
    
    public String getStringbyType(){
        if(proType == null){
            return null;
        }
        String typeName = proType.getStringbyType();
        if(patternTypes.size() > 0){
            StringBuilder sb = new StringBuilder();
            sb.append("<").append(patternTypes.get(0).getStringbyType());
            for(int i = 1; i < patternTypes.size(); i++){
                sb.append(", ").append(patternTypes.get(i).getStringbyType());
            }
            sb.append(">");
            typeName = typeName + sb.toString();
        }
        return typeName;
    }
    
    public boolean isCollection(){
        return true;
    }
    
    public String getProTypeName(){
        return proType.getFullType();
    }
    
}
