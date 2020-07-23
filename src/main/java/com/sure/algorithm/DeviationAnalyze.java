package com.sure.algorithm;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.function.DoubleToLongFunction;

/**
 * 采用
 * Created by SURE on ${DATA}.
 */
public class DeviationAnalyze {

    public int targetCnt;     //属性个数

    public int regionCnt;    //地区个数

    public DeviationAnalyze(int targetCnt, int regionCnt) {
        this.targetCnt = targetCnt;
        this.regionCnt = regionCnt;
    }


    int bestRegionIdx = 0;

    public double[][] computeDevaition(double[][] scoresOfTarget, List<Double> weightsOfTarget) {
        //1.计算总体评分，并选出最优地区
        double[] scoreOfTotal = new double[regionCnt];
        double[] sumOfTarget = new double[targetCnt];
        for (int i = 0; i < regionCnt; i++) {
            for (int j = 0; j < targetCnt; j++) {
                scoreOfTotal[i] += (scoresOfTarget[j][i] * weightsOfTarget.get(j));
                sumOfTarget[j] = sumOfTarget[j] + scoresOfTarget[j][i];
            }
        }
        for (int i = 1; i < regionCnt; i++) {
            if (scoreOfTotal[i] >= scoreOfTotal[bestRegionIdx]) {
                bestRegionIdx = i;
            }
        }
        //2.计算基本偏差率，偏差贡献
        double[][] deviationRateMatrix = new double[targetCnt][regionCnt];
        double[] sumOfColumn = new double[regionCnt];
        for (int i = 0; i < targetCnt; i++) {
            for (int j = 0; j < regionCnt; j++) {
                deviationRateMatrix[i][j] = (scoresOfTarget[i][bestRegionIdx] - scoresOfTarget[i][j]) / sumOfTarget[i] * weightsOfTarget.get(i);
//                System.out.println(deviationRateMatrix[i][j]);
                sumOfColumn[j] += deviationRateMatrix[i][j];
            }
//            System.out.println("---");
        }
        //3.计算评价对象各属性偏差贡献率
        double[][] contributionRate = new double[targetCnt][regionCnt];
        for (int i = 0; i < targetCnt; i++) {
            for (int j = 0; j < regionCnt; j++) {
                contributionRate[i][j] = deviationRateMatrix[i][j] / sumOfColumn[j];
//                System.out.println(contributionRate[i][j] );
            }
//            System.out.println("---");
        }
        return deviationRateMatrix;
    }

