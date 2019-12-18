package com.sure.algorithm.data_fusion;

public class KFunction {
float k;
	
	float kNum(float[]fs1,float[]fs2){
		
		float[][] fs=new float[fs1.length][fs2.length];
		Metrix met=new Metrix();
		fs=met.metrix(fs1, fs2);
		for(int i=0;i<fs1.length;i++){
			for(int j=0;j<=fs2.length;j++){
				if(i!=j&j<fs2.length-1&i<fs1.length-1) k+=fs[i][j];
			}
		}
		return k;
		
	}
}
