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

		InputStreamReader isr = new InputStreamReader(new FileInputStream(filepath));		 		  //读取文件数据
		BufferedReader br = new BufferedReader(isr);                                                //使用缓冲区的read(),readLine()
		while(br.read()!=-1)                                                                        //读取
		{
		   String s = br.readLine();
		   char[] chars=s.toCharArray();
		   Ccount += s.length();                                                                      //字符个数即为字符串长度
		   Wcount += s.split(" ").length;                                                             //把一个字符串以“ ”分割成字符串数组即单词

		   if(chars.length>1) {                                                                       //判断注释行
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
               else {                                                                                 //"/*"……"*/"之间为注释行
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
               Eline++;                                                                              //没有字符
           }
		   Lcount++;                                                                                 //总行数
		}  
		  

	    while(args[i].equals("-c")||args[i].equals("-w")||args[i].equals("-l"))
	    {
	            if(args[i].equals("-c")){
	            	c=1;
	            	System.out.println("/n字符数："+Ccount);	                
	            }
	            else if(args[i].equals("-w")){
	            	w=1;
	                System.out.println("/n单词数： "+Wcount );
	            }
	            else if(args[i].equals("-l")) {
                    l=1;
	          	    System.out.println("/n代码行数："+Lcount); 
	            }
	            else if(args[i].equals("-a")) {
                    a=1;
	          	    System.out.println("/n代码行 ："+Cline+"注释行："+Nline+"空行："+Eline); 
	            }
	            
	            else {
	                break;
	            }
	            i++;
	    }
	        
	    if(c==0&&w==0&&l==0&&a==0)
	        throw new IllegalArgumentException("请输入待统计参数");
	    if(i==args.length)
	        throw new IllegalArgumentException("请指定待统计文件");
		  	
	 }
}
