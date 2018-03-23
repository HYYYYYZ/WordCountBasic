package Main;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.io.File;
import java.util.ArrayList;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;

public class Main {

	//要解析的文件名
	private static String parseFilename;
	
	public static void main(String[] args) {
		DefaultParser parser=new DefaultParser();
		CommandLine cl=null;
		
		//定义选项
		Options options=new Options();
		
		options.addOption(Option.builder("c").optionalArg(true).numberOfArgs(1).build());//统计字符数
		options.addOption(Option.builder("w").optionalArg(true).numberOfArgs(1).build());//统计单词数
		options.addOption(Option.builder("l").optionalArg(true).numberOfArgs(1).build());//统计行数
		options.addOption(new Option("o",true,"OUTPUTFILE"));//输出到指定文件
		options.addOption(Option.builder("s").optionalArg(true).numberOfArgs(1).build());//递归处理目录下所有文件
		options.addOption(Option.builder("a").optionalArg(true).numberOfArgs(1).build());//更复杂的数据（代码行、空行、注释行）
		options.addOption(new Option("e",true,"STOPLIST"));//停用词表
		options.addOption(new Option("x","CHOOSEFILE"));//图形界面选取文件
		
		try {
			//解析命令行
			cl=parser.parse(options, args);
			ArrayList<String> content = new ArrayList<String>();
			FileChoose fileChoose=new FileChoose();
			String str;
			Count count=new Count();
			
			//有-x
			if(cl.hasOption("x")){
				parseFilename=fileChoose.chooseFile();
				count.parse(parseFilename, null);
				
				content.add(parseFilename+"，字符数："+count.getCharNum()+"\r\n");
				content.add(parseFilename+"，单词数："+count.getWordNum()+"\r\n");
				content.add(parseFilename+"，行数："+count.getLineNum()+"\r\n");
				content.add(parseFilename+"，代码行/空行/注释行："+count.getCodeLine()+"/"+count.getBlankLine()+"/"+count.getNoteLine()+"\r\n");
				fileChoose.showResult(content);
			}
				
			else if(!cl.hasOption("x")){
				//无-x时
				int c=0,w=0,l=0,a=0,s=0,e=0;
				ArrayList<String> files;
				ArrayList<String> stopList = new ArrayList<String>();
				
				//检查各option，得到参数（解析文件名）
				if(cl.hasOption("c")){
					c=1;
					if((str=cl.getOptionValue("c"))!=null)parseFilename=str;
				}

				
				if(cl.hasOption("w")){
					w=1;
					if((str=cl.getOptionValue("w"))!=null)parseFilename=str;
				}
				
				if(cl.hasOption("l")){
					l=1;
					if((str=cl.getOptionValue("l"))!=null)parseFilename=str;
				}
				
				if(cl.hasOption("a")){
					a=1;
					if((str=cl.getOptionValue("a"))!=null)parseFilename=str;
				}
				
				if(cl.hasOption("s")){
					s=1;
					if((str=cl.getOptionValue("s"))!=null)parseFilename=str;
				}
				
				if(cl.hasOption("e")){
					e=1;
					String stopListFile=cl.getOptionValue("e");
					stopList=count.stop(stopListFile);
				}
				
				if(parseFilename!=null){
					files=new ArrayList<String>();
					
					//如果有-s，files为所有文件，如果无-s，files只有parseFilename一个文件
					if(s==1){
						//file表示根目录
						File file=new File("");
						fileChoose.findFiles(file.getAbsolutePath(), files, parseFilename);	
					}
					else{
						files.add(parseFilename);
					}
					
					
					for(int i=0;i<files.size();i++){
						
						//根据是否有停用词表，进行解析
						if(e==1){
							//有停用词表
							count.parse(files.get(i), stopList);
						}
						else{
							//无停用词表
							count.parse(files.get(i), null);
						}
						
						//有-c
						if(c==1){
							int charCount=count.getCharNum();
							content.add(files.get(i)+"，字符数："+charCount+"\r\n");
						}
						
						//有-w
						if(w==1){
							int wordCount=count.getWordNum();
							content.add(files.get(i)+"，单词数："+wordCount+"\r\n");
						}
						
						//有-l
						if(l==1){
							int lineCount=count.getLineNum();
							content.add(files.get(i)+"，行数："+lineCount+"\r\n");
						}
						
						//有-a
						if(a==1){
							content.add(files.get(i)+"，代码行/空行/注释行："+count.getCodeLine()+"/"+count.getBlankLine()+"/"+count.getNoteLine()+"\r\n");
						}
					}
					
					
					//有-o
					if(cl.hasOption("o")){
						if((str=cl.getOptionValue("o"))!=null)
							fileChoose.outputToFile(str, content);
					}
					else fileChoose.outputToFile("result.txt", content);
				}
			}
			
			
			
			
			
				
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
