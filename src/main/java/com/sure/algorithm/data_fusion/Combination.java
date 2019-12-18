package com.sure.algorithm.data_fusion;

public class Combination {
	float k;
	float[] sum;
	
	float[] combination(float[] fs1,float[] fs2){
		M_Caculate mc=new M_Caculate();
		sum=mc.m_caculate(fs1, fs2);
		return sum;
	}
}
