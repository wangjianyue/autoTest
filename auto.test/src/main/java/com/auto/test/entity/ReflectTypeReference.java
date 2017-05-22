package com.auto.test.entity;

import java.lang.reflect.Type;

import com.alibaba.fastjson.TypeReference;

public class ReflectTypeReference<T> extends TypeReference<T>{
	 	private Type type;

	    public ReflectTypeReference(){
	        //super();	        
	    }
	    
	    public ReflectTypeReference(Type type){
	        this.type = type;        
	    }

	    public Type getType() {
	        return type;
	    }


}
