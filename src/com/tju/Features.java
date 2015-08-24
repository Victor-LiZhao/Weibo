package com.tju;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Features {

	public static List<WeiboItem> itemList=new ArrayList<WeiboItem>();
	public static Map<String,List<WeiboItem>> dataMap=new HashMap<String,List<WeiboItem>>();
	
	private static Integer getFile(String filename){
		File file=new File(filename);
		BufferedReader br=null;
		try {
			br=new  BufferedReader(new FileReader(file));
			String line="";
			while((line=br.readLine())!=null)
			{
				WeiboItem record=new WeiboItem(line);
				itemList.add(record);
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
	
	public static void main(String[] args) {
		String name="G:\\working\\tianchi\\weibo_train_data\\weibo_train_data.txt";
		getFile(name);
		getMap();
		Map<String,Double[]> featureMap=FeatureUtils.byMeanDevit();
		Prediction pre=new Prediction();
		itemList.clear();dataMap.clear();
		String preInName="G:\\working\\tianchi\\weibo_predict_data\\weibo_predict_data.txt";
		String preOutName="G:\\working\\tianchi\\weibo_result_data.txt";
		pre.predictByMeanDevit(preInName, preOutName, featureMap);
	}

	private static void getMap() {
		for(WeiboItem item : itemList){
			if(dataMap.containsKey(item.getUid())){
				List<WeiboItem> tempList=dataMap.get(item.getUid());
				tempList.add(item);
				dataMap.put(item.getUid(), tempList);
			}
			else{
				List<WeiboItem> tempList=new ArrayList<WeiboItem>();
				tempList.add(item);
				dataMap.put(item.getUid(), tempList);
			}
		}
	}
	

}
