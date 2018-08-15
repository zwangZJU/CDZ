package service;
import java.io.*;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import aos.framework.core.id.AOSId;
import aos.system.common.utils.SystemCons;
import dao.Alarm_descDao;
import dao.Alarm_logDao;
import po.Alarm_descPO;
import po.Alarm_logPO;
 
public class dataParse {
	
	@Autowired
	Alarm_descDao alarm_descDao;
	
	public void main(String[] args) throws Exception {
		//�ļ�·��
		ArrayList<Alarm_descPO> list = readTxt("C:\\zhihuianfang\\code\\CDZ7.09\\alarm.txt");
		//��ʾ��ź�����
		for(int i=0;i<225;i++)
		{
		    System.out.println(list.get(i).getEee());           
			System.out.println(list.get(i).getAlarm_type());  
		
			
			Alarm_descPO alarm_descPO = new Alarm_descPO();
			alarm_descPO.setId_(AOSId.appId(SystemCons.ID.SYSTEM));
			alarm_descPO.setEee(list.get(i).getEee());
			alarm_descPO.setAlarm_type(list.get(i).getAlarm_type());
			alarm_descDao.insert(alarm_descPO);
		}
		
		
		}
	
		//����һ�����ļ��ķ���
	public static ArrayList<Alarm_descPO> readTxt(String path) throws Exception {
		File file = new File(path);
		//����һ����ȡ�ļ���������
		FileReader fileReader = new FileReader(file);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		ArrayList<Alarm_descPO> al= new ArrayList();	
		ArrayList<String> arry = new ArrayList();
		//ȥ�����в���ȡ
		String line = "";
		while ((line = bufferedReader.readLine()) != null) {
			 if(!line.equals("")){
				 arry.add(line);	                   
	            }    
		}	
		// �ͷ���Դ
		bufferedReader.close();
		// ��������
		for (String s : arry) {
			String s1=s.trim();
			if(s1.equals("")){
                continue;
                }  
			
			//ȥ�����������
			String result = s1.substring(0,s1.length()-2);
			String s2=result.trim();    
			if(s2.equals("")){
                continue;
                }			
			//��ʾ����λ��ʼ������
			String s3=s2.trim().substring(3,s2.length());
			//��ʾǰ��λ������
			String s4=s2.trim().substring(0,3);		
			Alarm_descPO type = new Alarm_descPO();
			//�����������ͺͱ��
			type.setAlarm_type(s3);	  
			type.setEee(s4);          
			al.add(type);								
		}
		return al;		
		}	
	
	}



