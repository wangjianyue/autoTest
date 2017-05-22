/**
 * @author wjy
 * create at 2014-8-25
 */
package com.auto.test.tools.codeparse;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author wjy
 * edit at 2014-8-25
 */
public abstract class BaseCodeStruct {
    protected String codeTitle = "";
    protected List<String> codeHead = new ArrayList<String>();
    protected List<String> codeContents = new ArrayList<String>();
    protected List<String> codeTail = new ArrayList<String>();
    
    enum ClassPart {
        Head,
        Contents,
        Tail
    };
    
    public void parseTitle(List<String> codeBlock){
        Stack<String> stack = new Stack<String>();
        for(int i = 0 ; i < codeBlock.size(); i++){  
            String line = codeBlock.get(i);
            if(line.contains("{") && stack.isEmpty()){
                String[] items = line.split("\\{",2);
                codeTitle = codeTitle + items[0];
                break;
            }
            if(line.contains("/*") && stack.isEmpty()){
                stack.push("/*");
            }
            if(line.contains("*/")){
                stack.pop();
            }
            if(!line.contains("@") && !line.contains("//") && stack.isEmpty()){
                codeTitle = codeTitle + codeBlock.get(i);
            }
        }
        codeTitle = formateWithSpace(codeTitle.trim());
    }
    
    
    abstract String appendBrace();
    
    public void spliteContentsToThreePart(List<String> codeBlock) {
        
        ClassPart partInfo = ClassPart.Head;
        Stack<String> stack = new Stack<String>();
        
        
        for(int i = 0 ; i < codeBlock.size(); i++){
            
            if(partInfo == ClassPart.Head){
                codeHead.add(codeBlock.get(i));
            }
            else if(partInfo == ClassPart.Contents){
                codeContents.add(codeBlock.get(i));
            }
            else if(partInfo == ClassPart.Tail){
                codeTail.add(codeBlock.get(i));
            }
            
            if(codeBlock.get(i).trim().endsWith("{")){
                if(stack.isEmpty() && partInfo == ClassPart.Head){
                    partInfo = ClassPart.Contents;
                }
                stack.push("{");
            }
                       
            
            if(codeBlock.get(i).trim().endsWith("}")){
                stack.pop();
                if(stack.isEmpty() && partInfo == ClassPart.Contents){
                    removeContentsLastBrace();
                    partInfo = ClassPart.Tail;
                    codeTail.add(appendBrace());
                }
            }            
            
        }

    
    }
    
    //wjy edit at 2014-8-22
    private void removeContentsLastBrace() {
        int size = codeContents.size();
        String lastLine = codeContents.get(size - 1);
        if("}".equals(lastLine.trim())){
        	codeContents.remove(size - 1);
        }
        else{
	        String withOutLastBrace = lastLine.substring(0,lastLine.lastIndexOf('}'));
	        codeContents.set(size-1, withOutLastBrace);
        }
    }


    private String formateWithSpace(String readLine) {
        String beFormatedString = readLine.trim();
        while(beFormatedString.contains("  ")){
            beFormatedString = beFormatedString.replaceAll("  ", " ");
        }               
        return beFormatedString+" ";
    }
    
    public String printCodeInfo(){
        StringBuilder sb = new StringBuilder();
        for(String line : codeHead){
            sb.append(line).append("\n");
        }
        for(String line : codeContents){
            sb.append(line).append("\n");
        }
        for(String line : codeTail){
            sb.append(line);
        }
        return sb.toString();
    }
    
    
}
