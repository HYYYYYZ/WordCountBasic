package main;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.util.ArrayList;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Wordcount {
	public static void main(String[] args) throws Exception {
        int i=0;
        String filepath=null;            
        String outpath="result.txt";    
        filepath=args[i];               
        i++;

        int c=0,w=0,l=0,a=0;

	       
		int Ccount = 0;
		int Wcount = 0;
		int Lcount = 0;
		int Cline = 0,Nline = 0,Eline = 0,note = 0;

		InputStreamReader isr = new InputStreamReader(new FileInputStream(filepath));		 		  //��ȡ�ļ�����
		BufferedReader br = new BufferedReader(isr);                                                //ʹ�û�������read(),readLine()
		while(br.read()!=-1)                                                                        //��ȡ
		{
		   String s = br.readLine();
		   char[] chars=s.toCharArray();
		   Ccount += s.length();                                                                      //�ַ�������Ϊ�ַ�������
		   Wcount += s.split(" ").length;                                                             //��һ���ַ����ԡ� ���ָ���ַ������鼴����

		   if(chars.length>1) {                                                                       //�ж�ע����
               if(chars[0]=='/'&&chars[1]=='/'){                                                      //"//"
                   Nline++;
               }
               else if(chars[0]=='/'&&chars[1]=='*'){                                                 //"/*"
            	   Nline++;
                   note=1;
               }
               else if(chars.length>2) {
                   if ((chars[0] == '{' || chars[0] == '}')) {                                        //"{//"
                       if (chars[1] == '/' && chars[2] == '/'){
                    	   Nline++;
                       }
                       else if (chars[1] == '/' && chars[2] == '*') {                                 //"{/*
                    	   Nline++;
                           note = 1;
                       }
                       else if(note!=1) {
                           Cline++;
                       }
                   }
                   else if(note!=1) {
                       Cline++;
                   }
               }
               else if(note!=1) {
            	   Cline++;
               }
               else {                                                                                 //"/*"����"*/"֮��Ϊע����
                   Nline++;
                   for (i = 0; i < chars.length; i++) {
                       if (chars[i] == '*') {
                           if (i + 1 < chars.length)
                               if (chars[i + 1] == '/')
                                   note = 0;
                       }
                   }
               }
           }
           else {                                                                                    
               Eline++;                                                                              //û���ַ�
           }
		   Lcount++;                                                                                 //������
		}  
		  

	    while(args[i].equals("-c")||args[i].equals("-w")||args[i].equals("-l"))
	    {
	            if(args[i].equals("-c")){
	            	c=1;
	            	System.out.println("/n�ַ�����"+Ccount);	                
	            }
	            else if(args[i].equals("-w")){
	            	w=1;
	                System.out.println("/n�������� "+Wcount );
	            }
	            else if(args[i].equals("-l")) {
                    l=1;
	          	    System.out.println("/n����������"+Lcount); 
	            }
	            else if(args[i].equals("-a")) {
                    a=1;
	          	    System.out.println("/n������ ��"+Cline+"ע���У�"+Nline+"���У�"+Eline); 
	            }
	            
	            else {
	                break;
	            }
	            i++;
	    }
	        
	    if(c==0&&w==0&&l==0&&a==0)
	        throw new IllegalArgumentException("�������ͳ�Ʋ���");
	    if(i==args.length)
	        throw new IllegalArgumentException("��ָ����ͳ���ļ�");
		  	
	 }
}
