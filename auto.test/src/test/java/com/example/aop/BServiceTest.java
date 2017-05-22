/**                                                                        
 * Copyright(c) 2012~now, wjy          all rights reserved.         
 *                                                                          
 * This software is the confidential and proprietary information            
 * of wjy. You shall not disclose such Confidential        
 * Information and shall use it only in accordance with the terms           
 * of the license agreement you entered into with wjy.     
*/                                                                          
/**                                                                         
 * Copyright(c) 2012~now, wjy all rights reserved.         
 *                                                                          
 * This software is the confidential and proprietary information            
 * of wjy. You shall not disclose such Confidential        
 * Information and shall use it only in accordance with the terms           
 * of the license agreement you entered into with wjy.     
*/                                                                          
package com.example.aop;                                                
                                                                            
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import wjy.auto.test.BaseTest;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.auto.test.tools.fileproc.CompareTools;
                                                                            
                                                                       
                                                                            
public class BServiceTest extends BaseTest{                  
                                                                            
   @Autowired(required=false)                                               
   private BService bService;
   @Before                                                                  
   public void setUp() throws Exception{                                    
       super.setUp();                                                       
       System.out.println("start junit test bService....");       
    }

   @After                                                                   
   public void tearDown() throws Exception{                                 
       System.out.println("end junit test  bService....");       
       super.tearDown();                                                    
    
    }

                                                                     
    private void databasePrepareForTest(){                                  
       //TODO                                                               
    
    }
    
	@Test 
	@Rollback(true)
	public void barBTest()  {
	    System.out.println("start junit test method --------------- barB ----------------------");
	    
	    java.lang.String _msg_var = "aa";
	    int var_1 = 1;

	    java.util.List<java.lang.String> exceptResult = JSONObject.parseObject("[\"aa\",\"1\"]",new TypeReference<java.util.List<java.lang.String>>(){});
	  
		java.util.List<java.lang.String> callResult = 
	    			    bService.barB(_msg_var, var_1);
			
	    assertTrue(CompareTools.CompareArrays(callResult,exceptResult));
	}
	@Test 
	@Rollback(true)
	public void fooBTest()  {
	    System.out.println("start junit test method --------------- fooB ----------------------");
	    
	    java.lang.String[] a_var = JSONObject.parseObject("[\"a\",\"b\",\"c\"]",java.lang.String[].class);
	    java.util.Map<java.lang.String, java.lang.Integer> var_1 = JSONObject.parseObject("{\"a\":1,\"b\":2,\"c\":3}",new TypeReference<java.util.Map<java.lang.String, java.lang.Integer>>(){});

	    int[] exceptResult = JSONObject.parseObject("[1,2,3]",int[].class);
	  
		int[] callResult = 
	    			    bService.fooB(a_var, var_1);
			
	    assertTrue(CompareTools.CompareArrays(callResult,exceptResult));
	}

    
}
