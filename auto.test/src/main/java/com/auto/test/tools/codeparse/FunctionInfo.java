/**
 * @author wjy
 * create at 2014-8-22
 */
package com.auto.test.tools.codeparse;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.auto.test.tools.constant.SystemConstant;

/**
 * @author wjy
 * edit at 2014-8-22
 */
public class FunctionInfo extends BaseCodeStruct {
    
    private String functionName;

    List<String> functionBlock;
    
    public FunctionInfo(List<String> functionBlock){
        this.functionBlock = functionBlock;
        parseTitle(functionBlock);
        spliteContentsToThreePart(functionBlock);
        functionName = codeTitle;
    }
    

    public void CombineFucntions(FunctionInfo otherFunction){
        for(String line : otherFunction.codeHead){
            if(line.contains("coverType=com.auto.test.annotation.CoverType.NO_COVER")){
                return;
            }
            else if(line.contains("coverType=com.auto.test.annotation.CoverType.OVERRIDE")){
                overrideContents(otherFunction);
                return ;
            }
            else if(line.contains("coverType=com.auto.test.annotation.CoverType.ALL_COVER")){
                allCover(otherFunction);
                return ;
            }
            else if(line.contains("coverType=com.auto.test.annotation.CoverType.COMBINE")){
                CombinConetent(otherFunction);
                return ;
            }
        }
        dealDefualtCombine(otherFunction);
    }
      



    //wjy edit at 2014-8-25
    private void overrideContents(FunctionInfo otherFunction) {
        codeContents = otherFunction.codeContents;
        
    }
    
    //wjy edit at 2014-8-25
    private void allCover(FunctionInfo otherFunction) {
        codeHead = otherFunction.codeHead;
        codeContents = otherFunction.codeContents;
        codeTail = otherFunction.codeTail;    
    }
    
    

    private void CombinConetent (FunctionInfo otherFunction){
        if(!isFuntionContentsContains(otherFunction)){
            for(int i = 0; i < codeContents.size(); i++){
                String functionCode = codeContents.get(i);
                functionCode = functionCode.replaceAll("var", "var1");
                functionCode = functionCode.replaceAll("exceptResult", "exceptResult1");
                functionCode = functionCode.replaceAll("callResult", "callResult1"); 
                codeContents.set(i, functionCode);
            }
            codeContents.add("\n\n        // new Contents to be combined at time: " + new Date()+"\n");
            codeContents.addAll(otherFunction.codeContents);
        }
    }
    
    private boolean isFuntionContentsContains(FunctionInfo otherFunction){
        List<String> thisContents = getRemoveBlackLine(codeContents);
        List<String> otherContents = getRemoveBlackLine(otherFunction.codeContents);
        if(otherContents.size() > thisContents.size()){
            return false;
        }
        for(int i = 0; i < thisContents.size(); i++){
            if(thisContents.get(i).trim().equals(otherContents.get(0).trim())){
                int j = 0;
                for(j = 0; j < otherContents.size(); j++){
                    if(i + j >= thisContents.size()){
                        return false;
                    }
                    else if(!thisContents.get(i+j).trim().equals(otherContents.get(j).trim())){
                       break;
                    }
                }
                if(j == otherContents.size()){
                    return true;
                }
            }
        }
        return false;
    }

    
    //wjy edit at 2014-8-26
    private List<String> getRemoveBlackLine(List<String> codeContents) {
        List<String> result = new ArrayList<String>();
        for(String content: codeContents){
            if(!"".equals(content.trim())){
                result.add(content);
            }
        }
        return result;
    }
    
  //wjy edit at 2014-9-2
    private void dealDefualtCombine(FunctionInfo otherFunction) {
        if("NO_COVER".equals(SystemConstant.DEFAULT_COVER_TYPE.trim())){
            ;
        }
        else if("OVERRIDE".equals(SystemConstant.DEFAULT_COVER_TYPE.trim())){
            overrideContents(otherFunction);
        }
        else if("ALL_COVER".equals(SystemConstant.DEFAULT_COVER_TYPE.trim())){
            allCover(otherFunction);
        }
        else{
            CombinConetent(otherFunction);
        }
        
    }
    
    

    public String getFunctionName(){
        return functionName;
    }


    @Override
    String appendBrace() {
        return "    }";
    }
}
