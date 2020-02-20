package com.sure.algorithm.pearson;

import java.util.List;

/**
 * https://blog.csdn.net/m0_37691330/article/details/80791084
 * Created by SURE on ${DATA}.
 */
public class DenominatorCalculate {

    //add denominatorCalculate method
    public double calculateDenominator(List<String> xList, List<String> yList){
        double standardDifference = 0.0;
        int size = xList.size();
        double xAverage = 0.0;
        double yAverage = 0.0;
        double xException = 0.0;
        double yException = 0.0;
        double temp1=0.0;
        double temp2=0.0;
        for(int i=0;i<size;i++){
            temp1 += Double.parseDouble(xList.get(i));
        }
        xAverage = temp1/size;

        for(int i=0;i<size;i++){
            temp2 += Double.parseDouble(yList.get(i));
        }
        yAverage = temp2/size;

        for(int i=0;i<size;i++){
            xException += Math.pow(Double.parseDouble(xList.get(i))-xAverage,2);
            yException += Math.pow(Double.parseDouble(yList.get(i))-yAverage, 2);
        }
        //calculate denominator of
        return standardDifference = Math.sqrt(xException*yException);
    }
}
