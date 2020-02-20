package com.sure.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by SURE on ${DATA}.
 */
public class IndexCoordinate {

    public double calculatePearson(List<Double> xList, List<Double> yList) {
        //1.计算分子
        double numerator = 0.0;
        double temp = 0.0;

        int xSize = xList.size();
        for (int x = 0; x < xSize; x++) {
            temp += xList.get(x);
        }
        double xAverage = temp / xSize;

        temp = 0.0;
        int ySize = yList.size();
        for (int x = 0; x < ySize; x++) {
            temp += yList.get(x);
        }
        double yAverage = temp / ySize;

        for (int x = 0; x < xSize; x++) {
            numerator += (xList.get(x) - xAverage) * (yList.get(x) - yAverage);
        }
        //2.计算分母
        double denominator = 0.0;
        int size = xList.size();
        double xException = 0.0;
        double yException = 0.0;

        for (int i = 0; i < size; i++) {
            xException += Math.pow(xList.get(i) - xAverage, 2);
            yException += Math.pow(yList.get(i) - yAverage, 2);
        }
        denominator = Math.sqrt(xException * yException);
        return numerator / denominator;
    }

    public double calculateIndexCoordinationCoefficient(List<List<Double>> lists) {
        double indexCoordinationCoefficient = 0.0;
        //1.计算全部皮尔森系数
        int size = lists.size();
        List<Double> pearsons = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            List<Double> xList = lists.get(i);
            for (int j = i + 1; j < size; j++) {
                List<Double> yList = lists.get(j);
                pearsons.add(calculatePearson(xList, yList));
            }
        }
        //2.计算指标协调因子
        indexCoordinationCoefficient = pearsons.stream().mapToDouble(Double::doubleValue).sum() /
                                        (size * (size - 1) / 2);
        return indexCoordinationCoefficient;
    }

    public static void main(String[] args) {
        IndexCoordinate indexCoordinate = new IndexCoordinate();
        List<Double> xList = Arrays.asList(1.0, 2.0, 3.0);
        List<Double> yList = Arrays.asList(1.0, 2.0, 3.0);

        List<List<Double>> lists = new ArrayList<>();
        lists.add(new ArrayList<>(xList));
        lists.add(new ArrayList<>(xList));
        lists.add(new ArrayList<>(xList));
        System.out.println(indexCoordinate.calculateIndexCoordinationCoefficient(lists));
    }


}
