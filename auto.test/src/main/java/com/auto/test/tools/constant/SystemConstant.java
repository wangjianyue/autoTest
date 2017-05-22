/**
 * @author wjy
 * create at 2014-9-2
 */
package com.auto.test.tools.constant;

import java.io.FileInputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.auto.test.aop.TestAnnotationAspect;

/**
 * @author wjy
 * edit at 2014-9-2
 */
public class SystemConstant {
	private static final Logger logger = Logger.getLogger(SystemConstant.class);

    public static  String DEFAULT_COVER_TYPE;
    public static  String DEFAULT_JUNIT_CREATE;
    
    static {
        Properties prop=new Properties();        
        try {
            //System.out.println(System.getProperty("user.dir")+"/autoJunitBuild.properties");
            prop.load(new FileInputStream(System.getProperty("user.dir")+"/autoJunitBuild.properties"));
            DEFAULT_COVER_TYPE = prop.getProperty("default_cover_type");
            DEFAULT_JUNIT_CREATE = prop.getProperty("default_junit_create");           
        } catch (Exception e) {
        	logger.debug("获取配置信息报错", e);
            DEFAULT_COVER_TYPE = "COMBINE";
            DEFAULT_JUNIT_CREATE = "ALL_FUNCTIONS";
        }
    }  
    
}