    /**
     * @param contributionRate
     * @param regionIdx        确定比较哪个对象
     * @param
     * @param data
     */
    public double[] analyzeIndex(double[][] contributionRate, int regionIdx, List<List<Double>> data, int[][] dataLocation, List<List<Double>> weightsOfIndex, int yearIdx, int interval) {
        //1.数据归一化
        dataNormalization(data);
        //2.找到偏差贡献率最大的属性
        int maxContributeIdx = 0;
        double maxContribute = 0;
        for (int i = 0; i < targetCnt; i++) {
            if (contributionRate[i][regionIdx] > maxContribute) {
                maxContributeIdx = i;
                maxContribute = contributionRate[i][regionIdx];
            }
        }
        //3.计算该属性下的指标贡献率
        int start = dataLocation[maxContributeIdx][0];
        int end = dataLocation[maxContributeIdx][1];
        List<Double> currentWeight = weightsOfIndex.get(maxContributeIdx);
        double[] indexContribute = new double[end - start];
        double indexContributeSum = 0;
        for (int i = start; i < end; i++) {
            double indexSum = 0.0;
            List<Double> curIndexData = data.get(i);
            for (int j = 0; j < interval; j++) {
                indexSum += curIndexData.get(j * interval + yearIdx);
            }
            indexContribute[i - start] = currentWeight.get(i - start) *
                    (curIndexData.get(bestRegionIdx * interval + yearIdx) - curIndexData.get(regionIdx * interval + yearIdx)) / indexSum;
            indexContributeSum += indexContribute[i - start];
        }
        //4.计算指标贡献偏差
        double[] indexContributeRate = new double[end - start];
        for (int i = 0; i < end - start; i++) {
            System.out.println(indexContribute[i]);
            indexContributeRate[i] = indexContribute[i] / indexContributeSum;
        }

        return indexContributeRate;
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

    private static Scanner s;
    private static Scanner sc;

    public static void main(String[] args) {

        DeviationAnalyze deviation = new DeviationAnalyze(5, 3);

        //1一年各地区各属性得分
        double[][] scoresOfTarget = new double[deviation.targetCnt][deviation.regionCnt];


        /*
        double[][] zhuanzhijuzhen = {
                {0.0638,	0.0863, 0.4203,	0.5288,	0.5967 },
                {0.1427,	0.1282,	0.0761,	0.1398,	0.1821},
                {0.5041,	0.2507,	0.1471,	0.1154,	0.0623}
        };
//*/




/*
        double[][] zhuanzhijuzhen = {
                {0.3552,	0.3482,	0.7884,	0.8124,	0.8737},
                {0.5409,	0.4386,	0.4508,	0.4529,	0.5113},
                {0.6995,	0.4180,	0.5682,	0.2914,	0.1871}

                };
//        */



///*
        double[][] zhuanzhijuzhen = {
                {0.7200,	0.7711,	0.9561,	0.8838,	1.0000},
                {0.9211,	0.7421,	0.9263,	0.8619,	0.6344},
                {0.9579,	0.9571,	0.7152,	0.7349,	0.7614},
        };
//        */


        for (int i = 0; i < scoresOfTarget.length; i++) {
            for (int j = 0; j < scoresOfTarget[0].length; j++) {
                scoresOfTarget[i][j] = zhuanzhijuzhen[j][i];
            }
        }



        //属性权重
        List<Double> weightsOfTarget = Arrays.asList(0.2987, 0.1593, 0.2987, 0.1040, 0.1393);

        double[][] contributionRate = deviation.computeDevaition(scoresOfTarget, weightsOfTarget);


        List<List<Double>> matrix = new ArrayList<>();
//        try (BufferedReader br = new BufferedReader(new FileReader("0209.txt"))) {
        try (BufferedReader br = new BufferedReader(new FileReader("indexCooidinate.txt"))) {
            String tmp = null;
            for (int j = 0; j < 26; j++) {
                tmp = br.readLine();
                sc = new Scanner(tmp);
                if (j >= 0) {
                    List<Double> one = new ArrayList<>();
                    for (int i = 0; i < 9; i++) {
                        one.add(sc.nextDouble());
                    }
                    matrix.add(one);
                }

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        int[][] dataLocation = {{0, 6}, {6, 10}, {10, 16}, {16, 22}, {22, 26}};

        //各个属性下的指标权重
        List<List<Double>> weigtsss = new ArrayList<>();
        weigtsss.add(Arrays.asList(0.1721,
        0.1695,
        0.1672,
        0.1721,
        0.1712,
        0.1480
        ));
        weigtsss.add(Arrays.asList(0.2538,
        0.2368,
        0.2853,
        0.2242

        ));
        weigtsss.add(Arrays.asList(0.1929,
        0.1942,
        0.1555,
        0.1575,
        0.1598,
        0.1402

        ));
        weigtsss.add(Arrays.asList(0.1711,
        0.1884,
        0.1549,
        0.1621,
        0.1522,
        0.1713

        ));
        weigtsss.add(Arrays.asList(0.2516,
        0.2676,
        0.2361,
        0.2446

        ));

//        deviation.analyzeIndex(contributionRate, 1, matrix, dataLocation, weigtsss, 0, 3);
//        System.out.println("------");
//        deviation.analyzeIndex(contributionRate, 2, matrix, dataLocation, weigtsss, 0, 3);
//        deviation.analyzeIndex(contributionRate, 1, matrix, dataLocation, weigtsss, 1, 3);
//        System.out.println("----");
//        deviation.analyzeIndex(contributionRate, 2, matrix, dataLocation, weigtsss, 1, 3);
        deviation.analyzeIndex(contributionRate, 1, matrix, dataLocation, weigtsss, 2, 3);
        System.out.println("---");
        deviation.analyzeIndex(contributionRate, 2, matrix, dataLocation, weigtsss, 2, 3);


    }
}
