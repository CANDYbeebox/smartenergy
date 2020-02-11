package com.sure.algorithm;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * Created by SURE on ${DATA}.
 */
public class BalanceEvaluation {

    public double[] computeBalanceNumber(List<List<Double>> data) {
        //m个评价对象，n个指标
        int k = data.size();
        int m = data.get(0).size();
        //原始数据归一化
        dataNormalization(data);
        //计算每一行的平均值
        double[] averages = new double[m];
        for (int i = 0; i < m; i++) {
            double sum = 0.0;
            for (int j = 0; j < k; j++) {
                sum = sum + data.get(j).get(i);
            }
            averages[i] = sum / k;
        }
        //计算标准差
        double[] standardDeviations = new double[m];
        for (int i = 0; i < m; i++) {
            double sum = 0.0;
            for (int j = 0; j < k; j++) {
                sum += Math.pow(data.get(j).get(i) - averages[i], 2);
            }
            standardDeviations[i] = Math.sqrt(sum);
        }
        //计算各个评价对象的指标平衡系数系数
        double[] balanceNumbers = new double[m];
        double sumStandardDeviation = 0.0;
        for (double standardDeviation : standardDeviations) {
            sumStandardDeviation += standardDeviation;
        }
        for (int i = 0; i < m; i++) {
            balanceNumbers[i] = 1 - standardDeviations[i] / (sumStandardDeviation / m * k);
        }
        return balanceNumbers;

    }

    public void dataNormalization(List<List<Double>> data) {
        for (int i = 0; i < data.size(); i++) {
            double max = Collections.max(data.get(i));
            double min = Collections.min(data.get(i));
            for (int j = 0; j < data.get(i).size(); j++) {
                double temp = (data.get(i).get(j) - min) / (max - min);
                data.get(i).set(j, temp);
            }
        }
    }

    public static void main(String[] args) {
        List<List<Double>> data = new ArrayList<>();
        List<List<Double>> matrix = new ArrayList<>();
        Scanner sc;
        try (BufferedReader br = new BufferedReader(new FileReader("balance.txt"))) {
            String tmp = null;
            while ((tmp = br.readLine()) != null) {
                sc = new Scanner(tmp);
                List<Double> one = new ArrayList<>();
                for (int i = 0; i < 15; i++) {
                    one.add(sc.nextDouble());
                }
                matrix.add(one);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("sss");
        BalanceEvaluation balanceEvaluation = new BalanceEvaluation();
        balanceEvaluation.computeBalanceNumber(matrix);
    }

}
