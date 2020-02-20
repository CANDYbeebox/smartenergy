package com.sure.algorithm.pearson;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.DataOutput;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by SURE on ${DATA}.
 */
public class CallClass {


    public static void main(String[] args) throws IOException {


        double CORR = 0.0;
//
//        List<String> xList = Arrays.asList("0.9", "1.1", "4.8", "3.2", "7.8", "2.7", "1.6", "12.5", "1.0", "2.6", "0.3", "4.0", "0.8",
//                "3.5", "10.2", "3.0", "0.2", "0.4", "1.0", "6.8",
//                "11.6", "1.6", "1.2", "7.2", "3.2");
//
//        List<String> yList = Arrays.asList("67.3", "111.3", "173.0", "80.8", "199.7", "16.2", "107.4",
//                "185.4", "96.1", "72.8", "64.2", "132.2", "58.6", "174.6",
//                "263.5", "79.3", "14.8", "73.5", "24.7", "139.4", "368.2", "95.7", "109.6", "196.2", "102.2");

        List<String> xList = Arrays.asList("1", "2", "3");
        List<String> yList = Arrays.asList("3", "3", "9");


        NumeratorCalculate nc = new NumeratorCalculate(xList, yList);
        double numerator = nc.calcuteNumerator();
        DenominatorCalculate dc = new DenominatorCalculate();
        double denominator = dc.calculateDenominator(xList, yList);
        CORR = numerator / denominator;
        System.out.println("运算结果是：");
        System.out.printf("CORR = " + CORR);
    }
}
