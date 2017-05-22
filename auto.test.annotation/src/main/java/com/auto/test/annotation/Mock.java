package com.auto.test.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 测试结果标注
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Mock {
    
   
    /**
     * 输入参数值
     *
     */
     String  input() default  "";
    
   
     /**
      *  输入类型
      *  json or xml 
      */
     BeanType inputType() default BeanType.JSON;
     
     /**
      * 输出结果
      */
      String  output() default  "";
      
      /**
       * 输出类型
       * json or xml
       */
      BeanType outputType() default BeanType.JSON;
      
      
      /**
       * 是否录入及重现
       */      
      boolean recordReplay() default true;
}