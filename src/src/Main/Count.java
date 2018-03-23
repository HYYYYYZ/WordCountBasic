package Main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class Count {

	private int charNum=0;//�ַ���
	private int wordNum=0;//������
	private int lineNum=0;//����
	private int codeLine=0;//������
	private int noteLine=0;//ע����
	private int blankLine=0;//����
	
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

	//���н���
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
			int flag3=0;//����ע����
			
			//���㵥����
			while((s=br.readLine())!=null){
				if(flag3==2)flag3=0;
				
				//�ո�ָ�ĵ�����
				wordSplit1=s.split(" ");
				for(int i=0;i<wordSplit1.length;i++){
					//�õ��������
					wordSplit2=wordSplit1[i].split(",");
					wordNum+=wordSplit2.length;
					
					for(int j=0;j<wordSplit2.length;j++){
						if(wordSplit2[j].equals(""))wordNum--;
						
						if(stopList!=null)
							for(int k=0;k<stopList.size();k++)
								//����н����б��еĻ���ַ��������㵥����
								if(!stopList.get(k).equals("")&&wordSplit2[j].equals(stopList.get(k)))wordNum--;
						
					}
				}
				
				//���������
				lineNum++;
				charNum+=s.length();
				int flag1=0;//���пɿ���������ַ���
				int flag2=0;//�ж��Ƿ��е���ע��
				
				
				//���н���
				for(int i=0;i<s.length();i++){
					c1=s.charAt(i);
					
					if(i<=s.length()-2&&c1!=44){
						c2=s.charAt(i+1);
						//���ַ��ͺ�һ���ַ�Ϊ��//����Ϊ����ע��
						if(c1==47&&c2==47)flag2=1;
						//����ע�Ϳ�ʼ
						if(c1==47&&c2==42)flag3=1;
						//����ע�ͽ���
						if(c1==42&&c2==47)flag3=2;
						}
					if(flag2==0)flag1++;
					}
				
				//�õ���������
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
	
	//�õ�ͣ�ô��б�
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
