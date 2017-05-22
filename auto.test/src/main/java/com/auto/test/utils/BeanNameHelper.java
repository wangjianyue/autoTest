package com.auto.test.utils;

import org.apache.log4j.Logger;

import com.auto.test.aop.TestAnnotationAspect;


     
public class BeanNameHelper { 
	private static final Logger logger = Logger.getLogger(BeanNameHelper.class);

    private BeanNameHelper(){
        throw new IllegalAccessError("can't create it");
    }
    
    public static void main(String[] args) {
        String line = ("com\ncreate\ntest\ndemo.input.run");
        logger.debug(line);
        String[] a = line.split("\n");
        logger.debug(a.length);
     }
  
    public static String lowercaseFirst(String sequence) {
        if (sequence != null && sequence.length() > 0) {
            return sequence.substring(0, 1).toLowerCase()
                    + sequence.substring(1);
        }
        return sequence;
    }   
    
    public static String upercaseFirst(String sequence) {
        if (sequence != null && sequence.length() > 0) {
            return sequence.substring(0, 1).toUpperCase()
                    + sequence.substring(1);
        }
        return sequence;
    } 
    
    public static String getShortName(String javaName) {
        String[] nameItem = javaName.split("\\.");
        return nameItem[nameItem.length - 1];
    }
    public static String getFileDirFromClassName(String fullClassName){
        int index = fullClassName.lastIndexOf(".");
        String packageInfo = fullClassName.substring(0,index);
        return packageInfo.replaceAll("\\.", "\\/")+"/";
    }
    
    public static String getPackageString(String fullClassName){
        int index = fullClassName.lastIndexOf(".");
        return fullClassName.substring(0,index);

    }
}  