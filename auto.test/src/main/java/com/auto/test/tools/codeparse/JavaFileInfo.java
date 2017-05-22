/**
 * @author wjy
 * create at 2014-8-22
 */
package com.auto.test.tools.codeparse;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * @author wjy
 * edit at 2014-8-22
 */
public class JavaFileInfo {
    
    FileInfo file;
    String[] lineContents;
    
    List<ClassInfo> classes = new ArrayList<ClassInfo>();
    
    CodeSourceBlocker fileBlocker = new CodeSourceBlocker();

    //wjy edit at 2014-8-22
    public JavaFileInfo(File file) {
        this.file = new FileInfo(file);
        lineContents = this.file.getLineContents();
        parseFileIntoClasses();
    }
    
    public JavaFileInfo(String fileContents) {
        lineContents = fileContents.split("\n");
        parseFileIntoClasses();
    }

    //wjy edit at 2014-8-22
    private void parseFileIntoClasses() {
        
        formateLine();
        fileBlocker.parseConetentsToBlocks(lineContents);
        
        for(List<String> classBlock : fileBlocker.braceBlocks){
            
            ClassInfo classInfo = new ClassInfo(classBlock);
            classes.add(classInfo);
        }
        
    }
   
    
    
    
    //wjy edit at 2014-8-22
    private void formateLine() {
        //ȥ��ע���е�';' '{'��'}' 
        formateNotes(lineContents);
        
        //������';' '{'��'}'�������
        putSomeCharacterEnd(lineContents);
        
    }
    
    //wjy edit at 2014-8-22
    private void formateNotes(String[] lines) {
      //ȥ��ע���е�';' '{'��'}'
        formatNotesSigleLine(lines);
        formatNotesMultiLine(lines);
    }
    
  //wjy edit at 2014-8-22
    private void formatNotesSigleLine(String[] lines) {
        //ȥ������ע�͵�';' '{'��'}'
        for(int i = 0 ; i < lines.length; i++){            
            if(lines[i].contains("//")){
                String line = lines[i];
                int index = line.indexOf("//");
                String codeContents = line.substring(0, index);
                String noteContents = line.substring(index);
                noteContents = removeCharters(noteContents);
                lines[i] = codeContents + noteContents;
            }
            
            
        }
        
    }
    
    //wjy edit at 2014-8-22
    private String removeCharters(String noteContents) {
      //ȥ��';' '{'��'}'
        noteContents.replaceAll(";", "");
        noteContents.replaceAll("\\{", "");
        noteContents.replaceAll("\\{", "");
        return noteContents;
    }
    
    //wjy edit at 2014-8-22
    private void formatNotesMultiLine(String[] lines) {
        boolean isInNotes  = false;
        for(int i = 0 ; i < lines.length; i++){
            if(!isInNotes && lines[i].contains("/*") && lines[i].contains("*/")){
                String line = lines[i];
                int indexFirst = line.indexOf("/*");
                int indexLast = line.lastIndexOf("*/");
                String codeContentsOne = line.substring(0, indexFirst);
                String noteContents = line.substring(indexFirst,indexLast);
                String codeContentsTwo = line.substring(indexLast);
                noteContents = removeCharters(noteContents);
                lines[i] = codeContentsOne + noteContents + codeContentsTwo;
            }
            
            else if(lines[i].contains("/*")){
                String line = lines[i];
                int index = line.indexOf("/*");
                String codeContents = line.substring(0, index);
                String noteContents = line.substring(index);
                noteContents = removeCharters(noteContents);
                lines[i] = codeContents + noteContents;
                isInNotes = true;
            }
            else if(lines[i].contains("*/")){
                String line = lines[i];
                int index = line.indexOf("*/");
                String noteContents = line.substring(0, index);
                String codeContents = line.substring(index);
                noteContents = removeCharters(noteContents);
                lines[i] = codeContents + noteContents;
                isInNotes = false;
            }
            else if(isInNotes){
                lines[i] = removeCharters(lines[i]);
            }
        }
        
    }


    //wjy edit at 2014-8-22
    private void putSomeCharacterEnd(String[] lines) {
        //������';' '{'��'}'�������
        lines =  putOneCharacterEnd(lines, ";");       
        lines =  putOneCharacterEnd(lines, "{");
        lines =  putOneCharacterEnd(lines, "}");     
        
    }

    //wjy edit at 2014-8-25
    private String[] putOneCharacterEnd(String[] lines, String needPutEndChar) {
        List<String> fomat = new ArrayList<String>();
        
        String splitChar = "}".equals(needPutEndChar) || "{".equals(needPutEndChar)
                                ? "\\" + needPutEndChar : needPutEndChar;
        
        for(String line : lines){
            if(line.contains(needPutEndChar)){
                String[] items = line.split(splitChar);
                if(items.length < 2){
                    fomat.add(line);
                }
                else{
                    int i = 0;
                    for(i = 0; i < items.length -1 ; i++){
                        if(i > 0 && items[i-1].contains("//")){
                            fomat.add("//" + items[i]+" {");
                        }
                        else{
                            fomat.add(items[i]+" {");
                        }
                    }
                    fomat.add(items[i]);
                }
            }
            else {
                fomat.add(line);
            }
        }
        
        return fomat.toArray(lines);
    }


    
    public void CombinConetent(JavaFileInfo otherJavaFile){
        
        for(ClassInfo otherClass : otherJavaFile.classes){
            if(isHavingThisClassAlready(otherClass)){
                ClassInfo thisClass = getHavingThisClassAlready(otherClass);
                thisClass.CombinConetent(otherClass);
            }
            else{
                classes.add(otherClass);
            }
        }
    }
    
    
    private boolean isHavingThisClassAlready(ClassInfo inputClass){
        if(inputClass == null){
            return false;
        }
        for(ClassInfo classInfo : classes){
            if(classInfo.getClassName().equals(inputClass.getClassName())){
                return true;
            }
        }
        return false;
    }
    
    private ClassInfo getHavingThisClassAlready(ClassInfo inputClass){
        if(inputClass == null){
            return null;
        }
        for(ClassInfo classInfo : classes){
            if(classInfo.getClassName().equals(inputClass.getClassName())){
                return classInfo;
            }
        }
        return null;
    }
    
    
    
    public String printJavaFile(){
        StringBuilder sb = new StringBuilder();
        
        for(List<String> lines : fileBlocker.semicolonBlocks){
            for(String line : lines){
                sb.append(line).append("\n");
            }
        }
        
        for(ClassInfo classInfo : classes){
            sb.append(classInfo.printCodeInfo()).append("\n");
        }
        
        return sb.toString();
    }

}
