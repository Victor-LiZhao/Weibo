package com.tju;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FeatureUtils {

	public static Map<String,Double[]> byMeanDevit(){
		Map<String,Double[]> map=new HashMap<String,Double[]>();
		for(String key : Features.dataMap.keySet()){
			List<WeiboItem> list=Features.dataMap.get(key);
			Double p[]=new Double[6];
			for(int i=0;i<6;i++)
				p[i]=0.0;
			Double sum1=0.0;  //forward
			Double sum2=0.0;  //review
			Double sum3=0.0;  //nice
			Integer counts=0;
			for(WeiboItem item : list){
				sum1+=item.getForwardTimes();
				sum2+=item.getReviewTimes();
				sum3+=item.getNiceTimes();
				counts+=1;
			}
			p[0]=sum1/counts;
			p[1]=sum2/counts;
			p[2]=sum3/counts;
			Double sum4=0.0;
			sum1=0.0;  //forward
			sum2=0.0;  //review
			sum3=0.0;  //nice
			for(WeiboItem item : list){
				sum1+=Math.pow((item.getForwardTimes()-p[0]),2);
				sum2+=Math.pow((item.getReviewTimes()-p[1]),2);
				sum3+=Math.pow((item.getNiceTimes()-p[2]),2);
			}
			p[3]=Math.sqrt(sum1/counts);
			p[4]=Math.sqrt(sum2/counts);
			p[5]=Math.sqrt(sum3/counts);
			map.put(key, p);
			
		}
		return map;
	}
}
