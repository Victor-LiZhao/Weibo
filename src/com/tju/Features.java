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


/**
 * 程序主要控制类，用于读取原始数据，转换数据等操作
 * @author lz
 *
 */
public class Features {

	//用于存放训练数据的list
	public static List<WeiboItem> itemList=new ArrayList<WeiboItem>();
	
	//用于存放训练数据中每个用户所发微博的list  key为uid
	public static Map<String,List<WeiboItem>> dataMap=new HashMap<String,List<WeiboItem>>();
	
	//用于存放训练数据中每个用户每个类别所发微博的list  key为uid+cid
	public static Map<String,List<WeiboItem>> dataMap_C=new HashMap<String,List<WeiboItem>>();
	
	
	/**
	 * 读取训练数据，并保持到全局静态list中
	 * @param filename 文件地址
	 * @return 1
	 */
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
		String name="E:\\LZ\\weibo_train_data\\weibo_train_data.txt";
		getFile(name);
		getMap();
		getMap_C();
		Map<String,Double[]> featureMap=FeatureUtils.byMeanDevit();
		Map<String,Double[]> featureMap_C=FeatureUtils.byMeanDevit_C();
		Prediction pre=new Prediction();
		itemList.clear();dataMap.clear();dataMap_C.clear();
		String preInName="E:\\LZ\\weibo_predict_data\\weibo_predict_data.txt";
		String preOutName="E:\\LZ\\weibo_result_data.txt";
		//pre.predictByMeanDevit(preInName, preOutName, featureMap);
		pre.predictByMeanDevit_C(preInName, preOutName, featureMap,featureMap_C);
	}

	/**
	 * 将原始预测数据整理成每个用户所发微博的list  key为uid
	 */
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
	
	/**
	 * 将原始预测数据整理成每个用户每个类别所发微博的list  key为uid+cid
	 */
	private static void getMap_C() {
		for(WeiboItem item : itemList){
			if(dataMap_C.containsKey(item.getUid()+item.getCid())){
				List<WeiboItem> tempList=dataMap_C.get(item.getUid()+item.getCid());
				tempList.add(item);
				dataMap_C.put(item.getUid()+item.getCid(), tempList);
			}
			else{
				List<WeiboItem> tempList=new ArrayList<WeiboItem>();
				tempList.add(item);
				dataMap_C.put(item.getUid()+item.getCid(), tempList);
			}
		}
	}
	

}
