package Main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class FileChoose {

	//图形界面选择文件
	public String chooseFile(){
		JFileChooser jfc=new JFileChooser();
		jfc.setDialogTitle("请选择一个文件");
		jfc.showOpenDialog(null);
		jfc.setVisible(true);
		
		//得到用户选择的文件路径
		String filename=jfc.getSelectedFile().getAbsolutePath();
		return filename;
	}
	
	//输出到指定文件
	public void outputToFile(String filename,ArrayList<String> content){
		FileWriter fw = null;
		
		try {
			fw=new FileWriter(filename);
			for(int i=0;i<content.size();i++){
				fw.write(content.get(i));
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				fw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	//找到文件夹下所有符合的文件（文件名用绝对路径）
	public void findFiles(String upperFile,ArrayList<String> fileNames,String matchName){
		File f=new File(upperFile);
		File[] files;
		//如果是文件夹
		if(f.isDirectory()){
			//获取所有子文件
			files=f.listFiles();
			String absolutePath;
			//判断每个子文件是文件还是文件夹
			for(int i=0;i<files.length;i++){
				absolutePath=files[i].getAbsolutePath();
				
				//如果是文件且后缀匹配
				if(files[i].isFile()&&absolutePath.endsWith(matchName.substring(1))){
					fileNames.add(absolutePath);
				}
				//如果是文件夹，则继续查找
				else if(files[i].isDirectory())
					findFiles(absolutePath,fileNames,matchName);
			}
		}
	}
	
	//图形界面显示结果
	public void showResult(ArrayList<String> content){
		JFrame jf=new JFrame("统计结果");
		jf.setSize(500, 300);
		jf.setLocation(500, 400);
		jf.setVisible(true);
		//关闭时释放内存
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//文本域
		JTextArea jta=new JTextArea();
		jf.add(jta);
		
		for(int i=0;i<content.size();i++){
			jta.append(content.get(i)+"\n");
		}
	}
}
