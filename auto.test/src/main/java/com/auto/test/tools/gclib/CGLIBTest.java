/**
 * @author wjy
 * create at 2015-5-21
 */
package com.auto.test.tools.gclib;


/**
 * @author wjy
 * edit at 2015-5-21
 */
public class CGLIBTest {
    /**
     * @param args
     */
    public static void main(String[] args) {

        CGLIBProxy proxy = new CGLIBProxy();
        
        //生成子类，创建代理类
        CGLIBUserServiceImpl impl = (CGLIBUserServiceImpl)proxy.getProxy(CGLIBUserServiceImpl.class);
        impl.sayHello("test it");
        
    }


}
