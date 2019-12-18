package com.sure.algorithm.data_fusion;

public class Metrix {
	float[][] metrix(float[] fs1,float[] fs2){
		float[][] fs=new float[fs1.length][fs2.length];
		if(fs1.length==fs2.length){
			for(int i=0;i<fs1.length;i++){
				for(int j=0;j<fs2.length;j++){
					fs[i][j]=fs1[j]*fs2[i];
				if(j<fs2.length-1)	System.out.print(" "+fs[i][j]+" ");
				else System.out.print(" "+fs[i][j]+"\n");
				}
			}
			System.out.println("****************************************");
	}
		return fs;
	}
	
	
}
