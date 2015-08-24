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

	//需要预测数据的list
	public List<WeiboItem> predictList = new ArrayList<WeiboItem>();
	//预测后最终的结果list
	public List<WeiboItem> predictList_Result = new ArrayList<WeiboItem>();

	
	/**
	 * 通过均值，方差来预测
	 * @param inname   预测数据地址
	 * @param outname  输入结果地址
	 * @param map  预测器（每个用户近半年发微博对应转发  评论  赞的均值和方差）
	 */
	public void predictByMeanDevit(String inname, String outname,
			Map<String, Double[]> map) {
		getFile(inname);
		for (WeiboItem item : predictList) {
			Double[] predictors = map.get(item.getUid());
			if (predictors == null) {
				predictors = new Double[6];
				for (int i = 0; i < 6; i++) {
					predictors[i] = 0.0;        //无预测器则全为0
				}
			}
			Integer times[] = new Integer[3];
			for (int i = 0; i < 3; ++i) {
				Random r1 = new Random();
				int op1 = r1.nextInt(1);
				if (op1 == 0)
					op1 = -1;    //随机取正负号用于均值加或减标准差
				Random r2 = new Random();

				Double pTemp = predictors[i + 3];

				int temp = (int) Math.floor(pTemp);   //标准差转换整数
				int op2 = 0;
				if (temp > 0)
					op2 = r2.nextInt(temp);
				times[i] = (int) Math.floor(predictors[i]) + op1 * op2;  //result_predict=mean+/-(random(0-std_de))
				if (times[i] < 0)
					times[i] = 0;
			}
			item.setForwardTimes(times[0]);
			item.setReviewTimes(times[1]);
			item.setNiceTimes(times[2]);
			predictList_Result.add(item);
		}
		writeResult(outname, predictList_Result);
	}

	
	/**
	 * 通过均值，方差来预测
	 * @param inname   预测数据地址
	 * @param outname  输入结果地址
	 * @param map  预测器（每个用户近半年发微博对应转发  评论  赞的均值和方差）
	 * @param map_C 预测器（每个用户每个类别近半年发微博对应转发  评论  赞的均值和方差）
	 * 
	 */
	public void predictByMeanDevit_C(String inname, String outname,
			Map<String, Double[]> map, Map<String, Double[]> map_C) {
		getFile(inname);
		for (WeiboItem item : predictList) {
			Double[] predictors = map_C.get(item.getUid() + item.getCid());
			if (predictors == null) {
				predictors = map.get(item.getUid());
				if (predictors == null) {
					predictors = new Double[6];
					for (int i = 0; i < 6; i++) {
						predictors[i] = 0.0;
					}
				}
			}
			Integer times[] = new Integer[3];
			for (int i = 0; i < 3; ++i) {
				Random r1 = new Random();
				int op1 = r1.nextInt(1);
				if (op1 == 0)
					op1 = -1;
				Random r2 = new Random();

				Double pTemp = predictors[i + 3];

				int temp = (int) Math.floor(pTemp);
				int op2 = 0;
				if (temp > 0)
					op2 = r2.nextInt(temp);
				times[i] = (int) Math.floor(predictors[i]) + op1 * op2;
				if (times[i] < 0)
					times[i] = 0;
			}
			item.setForwardTimes(times[0]);
			item.setReviewTimes(times[1]);
			item.setNiceTimes(times[2]);
			predictList_Result.add(item);
		}
		writeResult(outname, predictList_Result);
	}

	
	/**
	 * 按固定格式输出结果
	 * @param filename
	 * @return
	 */
	private Integer getFile(String filename) {
		File file = new File(filename);
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
			String line = "";
			while ((line = br.readLine()) != null) {
				WeiboItem record = new WeiboItem(line);
				predictList.add(record);
			}
		} catch (IOException e) {

			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return 1;
	}

	public void writeResult(String outfilename, List<WeiboItem> list) {
		File file = new File(outfilename);
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new FileWriter(file));
			for (int i = 0; i < list.size(); ++i) {
				WeiboItem item = list.get(i);
				pw.print(item.getUid() + "\t");
				pw.print(item.getCid() + "\t");
				pw.print(item.getForwardTimes() + ",");
				pw.print(item.getReviewTimes() + ",");
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
