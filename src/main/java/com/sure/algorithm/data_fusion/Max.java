package com.sure.algorithm.data_fusion;

public class Max {
	float[] max(float...fs){
		
		float[] maxnum =new float[2];
		for(int j=1;j<fs.length;j++){
			if(fs[j]>=fs[j-1]&fs[j]>=maxnum[0]){
				maxnum[0]=fs[j];
			}else if(maxnum[0]>=fs[j]&maxnum[0]>=fs[j-1]) {}
			else if (maxnum[0]<=fs[j]&maxnum[0]>=fs[j-1])	maxnum[0]=fs[j];
			else if(fs[j]<=fs[j-1]&maxnum[0]<=fs[j-1]) maxnum[0]=fs[j-1];
		}
		for (int i=0;i<fs.length;i++){
			if(fs[i]==maxnum[0]) maxnum[1]=i+1;
		}
		return maxnum;
}
public static void main(String[] args){
	float[] fs={23.23f,22.23f,16.012f,3.234f,4.23f,5.234f,6.345f,7.323f,8.44f,0.012f,112.232f,18.123f,23.322f,2.334f};
	float f;
	Max m=new Max();
	f=m.max(fs)[0];
	System.out.print("Max number is "+f+"."+" "+m.max(fs)[1]);
}
}
