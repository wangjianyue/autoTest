/**
 * @author wjy
 * create at 2015-6-12
 */
package com.example.aop;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.auto.test.annotation.Test;

@Service
public class BService {
    @Resource
    private AService aService;
    /**
     * @param aService the aService to set
     */
    public void setAService(AService aService) {
        this.aService = aService;
    }
    
    
    @Test(input = "[\"aa\",\"1\"]", output = "[\"aa\",\"1\"]")
    public List<String> barB(String _msg, int _type) {  
        return aService.barA(_msg, _type);
    }  
  
    @Test(input = "[[\"a\",\"b\",\"c\"],{a:1,b:2,c:3}]", output = "[1,2,3]")
    public int[] fooB(String[] a, Map<String, Integer> b) {  
        return aService.fooA(a,b);        
        
    }  
  
}  

