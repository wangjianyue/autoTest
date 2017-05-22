package com.auto.test.tools.xmlbean;

import java.io.File;
import java.io.FileFilter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ClassPathFile {
	
	private File fileInfo;

	public ClassPathFile(File fileInfo){
		this.fileInfo = fileInfo;
	}
	
	public ClassPathFile(String filePathInClassPath){
		//ClassLoader classLoader = ClassPathFile.class.getClassLoader();
		this(ClassPathFile.class.getClassLoader(), filePathInClassPath);
	}
	
	public ClassPathFile(ClassLoader classLoader, String filePathInClassPath){	    
		URL fileUrl = classLoader.getResource(filePathInClassPath);
		if(fileUrl != null){
			fileInfo = new File(fileUrl.getFile());
        }
	}
	
	public File getFile(){
		return fileInfo;
	}
	
	public List<File> getSubFileNamesIfHaving(){
		return getSubFileNamesIfHaving(fileInfo);
	}
	
	public List<File> getSubFileNamesIfHaving(File parentFile){
		List<File> fileNames = new ArrayList<File>();
		if(parentFile == null){
			return new ArrayList<File>(); 
		}
		else if(parentFile.isDirectory()){
			fileNames.addAll(getDirSubFilesWithRecursion(parentFile));			
		}
		else{
			fileNames.add(parentFile);
		}
		return fileNames;
	}

	private List<File> getDirSubFilesWithRecursion(File parentFile) {
		List<File> fileNames = new ArrayList<File>();
		for(File subFile : parentFile.listFiles()){
			fileNames.addAll(getSubFileNamesIfHaving(subFile));
		}
		return fileNames;
	}
	
	public String findBaseTestClass(){
	    // 获取包的物理路径   
        String filePath = fileInfo.getAbsolutePath();   
        // 以文件的方式扫描整个包下的文件 并添加到集合中   
        return findBaseTestClass( fileInfo.getName(), filePath,   
                true);   

	}
	
	/**  
     * 以文件的形式来获取包下的所有Class  
     *   
     * @param packageName  
     * @param packagePath  
     * @param recursive  
     * @param classes  
     */  
    private static String findBaseTestClass(String packageName,   
            String packagePath, final boolean recursive) {   
        // 获取此包的目录 建立一个File   
        File dir = new File(packagePath);   
        // 如果不存在或者 也不是目录就直接返回   
        if (!dir.exists() || !dir.isDirectory()) {   
             //System.out.println("用户定义包名 " + packageName + " 下没有任何文件");   
            return "";   
        }   
        // 如果存在 就获取包下的所有文件 包括目录   
        File[] dirfiles = dir.listFiles(new FileFilter() {   
            // 自定义过滤规则 如果可以循环(包含子目录) 或则是以.class结尾的文件(编译好的java类文件)   
            public boolean accept(File file) {   
                return (recursive && file.isDirectory())   
                        || ("BaseTest.class".equals(file.getName()));   
            }   
        });   
        // 循环所有文件   
        for (File file : dirfiles) {   
            // 如果是目录 则继续扫描   
            if (file.isDirectory()) {   
                String result = findBaseTestClass(packageName + "."  
                        + file.getName(), file.getAbsolutePath(), recursive);
                if(!"".equals(result)){
                    return result;
                }
            } else {   
                return packageName;
            }   
        }  
        return "";
    }  
	
	
}
