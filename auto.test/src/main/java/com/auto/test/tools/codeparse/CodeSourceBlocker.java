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
public class CodeSourceBlocker {
    
    List<List<String>> semicolonBlocks = new ArrayList<List<String>>();
    List<List<String>> braceBlocks = new ArrayList<List<String>>();
    
    public void parseConetentsToBlocks(String[] contents){
        List<String> block = new ArrayList<String>();
        Stack<String> braceStack = new Stack<String>();
        for(String line : contents){
            block.add(line);
            if(line.contains("{")){
                braceStack.push("{");
            }
            if(line.contains("}")){
                braceStack.pop();
                if(braceStack.isEmpty()){
                    braceBlocks.add(block);
                    block = new ArrayList<String>();
                }
            }
            if(line.contains(";") && braceStack.isEmpty()){
                semicolonBlocks.add(block);
                block = new ArrayList<String>();
            }
        }
    }

}
