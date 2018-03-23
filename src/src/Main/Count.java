package Main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class Count {

	private int charNum=0;//字符数
	private int wordNum=0;//单词数
	private int lineNum=0;//行数
	private int codeLine=0;//代码行
	private int noteLine=0;//注释行
	private int blankLine=0;//空行
	
	public int getCodeLine() {
		return codeLine;
	}

	public int getNoteLine() {
		return noteLine;
	}

	public int getBlankLine() {
		return blankLine;
	}
	
	public int getCharNum() {
		return charNum;
	}

	public int getWordNum() {
		return wordNum;
	}

	public int getLineNum() {
		return lineNum;
	}

	//按行解析
	public void parse(String name,ArrayList<String> stopList){
		String s;
		String wordSplit1[]=null;
		String wordSplit2[]=null;
		char c1;
		char c2 = 0;
		FileReader fr=null;
		BufferedReader br=null;
		try {
			fr=new FileReader(new File(name));
			br=new BufferedReader(fr);
			int flag3=0;//多行注释行
			
			//计算单词数
			while((s=br.readLine())!=null){
				if(flag3==2)flag3=0;
				
				//空格分割的单词组
				wordSplit1=s.split(" ");
				for(int i=0;i<wordSplit1.length;i++){
					//得到多个单词
					wordSplit2=wordSplit1[i].split(",");
					wordNum+=wordSplit2.length;
					
					for(int j=0;j<wordSplit2.length;j++){
						if(wordSplit2[j].equals(""))wordNum--;
						
						if(stopList!=null)
							for(int k=0;k<stopList.size();k++)
								//如果有禁用列表中的或空字符串，不算单词数
								if(!stopList.get(k).equals("")&&wordSplit2[j].equals(stopList.get(k)))wordNum--;
						
					}
				}
				
				//计算各行数
				lineNum++;
				charNum+=s.length();
				int flag1=0;//此行可看做代码的字符数
				int flag2=0;//判断是否有单行注释
				
				
				//按行解析
				for(int i=0;i<s.length();i++){
					c1=s.charAt(i);
					
					if(i<=s.length()-2&&c1!=44){
						c2=s.charAt(i+1);
						//此字符和后一个字符为“//”，为单行注释
						if(c1==47&&c2==47)flag2=1;
						//多行注释开始
						if(c1==47&&c2==42)flag3=1;
						//多行注释结束
						if(c1==42&&c2==47)flag3=2;
						}
					if(flag2==0)flag1++;
					}
				
				//得到此行类型
				if(flag3!=0)noteLine++;
				else if(flag1==0||flag1==1){
					if(flag2==0)blankLine++;
					if(flag2==1)noteLine++;
				}
				else codeLine++;
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				br.close();
				fr.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	//得到停用词列表
	public ArrayList<String> stop(String stopFile){
		FileReader fr=null;
		BufferedReader br=null;
		ArrayList<String> stopList = new ArrayList<String>();
		String[] str=null;
		String s=null;
		
		try {
			fr=new FileReader(new File(stopFile));
			br=new BufferedReader(fr);
			while((s=br.readLine())!=null){
				str=s.split(" ");
				for(int i=0;i<str.length;i++)
					stopList.add(str[i]);
			}
			return stopList;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				br.close();
				fr.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return stopList;
	}
	
	
}
