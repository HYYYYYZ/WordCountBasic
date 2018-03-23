package Main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class FileChoose {

	//ͼ�ν���ѡ���ļ�
	public String chooseFile(){
		JFileChooser jfc=new JFileChooser();
		jfc.setDialogTitle("��ѡ��һ���ļ�");
		jfc.showOpenDialog(null);
		jfc.setVisible(true);
		
		//�õ��û�ѡ����ļ�·��
		String filename=jfc.getSelectedFile().getAbsolutePath();
		return filename;
	}
	
	//�����ָ���ļ�
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
	
	//�ҵ��ļ��������з��ϵ��ļ����ļ����þ���·����
	public void findFiles(String upperFile,ArrayList<String> fileNames,String matchName){
		File f=new File(upperFile);
		File[] files;
		//������ļ���
		if(f.isDirectory()){
			//��ȡ�������ļ�
			files=f.listFiles();
			String absolutePath;
			//�ж�ÿ�����ļ����ļ������ļ���
			for(int i=0;i<files.length;i++){
				absolutePath=files[i].getAbsolutePath();
				
				//������ļ��Һ�׺ƥ��
				if(files[i].isFile()&&absolutePath.endsWith(matchName.substring(1))){
					fileNames.add(absolutePath);
				}
				//������ļ��У����������
				else if(files[i].isDirectory())
					findFiles(absolutePath,fileNames,matchName);
			}
		}
	}
	
	//ͼ�ν�����ʾ���
	public void showResult(ArrayList<String> content){
		JFrame jf=new JFrame("ͳ�ƽ��");
		jf.setSize(500, 300);
		jf.setLocation(500, 400);
		jf.setVisible(true);
		//�ر�ʱ�ͷ��ڴ�
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//�ı���
		JTextArea jta=new JTextArea();
		jf.add(jta);
		
		for(int i=0;i<content.size();i++){
			jta.append(content.get(i)+"\n");
		}
	}
}
