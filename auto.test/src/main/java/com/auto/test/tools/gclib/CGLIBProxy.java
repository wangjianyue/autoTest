/**
 * @author wjy
 * create at 2015-5-21
 */
package com.auto.test.tools.gclib;

import java.lang.reflect.Method;
import java.util.Arrays;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;


/**
 *   
 * Simple to Introduction  
 *
 * @ProjectName:  [springAop] 
 * @Package:      [cn.cubic.aop.cglib]  
 * @ClassName:    [CGLIBProxy]   
 * @Description:  [描述该类的功能]   
 * @Author:       [逍遥梦]   
 * @CreateDate:   [2014-3-1 下午4:47:22]   
 * @UpdateUser:   [逍遥梦]   
 * @UpdateDate:   [2014-3-1 下午4:47:22]   
 * @UpdateRemark: [说明本次修改内容]  
 * @Version:      [v1.0] 
 *
 */
public class CGLIBProxy implements MethodInterceptor{
    
     private Enhancer enhancer = new Enhancer();
    
     public Object getProxy(Class clazz){
          
          //设置父类
          enhancer.setSuperclass(clazz);
          enhancer.setCallback(this);
          
          //通过字节码技术动态创建子类实例
          return enhancer.create();
     }
    
    /**
     * 所有的方法都会被这个方法所拦截。该类实现了创建子类的方法与代理的方法。getProxy(SuperClass.class)方法通过入参即父类的字节码，通过扩展父类的class来创建代理对象。intercept()方法拦截所有目标类方法的调用，obj表示目标类的实例，method为目标类方法的反射对象，args为方法的动态入参，proxy为代理类实例。
     */
    @Override
    public Object intercept(Object obj, Method method, Object[] args,
            MethodProxy methodproxy) throws Throwable {
        
        long time = System.nanoTime();//纳秒  1毫秒=1000纳秒 
        for(Object arg : args){
            //JSONObject jsonObject = JSONObject.fromObject(arg);
            System.out.println(method.getParameterTypes()[0].getName());

        }
        System.out.println(Arrays.toString(args));
        
        //通过代理类调用父类中的方法
        Object result = methodproxy.invokeSuper(obj, args);
        
        System.out.println(method.getName()+"运行耗时："+(System.nanoTime()-time)/1000+"秒");
        
        
        return result;
    }

    
}