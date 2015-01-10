import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class ServiceSelectNoKeyConstraint {
	public static void main(String[] args) throws IOException {
		double startTime,endTime, totalTime;
		startTime = new Date().getTime();
//		File infile = new File("servicedata1.data");
//		File infile = new File("servicedata2.data");
//		File infile = new File("servicedata3.data");
//		File infile = new File("servicedata4.data");
//		File infile = new File("servicedata5.data");
//		File infile = new File("servicedata6.data");
//		File infile = new File("servicedata7.data");
//		File infile = new File("servicedata8.data");
//		File infile = new File("servicedata9.data");
//		File infile = new File("servicedata10.data");
//		File infile = new File("servicedata11.data");
//		File infile = new File("service.txt");
//		File infile = new File("service1.data");
//		File infile = new File("service2.data");
//		File infile = new File("service3.data");
//		File infile = new File("service4.data");
//		File infile = new File("service5.data");
//		File infile = new File("service6.data");
//		File infile = new File("service7.data");
//		File infile = new File("service8.data");
//		double[][] data = getServiceData(infile);
		double[][] data = randGetServiceData();
//		double[][] data = {{2900,4.0,0.87,0.91,0.89}, {3300,6.0,0.94,0.85,0.94}, {2500,8.5,0.90,0.93,0.87},{3000,3.5,0.85,0.89,0.90},
//				 		   {3500,5.5,0.95,0.87,0.93}, {2600,7.5,0.90,0.95,0.85}, {3000,4.5,0.87,0.91,0.89},{3400,5.5,0.95,0.87,0.93},
//				 		   {3000,4.0,0.86,0.90,0.90}, {2700,7.5,0.89,0.94,0.85}, {3300,5.5,0.93,0.86,0.95},{3100,4.0,0.85,0.90,0.91},
//				           {2700,8.0,0.89,0.94,0.85}, {3400,6.0,0.94,0.86,0.94}, {2500,8.0,0.91,0.94,0.87},{2900,4.5,0.87,0.89,0.89},
//				           {3500,6.0,0.95,0.87,0.93}, {2600,8.0,0.90,0.94,0.86}, {3300,6.5,0.93,0.85,0.95},{3100,3.5,0.85,0.89,0.91}};
		boolean[] flag = {false, true, true, true, true, true, true, true, true};
		double threshold = 0.8;
//		double[][] keyAttribute = {{1, 2600, 3000}, {2, 4.0, 6.0}, {3, 0.9, 1.0}};
//		double[][] weights = {{0.4, 0.2, 0.1, 0.2, 0.1}, {0.1, 0.5, 0.1, 0.2, 0.1}, {0.2, 0.1, 0.2, 0.1, 0.4}};
//		double[][] weights = {{0.1, 0.1, 0.1, 0.1, 0.1, 0.2, 0.1, 0.1, 0.1, 0.1}};
		double[][] weights = randGetUserData();
		double[][] normalization = getNormalizationMatrix(data, flag);
		double[] max = getMaxProperty(data);
		double[] min = getMinProperty(data);
		double[][] similarity = getSimilarityMatrix(normalization);
		List<ArrayList> totalList = getInitalSimilarClass(similarity, threshold);
		List<ArrayList> reduceList = getAccuracySimilarClass(totalList);
		double[][] virtualService = GetVirtualService(data, reduceList);
		keyAttributeConstraint(normalization, max, min, virtualService, flag, weights, reduceList);
//		endTime = new Date().getTime();
//		totalTime = endTime - startTime;
//		System.out.println(totalTime);
		
//		File outfile =new File("response.txt");
//		FileWriter writer = new FileWriter(outfile,true);
//		writer.append(String.valueOf(totalTime) + " ");
//		writer.write(String.valueOf(totalTime));
//		writer.close();
	}
	
	public static double[][] getServiceData(File infile) throws IOException  {
		FileReader fileReader = new FileReader(infile);
		BufferedReader in = new BufferedReader(fileReader);
		String s1 = "";
//		int row = 0;
//		while(((s1=in.readLine()) != null)) {
//			row ++;
//		}
		double[][] serviceData = new double[50][9];
		int i = 0;
		while(((s1=in.readLine()) != null)) {
			String[] s = s1.split(",");
			for(int j=0; j<9; j++) {
				serviceData[i][j] = Double.parseDouble(s[j]);
			}
			i ++;
		}
		
		return serviceData;
	}
	
	public static double[][] randGetServiceData() {
		double[][] serviceData = new double[50][9];
		Random rand = new Random();
		for(int i=0; i<serviceData.length; i++) {
			for(int j=0; j<serviceData[0].length; j++) {
				if(j == 0) {
					serviceData[i][j] = rand.nextInt(4953)+37;
				} else if(j == 1){
					serviceData[i][j] = rand.nextInt(93)+7;
				} else if(j == 2){
					serviceData[i][j] = rand.nextInt(43)+0.1;
				} else if(j == 3){
					serviceData[i][j] = rand.nextInt(92)+8;
				} else if(j == 4){
					serviceData[i][j] = rand.nextInt(56)+33;
				} else if(j == 5){
					serviceData[i][j] = rand.nextInt(67)+33;
				} else if(j == 6){
					serviceData[i][j] = rand.nextInt(43)+50;
				} else if(j == 7){
					serviceData[i][j] = rand.nextInt(4140)+0.33;
				} else if(j == 8){
					serviceData[i][j] = rand.nextInt(96)+1;
				}
			}
		}
		return serviceData;
	}
	
	public static double[][] randGetUserData() {
		double[][] userData = new double[300][9];
		Random rand = new Random();
		for(int i=0; i<userData.length; i++) {
			double temp = 0;
			for(int j=0; j<userData[0].length; j++) {
				userData[i][j] = rand.nextInt(9) + 1;
				temp += userData[i][j];
			}
			
			for(int j=0; j<userData[0].length; j++) {
				userData[i][j] = userData[i][j] / temp;
			}
		}
		
		
		return userData;
	}
	
	public static double[][] getNormalizationMatrix(double[][] data, boolean[] flag) {
		int row = data.length;
		int column = data[0].length;
		double[][] normalization = new double[row][column];
		for (int i = 0; i < column; i++) { // i代表列
			double max = data[0][i];
			double min = data[0][i];
			for (int j = 0; j < row; j++) { // j代表行
				if (max < data[j][i]) {
					max = data[j][i];
				}
				if (min > data[j][i]) {
					min = data[j][i];
				}
			}
			double s = max - min;
			if(flag[i]) {
				for (int j = 0; j < row; j++) {
					normalization[j][i] = (data[j][i] - min) / s;
				}
			} else {
				for (int j = 0; j < row; j++) {
					normalization[j][i] = (max - data[j][i]) / s;
				}
			}
			
		}
		
		NumberFormat nf = NumberFormat.getNumberInstance();
		 nf.setMaximumFractionDigits(3);
		 
//		for (int i = 0; i < normalization.length; i++) {
//			for (int j = 0; j < normalization[i].length; j++) {
//				System.out.print(nf.format(normalization[i][j]) + " ");
//			}
//			System.out.println();
//		}
		return normalization;
	}
	
	public static double[][] getNormalizations(double[] max, double[] min, double[][] data, boolean[] flag) {
		int row = data.length;
		int column = data[0].length;
		double[][] normalization = new double[row][column];
		for (int i = 0; i < column; i++) { // i代表列
			
			if(flag[i]) {
				for (int j = 0; j < row; j++) {
					normalization[j][i] = (data[j][i] - min[i]) / max[i] - min[i];
				}
			} else {
				for (int j = 0; j < row; j++) {
					normalization[j][i] = (max[i] - data[j][i]) / max[i] - min[i];
				}
			}
			
		}
		
		return normalization;
	}
	
	public static double[] getMaxProperty(double[][] data) {
		int row = data.length;
		int column = data[0].length;
		
		double[] max = new double[column];
		for (int i = 0; i < column; i++) { // i代表列
			for (int j = 0; j < row; j++) { // j代表行
				if (max[i] < data[j][i]) {
					max[i] = data[j][i];
				}
			}
			
		}
		return max;
	}
	
	public static double[] getMinProperty(double[][] data) {
		int row = data.length;
		int column = data[0].length;
		
		double[] min = new double[column];
		for (int i = 0; i < column; i++) { // i代表列
			for (int j = 0; j < row; j++) { // j代表行
				if (min[i] > data[j][i]) {
					min[i] = data[j][i];
				}
			}
			
		}
		return min;
	}
	
	public static double[][] getSimilarityMatrix(double normalization[][]) {
		int row = normalization.length;
		int column = normalization[0].length;
		double[][] distance = new double[row][row];
		double[][] similarity = new double[row][row];
		for (int i = 0; i < row; i++) { // i代表diatance的行
			for (int j = 0; j < row; j++) { // i代表distance的列
				distance[i][j] = 0;
				for (int k = 0; k < column; k++) { // k代表normalization的列
					distance[i][j] += Math.pow((normalization[i][k]-normalization[j][k]), 2);
				}
				similarity[i][j] = 1 - Math.sqrt(distance[i][j] / column);
				NumberFormat nf = NumberFormat.getNumberInstance();
				 nf.setMaximumFractionDigits(3);
//				System.out.print(nf.format(similarity[i][j]) + "   ");
			}
//			System.out.println();
		}
		return similarity;
	}
	
	
	public static List<ArrayList> getInitalSimilarClass(double[][] similarity, double threshold) {
		int m = similarity.length;
		List<ArrayList> totalList = new ArrayList<ArrayList>();
		for(int i=0; i<=m-1; i++) {
			totalList.add(new ArrayList<Integer>());
		}
		
		for(int i=0; i<=m-1; i++) {
			List<Integer> list = totalList.get(i);
 			for(int j=i+1; j<=m-1; j++) {
				if(similarity[i][j] >= threshold) {
					if(list.isEmpty()) {
						list.add(i+1);
						list.add(j+1);
					} else {
						boolean isHaveLow = false;
						for(int k=0; k<list.size(); k++) {
							if(similarity[list.get(k)-1][j] < threshold) {
								isHaveLow = true;
							}
						}
						if(!isHaveLow) {
							list.add(j+1);
						}
					}
				} else {
					if(list.isEmpty()) {
						list.add(i+1);
					}
				}
			}
		}
		return totalList;
	}
	
	public static List<ArrayList> getAccuracySimilarClass(List<ArrayList> totalList) {
		for(int i=0; i<totalList.size()-1; i++) {
			for(int j=i+1; j<totalList.size(); j++) {
				if(contain(totalList.get(i), totalList.get(j))) {
					totalList.remove(totalList.get(j));
					j --;
				}
			}
		}
		List<ArrayList> reduceList = totalList;
//		System.out.println("聚类后产生的相似类分别为:");
//		for(int i=0; i<reduceList.size(); i++) {
//			for(int j=0; j<reduceList.get(i).size(); j++) {
//				System.out.print("s" + reduceList.get(i).get(j) + "  ");
//			}
//			System.out.println();
//		}
		return reduceList;
	}
	
	public static boolean contain(List<Integer> list1, List<Integer> list2) {
		boolean flag = true;
		for(int i=0; i<list2.size(); i++) {
			if(!list1.contains(list2.get(i))) {
				flag = false;
				break;
			}
		}
		
		return flag;
	}
	
	public static double[][] GetVirtualService(double[][] data, List<ArrayList> reduceList) {
		int row = reduceList.size();
		int column = data[0].length;
		double[][] virtualService = new double[row][column];
		
		for(int i=0; i<row; i++) {
			for(int k=0; k<reduceList.get(i).size(); k++) {
				for(int j=0; j<column; j++) {
					virtualService[i][j] += data[(Integer) reduceList.get(i).get(k)-1][j];
				}
				
			}
			
			for(int k=0; k<column; k++) {
				virtualService[i][k] = virtualService[i][k] / reduceList.get(i).size();
			}
 		}
		
//		NumberFormat nf = NumberFormat.getNumberInstance();
//		 nf.setMaximumFractionDigits(3);
//		for(int i=0; i<row; i++) {
//			System.out.print("虚拟服务SV" + (i+1) + "QoS描述向量<");
//			for(int j=0; j<column; j++) {
//				System.out.print(nf.format(virtualService[i][j]) + " ");
//			}
//			System.out.println(">");
//		}
		return virtualService;
	}
	
	public static void keyAttributeConstraint(double[][] normalization, double[] max, double[] min, double[][] virtualService, boolean[] flag, double[][] weights, List<ArrayList> reduceList) throws IOException {
		int row = virtualService.length;
		List<List<double[]>> vsList = new ArrayList<List<double[]>>();
		List<List<Integer>> subList = new ArrayList<List<Integer>>();
		
		for(int i=0; i<weights.length; i++) {
			List<double[]> list1 = new ArrayList<double[]>();
			List<Integer> list2 = new ArrayList<Integer>();
			
			for(int j=0; j<row; j++) {
				list1.add(virtualService[j]);
				list2.add(j);
			}
			
			vsList.add(list1);
			subList.add(list2);
		}
		
//		NumberFormat nf = NumberFormat.getNumberInstance();
//		 nf.setMaximumFractionDigits(3);
//		for(int i=0; i<vsList.size(); i++) {
//			System.out.println("user" + (i+1) +"可选择的虚拟服务有：");
//			for(int j=0; j<vsList.get(i).size(); j++) {
//				System.out.print("SV" + (subList.get(i).get(j)+1) +"<");
//				for(int k=0; k<vsList.get(i).get(j).length; k++) {
//					System.out.print(nf.format(vsList.get(i).get(j)[k]) + " ");
//				}
//				System.out.println(">");
//			}
//		}
		
		int[] selectService = new int[weights.length];
		double[] sat = new double[weights.length];
		for(int i=0; i<vsList.size(); i++) {
			double[][] vs = new double[vsList.get(i).size()][vsList.get(i).get(0).length];
			int[] sub = new int[subList.get(i).size()];
			for(int j=0; j<vsList.get(i).size(); j++) {
				sub[j] = subList.get(i).get(j);
				for(int k=0; k<vsList.get(i).get(j).length; k++) {
					vs[j][k] = vsList.get(i).get(j)[k];
				}
			}
			
			vs = getNormalizations(max, min, vs, flag);
			int subNum = calaulateAndSort(vs, sub, weights[i]);
//			System.out.println("user" + (i+1) + "将从虚拟服务sv" + (subNum+1) + "选取候选服务");
			
			int serviceSub = randomSelect(subNum, reduceList);
			selectService[i] = serviceSub;
			sat[i] = getUserSatisfaction(normalization, serviceSub,  weights[i]);
//			System.out.println("user" + (i+1) +  "最终选择的候选服务是s" + serviceSub);
		}
		getSatisfaction(selectService, sat);
//		overLoadNum(selectService);
//		overLoadRate(selectService);
//		idleRate(selectService);
		
	}
	

	public static int calaulateAndSort(double[][] vs, int[] sub, double[]weights) {
		double temp = 0;
		double[] f = new double[vs.length];
		int row = vs.length;
		int column = vs[0].length;
		for(int i=0; i<row; i++) {
			for(int j=0; j<column; j++) {
				temp += (vs[i][j] * weights[j]);
			}
			f[i] = temp;
			temp = 0;
		}
		
		double max = f[0];
		int subNum = 0;
		for(int i=0; i<f.length; i++) {
			if(f[i] > max) {
				max = f[i];
				subNum = i;
			}
		}
		return sub[subNum];
	}
	
	private static double getUserSatisfaction(double[][] normalization, int serviceSub, double[]weights) {
		double f = 0;
		double temp = 0;
		for(int i=0; i<normalization[0].length; i++) {
			temp = normalization[serviceSub-1][i] * weights[i];
			f += temp;
		}
		return f;
	}
	
	public static void getSatisfaction(int[] selectService, double[] f) throws IOException {
		int[] num = new int[50];
		double sum = 0;
		double avg = 0;
		double[] sat = new double[f.length];
		for(int i=0; i<selectService.length; i++) {
			num[selectService[i]-1] ++;   //每个服务被多少个用户选取了
		}
		
		for(int i=0; i<sat.length; i++) {
//			sat[i] = f[i];
			sat[i] = f[i] * Math.pow(1.01, 1-num[selectService[i]-1]);
			sum += sat[i];
		}
		avg = sum / sat.length;
		File outfile =new File("satisfaction.txt");
		FileWriter writer = new FileWriter(outfile,true);
		writer.append(String.valueOf(avg) + " ");
		writer.close();
		
		System.out.println(avg);
	}
	
	public static int randomSelect(int subNum, List<ArrayList> reduceList) {
		int rand = (int)(Math.random()*reduceList.get(subNum).size());
		int serviceSub = (Integer) reduceList.get(subNum).get(rand);
		return serviceSub;
	}
	
	public static void overLoadRate(int[] selectService) throws IOException {
		int[] num = new int[20];
		for(int i=0; i<selectService.length; i++) {
			num[selectService[i]-1] ++;
		}
		
		int count = 0;
		for(int i=0; i<num.length; i++) {
			if(num[i] >= 100) {
				count += num[i];
			}
		}
		File outfile =new File("overLoad.txt");
		FileWriter writer = new FileWriter(outfile,true);
		writer.append(String.valueOf(count) + " ");
		System.out.println("超载用户数为：" + count);
		writer.close();
	}
	
	public static void overLoadNum(int[] selectService) throws IOException {
		int[] num = new int[50];
		for(int i=0; i<selectService.length; i++) {
			num[selectService[i]-1] ++;   //每个服务被多少个用户选取了
		}
//		for(int i=0; i<num.length; i++) {
//			System.out.print(num[i] + " ");
//		}
//		System.out.println();
		int[] overLoad = new int[20];
		for(int i=0; i<num.length; i++) {
			for(int j=0; j<overLoad.length; j++) {
				if(num[i]>10*j && num[i]<=10*(j+1)) {
					overLoad[j] ++;
				}
			}
		}
		File outfile =new File("overLoadNum.txt");
		FileWriter writer = new FileWriter(outfile, true);
		for(int i=0; i<overLoad.length; i++) {
			System.out.print(overLoad[i] + " ");
			writer.append(String.valueOf(overLoad[i]) + " ");
		}
		writer.close();
		
	}
	public static void idleRate(int[] selectService) throws IOException {
		int[] num = new int[20];
		for(int i=0; i<selectService.length; i++) {
			num[selectService[i]-1] ++;
		}
		
		int count = 0;
		for(int i=0; i<num.length; i++) {
			if(num[i] <= 10) {
				count ++;
			}
		}
		File outfile =new File("idle.txt");
		FileWriter writer = new FileWriter(outfile,true);
		writer.append(String.valueOf(count) + " ");
		System.out.println("空闲节点数为：" + count);
		writer.close();
	}
	
}

