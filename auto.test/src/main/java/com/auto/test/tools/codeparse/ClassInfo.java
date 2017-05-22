/**
 * @author wjy
 * create at 2014-8-22
 */
package com.auto.test.tools.codeparse;

import java.util.ArrayList;
import java.util.List;


/**
 * @author wjy
 * edit at 2014-8-22
 */
public class ClassInfo extends BaseCodeStruct{
    
    private String className = "";
    private List<FunctionInfo> functions = new ArrayList<FunctionInfo>();
    List<String> classBlock;
    CodeSourceBlocker classBlocker = new CodeSourceBlocker();
    
    public ClassInfo(List<String> classContents){
        classBlock = classContents;
        parseTitle(classContents);

        spliteContentsToThreePart(classContents);
        className = codeTitle;
        parseForClass();
    }

    //wjy edit at 2014-8-22
    private void parseForClass() {       
        
        classBlocker.parseConetentsToBlocks(codeContents.toArray(new String[0]));      
        
        for(List<String> functionBlock : classBlocker.braceBlocks){
            FunctionInfo functionInfo = new FunctionInfo(functionBlock);
            functions.add(functionInfo);
        }
        
    }
    
    public void CombinConetent(ClassInfo otherClassInfo){

        for(FunctionInfo otherFuction : otherClassInfo.functions){
            if(isHavingThisFunctonAlready(otherFuction)){
                FunctionInfo thisFunction = getHavingThisFunctonAlready(otherFuction);
                thisFunction.CombineFucntions(otherFuction);
            }
            else{
                functions.add(otherFuction);
            }
        }
        
        List<String> newCodeContents = new ArrayList<String>();
        for(List<String> semicolonBlock : classBlocker.semicolonBlocks){
            newCodeContents.addAll(semicolonBlock);
        }
        for(FunctionInfo function: functions){
            newCodeContents.add(function.printCodeInfo());
        }
        codeContents = newCodeContents;
    }
    
    private boolean isHavingThisFunctonAlready(FunctionInfo inputFucntion){
        if(inputFucntion == null){
            return false;
        }
        for(FunctionInfo fuction : functions){
            if(fuction.getFunctionName().equals(inputFucntion.getFunctionName())){
                return true;
            }
        }
        return false;
    }
    
    private FunctionInfo getHavingThisFunctonAlready(FunctionInfo inputFucntion){
        if(inputFucntion == null){
            return null;
        }
        for(FunctionInfo fuction : functions){
            if(fuction.getFunctionName().equals(inputFucntion.getFunctionName())){
                return fuction;
            }
        }
        return null;
    }
    
    public String getClassName(){
        return className;
    }

   
    @Override
    String appendBrace() {
        return "}";
    }

}
