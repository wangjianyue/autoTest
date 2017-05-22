/**
 * @author wjy
 * create at 2015-6-4
 */
package com.auto.test.emun;

import java.lang.reflect.Method;

import com.auto.test.entity.Property;
import com.auto.test.entity.ReflectTypeReference;



/**
 * @author wjy edit at 2015-6-4
 */
public enum TypeJsonEnum {
    ARRAY("JSONObject", "parseObject", "[]", "CompareArrays",true), 
    MAP("JSONObject", "parseObject", "{}", "CompareObject",true), 
    COLLECTION("JSONObject", "parseObject", "[]", "CompareArrays",true), 
    NUM("", "", "0", "ComparePrimitive",false),
    BOOLEAN("", "", "false", "ComparePrimitive",false),
    STRING("\"", "\"", "", "CompareString",false),
    OBJECT("JSONObject", "parseObject", "{}", "CompareObject",true);

    private String jsonClass;
    private String jsonParseMethod;

    private String defalutValue;
    private String CompareMethod;
    
    private boolean needJsonParse; 


    /**
     * @return the jsonClass
     */
    public String parseObjectTJson(Object obj) {
        if(needJsonParse){
            try {
                Class<?> a = Class.forName("net.sf.json."+jsonClass);
                Method method = a.getMethod("fromObject",Object.class);
                Object result = method.invoke(null,obj);
                
                return result.toString();
                
            } catch (Exception e) {
                //e.printStackTrace();
                //System.out.println("Json parse failed! return without parse result");
                return obj.toString();
            }
        }
        else{
            return obj.toString();
        }
    }



    /**
     * @return the jsonParseMethod
     */
    public String getJsonParseExpression(String jsonString, Property property) {
        if(needJsonParse){
            jsonString = jsonString.replaceAll("\\\"", "\\\\\"");
            StringBuilder sb = new StringBuilder(jsonClass);
            sb.append(".").append(jsonParseMethod).append("(");
            sb.append("\"").append(jsonString).append("\"," );
            if(this == ARRAY){
            	sb.append(property.getProTypeName()).append("[].class)");
            }
            else if(this == COLLECTION){
            	sb.append("new TypeReference<").append(property.getFullType()).append(">(){})");
            }
            else if(this == MAP){
            	sb.append("new TypeReference<").append(property.getFullType()).append(">(){})");
            }
            else{
            	sb.append(property.getProTypeName()).append(".class)");
            }
            		
            return sb.toString();
        }
        else{
        	if(this == STRING){
        		if(jsonString.startsWith("\"")){
        			return jsonString;
        		}
        	}
            return jsonClass + jsonString + jsonParseMethod+"";
        }

    }
    

    /**
     * @return the defalutValue
     */
    public String getDefalutValue() {
        return defalutValue;
    }

    /**
     * @return the compareMethod
     */
    public String getCompareMethod() {
        return CompareMethod;
    }

    private TypeJsonEnum(String jsonClass, String jsonParseMethod,
            String defalutValue, String CompareMethod, boolean needJsonParse) {
        this.jsonClass = jsonClass;
        this.jsonParseMethod = jsonParseMethod;
        this.defalutValue = defalutValue;
        this.CompareMethod = CompareMethod;
        this.needJsonParse = needJsonParse;
    }
}
