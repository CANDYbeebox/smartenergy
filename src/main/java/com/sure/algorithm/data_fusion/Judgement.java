package com.sure.algorithm.data_fusion;

import java.text.NumberFormat;

public class Judgement {
	void judge(float[]fs,float a1,float a2){
		Max max=new Max();
		SubMax submax=new SubMax();
		if(max.max(fs)[0]-submax.submax(fs)>a1){
			if(fs[fs.length-1]<a2){
				if(max.max(fs)[0]>fs[fs.length-1]){
					printout((int)max.max(fs)[1]);
				}else System.out.println("����3�����㣡");
			}else System.out.println("����2�����㣡");
		}else System.out.println("����1������");
	}
	public static void main(String[] args){
		float[] fs1={0.668f,0.189f,0.128f,0.015f};
		float[] fs2={0.102f,0.527f,0.361f,0.009f};
		//float[] fs3={};
		//float[] fs4={};
		float[] temp,temp1,temp2,temp3=null;
		
		Combination com=new Combination();
		temp1=com.combination(fs1, fs2);
		printformat("�ֲ��ں�1: ",temp1);
	/*		temp2=com.combination(temp1,fs3);
	printformat("�ֲ��ں�2: ",temp2);
		temp3=com.combination(temp2, fs4);
		printformat("�ֲ��ں�3: ",temp3);
		temp=com.combination(temp2, fs1);
		printformat("�ֲ��ں�4: ",temp);
		temp=com.combination(temp, fs2);
		printformat("�ֲ��ں�5: ",temp);
		temp=com.combination(temp, fs3);
		printformat("�ֲ��ں�5: ",temp);
		temp=com.combination(temp, fs4);
		printformat("�ֲ��ں�5: ",temp);*/
		Judgement judge=new Judgement();
		judge.judge(temp1, 0.1f, 0.1f);
	}
	void printout(int i){
		System.out.print("\n Ŀ��Ϊ��");
		switch(i){
		case 1: System.out.print("ս������");break;
		case 2: System.out.print("����;����湥���ɻ���");break;
		case 3: System.out.print("��ը����");break;
		case 4: System.out.print("Ԥ����");break;
	   default:	System.out.print("����������");break;
		}
	
	}
 static void printformat(String s,float[] temp){
		for(int i1=0;i1<temp.length;i1++){
			if(i1==temp.length-1)System.out.print(s+"m(U)="+temp[i1]+".  ");
			else System.out.print(s+"m(O"+(i1+1)+")="+temp[i1]+".");
		}System.out.println("\n=====================================");
	}
}

