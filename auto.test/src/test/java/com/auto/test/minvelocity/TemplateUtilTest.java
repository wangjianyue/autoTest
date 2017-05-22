package com.auto.test.minvelocity;
 
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.apache.log4j.Logger;

import com.github.pfmiles.minvelocity.TemplateUtil;
import com.github.pfmiles.org.apache.velocity.Template;
 
public class TemplateUtilTest extends TestCase {
	private static final Logger logger = Logger.getLogger(TemplateUtilTest.class);

    public void testRenderStringTemp() {
        String templateString = "#foreach($i in $list)\n$i\n#end";
        Map<String, Object> ctxPojo = new HashMap<String, Object>();
        List<String> list = new ArrayList<String>();
        list.add("one");
        list.add("two");
        list.add("three");
        ctxPojo.put("list", list);
        StringWriter out = new StringWriter();
        TemplateUtil.renderString(templateString, ctxPojo, out);
        logger.debug(out.toString());
        assertTrue("one\ntwo\nthree\n".equals(out.toString()));
    }
    
   /* public void testVelocityString(){
    	InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("config/spring_junit_template.vm");
    	BufferedReader reader = new BufferedReader(new InputStreamReader(in));
    	StringBuilder sb = new StringBuilder();          
        
       String line = null;      
	   try {      
	       while ((line = reader.readLine()) != null) {      
	            sb.append(line);      
	        }      
	    } catch (IOException e) {      
	    	logger.debug("获取模板文件报错",e);
	    } finally {      
	       try {      
	            in.close();      
	        } catch (IOException e) {      
	        	logger.debug("获取模板文件报错",e); 
	        }      
        }      
	   String templateString = sb.toString();
       Map<String, Object> ctxPojo = new HashMap<String, Object>();
       List<ArgProp> list = new ArrayList<ArgProp>();
       ArgProp first = new ArgProp();
       first.setArgName("arg1");
       first.setFullType("int");
       first.setValueExpression("10");
       list.add(first);
       ArgProp second = new ArgProp();
       second.setArgName("arg2");
       second.setFullType("String");
       second.setValueExpression("\"aa\"");
       list.add(second);      
       ctxPojo.put("inputProps", list);
       
       ArgProp outputProp = new ArgProp();
       outputProp.setArgName("output");
       outputProp.setFullType("String");
       outputProp.setValueExpression("\"bb\"");
       ctxPojo.put("outputProp", outputProp);
       
       ctxPojo.put("methodName", "testVelocity");
       ctxPojo.put("beanName", "testBean");
       
       
       StringWriter out = new StringWriter();
       TemplateUtil.renderString(templateString, ctxPojo, out);
       System.out.println(out.toString());

    }*/
 
    public void testRenderTemplate() {
        Template temp = TemplateUtil.parseStringTemplate("#foreach($i in $list)\n$i\n#end");
        Map<String, Object> ctxPojo = new HashMap<String, Object>();
        List<String> list = new ArrayList<String>();
        list.add("one");
        list.add("two");
        list.add("three");
        ctxPojo.put("list", list);
        StringWriter out = new StringWriter();
        TemplateUtil.renderTemplate(temp, ctxPojo, out);
        assertTrue("one\ntwo\nthree\n".equals(out.toString()));
    }
 
    public void testRefRendering() {
        Template temp = TemplateUtil.parseStringTemplate("hello $ref world");
        Map<String, Object> ctxPojo = new HashMap<String, Object>();
        StringReader stream = new StringReader("1234567890");
        ctxPojo.put("ref", stream);
        StringWriter writer = new StringWriter();
        TemplateUtil.renderTemplate(temp, ctxPojo, writer);
        System.out.println(writer);
        assertTrue("hello 1234567890 world".equals(writer.toString()));
    }
}