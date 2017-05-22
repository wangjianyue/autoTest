package com.auto.test;

import java.math.BigDecimal;

import com.alibaba.fastjson.JSONObject;
import com.auto.test.entity.ArgProp;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        BigDecimal a = new BigDecimal("1111.232");
        System.out.println(a.setScale(0, BigDecimal.ROUND_UP));
        
    	ArgProp it_var = JSONObject.parseObject("{fullType:\"wjy\"}", ArgProp.class);
        System.out.println(it_var.getFullType());
        
        Integer it_var2 = JSONObject.parseObject("2", Integer.class);
        
        System.out.println(it_var2);
        
        String it_var3 = JSONObject.parseObject("\"aa\"", String.class);
        
        System.out.println(it_var3);
    }
}
