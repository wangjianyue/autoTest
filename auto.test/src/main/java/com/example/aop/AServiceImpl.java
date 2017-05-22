/**
 * @author wjy
 * create at 2015-6-12
 */
package com.example.aop;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.auto.test.annotation.Mock;

/**
 * @author wjy
 * edit at 2015-6-12
 */

 // ʹ��jdk��̬����   
@Component
public class AServiceImpl implements AService {  
  
	@Mock
    public List<String> barA(String _msg, int _type) {  
        System.out.println("BServiceImpl.barB(msg:" + _msg + " type:" + _type + ")");  
        List<String> a =  new ArrayList<String>();
        a.add(_msg);
        a.add(""+_type);
        return a;
    }  
  
    public int[] fooA(String[] a, Map<String, Integer> b) {  
        List<Integer> result = new ArrayList<Integer>();
        int[] rr = new int[100];
        int i=0;
        for(String s : a){
            if(b.get(s) != null)
                rr[i++] = b.get(s);
        }
        
        return rr;
        
        
    }
}  


