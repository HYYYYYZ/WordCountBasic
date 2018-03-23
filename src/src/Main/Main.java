package Main;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.io.File;
import java.util.ArrayList;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;

public class Main {

	//Ҫ�������ļ���
	private static String parseFilename;
	
	public static void main(String[] args) {
		DefaultParser parser=new DefaultParser();
		CommandLine cl=null;
		
		//����ѡ��
		Options options=new Options();
		
		options.addOption(Option.builder("c").optionalArg(true).numberOfArgs(1).build());//ͳ���ַ���
		options.addOption(Option.builder("w").optionalArg(true).numberOfArgs(1).build());//ͳ�Ƶ�����
		options.addOption(Option.builder("l").optionalArg(true).numberOfArgs(1).build());//ͳ������
		options.addOption(new Option("o",true,"OUTPUTFILE"));//�����ָ���ļ�
		options.addOption(Option.builder("s").optionalArg(true).numberOfArgs(1).build());//�ݹ鴦��Ŀ¼�������ļ�
		options.addOption(Option.builder("a").optionalArg(true).numberOfArgs(1).build());//�����ӵ����ݣ������С����С�ע���У�
		options.addOption(new Option("e",true,"STOPLIST"));//ͣ�ôʱ�
		options.addOption(new Option("x","CHOOSEFILE"));//ͼ�ν���ѡȡ�ļ�
		
		try {
			//����������
			cl=parser.parse(options, args);
			ArrayList<String> content = new ArrayList<String>();
			FileChoose fileChoose=new FileChoose();
			String str;
			Count count=new Count();
			
			//��-x
			if(cl.hasOption("x")){
				parseFilename=fileChoose.chooseFile();
				count.parse(parseFilename, null);
				
				content.add(parseFilename+"���ַ�����"+count.getCharNum()+"\r\n");
				content.add(parseFilename+"����������"+count.getWordNum()+"\r\n");
				content.add(parseFilename+"��������"+count.getLineNum()+"\r\n");
				content.add(parseFilename+"��������/����/ע���У�"+count.getCodeLine()+"/"+count.getBlankLine()+"/"+count.getNoteLine()+"\r\n");
				fileChoose.showResult(content);
			}
				
			else if(!cl.hasOption("x")){
				//��-xʱ
				int c=0,w=0,l=0,a=0,s=0,e=0;
				ArrayList<String> files;
				ArrayList<String> stopList = new ArrayList<String>();
				
				//����option���õ������������ļ�����
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
					
					//�����-s��filesΪ�����ļ��������-s��filesֻ��parseFilenameһ���ļ�
					if(s==1){
						//file��ʾ��Ŀ¼
						File file=new File("");
						fileChoose.findFiles(file.getAbsolutePath(), files, parseFilename);	
					}
					else{
						files.add(parseFilename);
					}
					
					
					for(int i=0;i<files.size();i++){
						
						//�����Ƿ���ͣ�ôʱ����н���
						if(e==1){
							//��ͣ�ôʱ�
							count.parse(files.get(i), stopList);
						}
						else{
							//��ͣ�ôʱ�
							count.parse(files.get(i), null);
						}
						
						//��-c
						if(c==1){
							int charCount=count.getCharNum();
							content.add(files.get(i)+"���ַ�����"+charCount+"\r\n");
						}
						
						//��-w
						if(w==1){
							int wordCount=count.getWordNum();
							content.add(files.get(i)+"����������"+wordCount+"\r\n");
						}
						
						//��-l
						if(l==1){
							int lineCount=count.getLineNum();
							content.add(files.get(i)+"��������"+lineCount+"\r\n");
						}
						
						//��-a
						if(a==1){
							content.add(files.get(i)+"��������/����/ע���У�"+count.getCodeLine()+"/"+count.getBlankLine()+"/"+count.getNoteLine()+"\r\n");
						}
					}
					
					
					//��-o
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
