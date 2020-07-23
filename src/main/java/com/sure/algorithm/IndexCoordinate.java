package com.sure.algorithm;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * 指标协调性因子计算
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
        System.out.println(indexCoordinationCoefficient + " ");
        return indexCoordinationCoefficient;
    }

    public void helper(double[][] data, int areaStart, int areaEnd, int indexStart, int indexEnd) {
        List<List<Double>> matrix = new ArrayList<>();
        for (int i = indexStart; i < indexEnd; i++) {
            List<Double> one = new ArrayList<>();
            for (int j = areaStart; j < areaEnd; j++) {
                one.add(data[i][j]);
            }
            matrix.add(one);
        }
        calculateIndexCoordinationCoefficient(matrix);
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
        IndexCoordinate indexCoordinate = new IndexCoordinate();

        Scanner sc;
        List<List<Double>> matrix = new ArrayList<>();
        List<List<Double>> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("indexCooidinate.txt"))) {
            String tmp = null;
            for (int i = 0; i < 26; i++) {
                tmp = br.readLine();
                sc = new Scanner(tmp);
                List<Double> one = new ArrayList<>();
                data.add(one);
                for (int j = 0; j < 9; j++) {
                    one.add(sc.nextDouble());
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        indexCoordinate.dataNormalization(data);

        double[][] ddd = new double[26][9];
        for (int i = 0; i < 26; i++) {
            List<Double> one = data.get(i);
            for (int j = 0; j < 9; j++) {
                ddd[i][j] = one.get(j);
            }
        }

        for (int i = 0; i < 9; i++) {
            System.out.println("----");
            for (int j = 0; j < 26; j++) {
                System.out.println(ddd[j][i]);
            }
        }

        //地区1
        System.out.println("地区1");
        indexCoordinate.helper(ddd, 0, 3, 0,6);
        indexCoordinate.helper(ddd, 0, 3, 6,10);
        indexCoordinate.helper(ddd, 0, 3, 10,16);
        indexCoordinate.helper(ddd, 0, 3, 16,22);
        indexCoordinate.helper(ddd, 0, 3, 22,26);

        //地区2
        System.out.println("地区2");
        indexCoordinate.helper(ddd, 3, 6, 0,6);
        indexCoordinate.helper(ddd, 3, 6, 6,10);
        indexCoordinate.helper(ddd, 3, 6, 10,16);
        indexCoordinate.helper(ddd, 3, 6, 16,22);
        indexCoordinate.helper(ddd, 3, 6, 22,26);

        //地区3
        System.out.println("地区3");
        indexCoordinate.helper(ddd, 6, 9, 0,6);
        indexCoordinate.helper(ddd, 6, 9, 6,10);
        indexCoordinate.helper(ddd, 6, 9, 10,16);
        indexCoordinate.helper(ddd, 6, 9, 16,22);
        indexCoordinate.helper(ddd, 6, 9, 22,26);

    }


}
