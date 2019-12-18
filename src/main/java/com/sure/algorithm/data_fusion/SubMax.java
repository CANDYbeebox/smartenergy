package com.sure.algorithm.data_fusion;

public class SubMax extends Max {
	float submax=0,max=0;
	float submax(float...fs){
		Max m=new Max();
		max=m.max(fs)[0];
		for(int i=0;i<fs.length;i++){
			if(fs[i]!=max&submax<max){
				if (submax<=fs[i]){
					submax=fs[i];
				}
				else if(submax>fs[i]){
				}
			}
			else if(submax==max){
				break;
			}
		}
		return submax;
	}
}

