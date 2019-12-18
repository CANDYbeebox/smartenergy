package com.sure.algorithm.data_fusion;

public class M_Caculate {


	public float[] m_caculate(float[] fs1, float[] fs2) {
		// TODO Auto-generated method stub
		float[] fs = new float[fs1.length];
		float[][] met=new float[fs2.length][fs1.length];
		float k=0;
		Metrix metrix =new Metrix();
		met=metrix.metrix(fs2, fs1);
		KFunction kf=new KFunction();
		k=kf.kNum(fs1, fs2);
		for(int i=0;i<fs1.length;i++){
			fs[i]=met[fs2.length-1][i]+met[i][fs1.length-1]+met[i][i];
	
			}
		fs[fs1.length-1]=met[fs2.length -1][fs2.length -1];
		for(int j=0;j<fs1.length;j++){
			fs[j]=fs[j]/(1-k);
		}
		return fs;
	}

}
