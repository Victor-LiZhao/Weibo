package com.tju;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Prediction {
	
	public List<WeiboItem> predictList=new ArrayList<WeiboItem>();
	public List<WeiboItem> predictList_Result=new ArrayList<WeiboItem>();
	
	public void predictByMeanDevit(String inname,String outname,Map<String,Double[]> map){
		getFile(inname);
		for(WeiboItem item : predictList){
			Double[] predictors=map.get(item.getUid());
			if(predictors==null){
				predictors=new Double[6];
				for(int i=0;i<6;i++){
					predictors[i]=0.0;
				}
			}
			Integer times[]=new Integer[3];
			for(int i=0;i<3;++i){
				Random r1 = new Random();
				int op1=r1.nextInt(1);
				if(op1==0)
					op1=-1;
				Random r2 = new Random();
				
				Double pTemp=predictors[i+3];
				
				int temp=(int)Math.floor(pTemp);
				int op2=0;
				if(temp>0)
					op2=r2.nextInt(temp);
				times[i]=(int)Math.floor(predictors[i])+op1*op2;
				if(times[i]<0)
					times[i]=0;
			}
			item.setForwardTimes(times[0]);
			item.setReviewTimes(times[1]);
			item.setNiceTimes(times[2]);
			predictList_Result.add(item);
		}
		writeResult(outname,predictList_Result);
	}
	private Integer getFile(String filename){
		File file=new File(filename);
		BufferedReader br=null;
		try {
			br=new  BufferedReader(new FileReader(file));
			String line="";
			while((line=br.readLine())!=null)
			{
				WeiboItem record=new WeiboItem(line);
				predictList.add(record);
			}
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		finally{
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return 1;
	}
	public void writeResult(String outfilename,List<WeiboItem> list){
		File file = new File(outfilename);
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new FileWriter(file));
			for(int i=0;i<list.size();++i){
				WeiboItem item=list.get(i);
				pw.print(item.getUid()+"\t");
				pw.print(item.getCid()+"\t");
				pw.print(item.getForwardTimes()+",");
				pw.print(item.getReviewTimes()+",");
				pw.print(item.getNiceTimes());
				pw.print("\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			pw.close();
		}
	}
}
