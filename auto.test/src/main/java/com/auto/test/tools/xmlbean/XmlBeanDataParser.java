package com.auto.test.tools.xmlbean;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class XmlBeanDataParser implements BeanDataParser {
	
	private static final SAXReader saxReader = new SAXReader();  
	
	private Document document;
	private final File xmlFile;
	private Class<?> classInfo;
	
	public XmlBeanDataParser(File xmlFile){
		this.xmlFile = xmlFile;
		init();
	}
	
	public void init(){
		if(xmlFile != null){
			try {
				document = saxReader.read(xmlFile);
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public Class<?> getClassInfo() {
		if(document == null){
			init();
		}
		if(classInfo == null){		
			Element classInf = document.getRootElement().element("class");
            try {
				classInfo = Class.forName(classInf.attributeValue("value").trim());
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return classInfo;
	}

	@Override
	public List<BeanEntity> getBeanEntitys() {
		List<BeanEntity> beanEntitys = new ArrayList<BeanEntity>();
		Element datas = document.getRootElement().element("datas");       
        List<?> dataList = datas.elements();
        for (int i = 0; i < dataList.size(); i++) {       	 
        	 Element dataInfo = (Element) dataList.get(i);
        	 String beanId = dataInfo.attributeValue("id");            
             Object beanData = null;
			try {
				beanData = parseProps(dataInfo);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				throw new RuntimeException(e);
			}
             BeanEntity beanEntity = new BeanEntity(beanId, beanData);
             beanEntitys.add(beanEntity);
        }
        return beanEntitys;
	}
	
	private Object parseProps(Element dataInfo) throws InstantiationException, IllegalAccessException, NoSuchFieldException, SecurityException {
		Object bean = getClassInfo().newInstance();  
		@SuppressWarnings("unchecked")
        List<Element> props = dataInfo.elements();
        for(Element prop : props){
            String name = prop.attributeValue("name").trim();
            String value = prop.attributeValue("value").trim();  
            Field field = getClassInfo().getDeclaredField(name);
            parseFildValue(bean, field, value);         
        }
        return bean;
		
	}


	private void parseFildValue(Object bean, Field field, String value) throws IllegalArgumentException, IllegalAccessException {
		field.setAccessible(true);
		if("class java.lang.Integer".equals(field.getType().toString())){
           setFieldValueDealNull(bean, field, value);                                         
       }
       else if("class java.lang.Long".equals(field.getType().toString())){
          setFieldValueDealNull(bean, field, value);
       }
       else if("class java.lang.Double".equals(field.getType().toString())){
          setFieldValueDealNull(bean, field, value);
       }
       else if("class java.sql.Timestamp".equals(field.getType().toString())){
          setFieldValueDealNull(bean, field, value);
       }
       else if("class java.util.Date".equals(field.getType().toString())){
          setFieldValueDealNull(bean, field, value);
       }
       else{         
          field.set(bean, value); 
       }
		
	}
	
	private void setFieldValueDealNull(Object bean, Field field, String value) throws IllegalArgumentException, IllegalAccessException {
        if("null".equals(value.toString())){
            field.set(bean, null);                          
        }
        else{
           field.set(bean, value); 
        }
        
    }
	
	@Override
	public void putBeaninToFile(BeanEntity beanEntity, Class<?> beanClass){
	    String content ="";
        try {
            content = beanToFilesContents(beanEntity, beanClass);
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IntrospectionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	    try {
            writeFileAppend(content, beanEntity.getBeanId());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}
	
	private String beanToFilesContents(BeanEntity beanEntity, Class<?> beanClass) throws IntrospectionException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {

        StringBuffer beanContents = new StringBuffer();
        
        if(!xmlFile.exists()){
            beanContents.append("<bean>\n");
            beanContents.append("   <datas>\n");
        }
        
        beanContents.append("       <data id=\""+beanEntity.getBeanId()+"\">\n");        
        Field[] allFields = beanClass.getDeclaredFields();
        for(Field field : allFields){
            PropertyDescriptor pd = new PropertyDescriptor(field.getName(),beanClass);
            Method getMethod = pd.getReadMethod();//获得get方法
            Object value = getMethod.invoke(beanEntity.getBeanObject());//执行get方法返回一个Object
            beanContents.append("           <prop name=\""+field.getName()+"\" value=\""+value+"\" />\n");
            field.getName();
        }        
        beanContents.append("       </data>\n");
        beanContents.append("   </datas>\n");
        beanContents.append("</bean>\n");
        return beanContents.toString();
	}
	
	private void writeFileAppend(String conent, String beanName) throws IOException {   
        BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(xmlFile), "UTF-8")); 

        StringBuilder fileFormateString = new StringBuilder();
        
        String readLine = br.readLine();
        boolean replace = false;
        while(readLine != null){
            if("<data".startsWith(readLine.trim())){
                if(readLine.trim().contains("id=\"" + beanName +"\"")){
                    replace = true;
                }
            }
            if(readLine.trim().contains("</data>")){
                replace = false;
            }
            if((!"</datas>".startsWith(readLine.trim()) || !"</bean>".startsWith(readLine.trim())) && !replace){
                fileFormateString.append(readLine);
                fileFormateString.append("\n");
            }
            readLine = br.readLine();
        }
        
        String oldFileContent = fileFormateString.toString().replaceAll("</datas>", "");
        oldFileContent = oldFileContent.replaceAll("</bean>", "");
        
        conent = oldFileContent + conent;
        
        BufferedWriter out = null;   
        try {
            
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(xmlFile), "UTF-8"));   
            out.write(conent);
            out.flush(); 
        } catch (Exception e) {   
            e.printStackTrace();   
        } finally {   
            try {   
                if(out != null){
                    out.close();   
                }
            } catch (IOException e) {   
                e.printStackTrace();   
            }   
        }   
    }   


}
