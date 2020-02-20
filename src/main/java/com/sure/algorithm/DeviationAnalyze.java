package com.sure.algorithm;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.function.DoubleToLongFunction;

/**
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
                sumOfColumn[j] += deviationRateMatrix[i][j];
            }
        }
        //3.计算评价对象各属性偏差贡献率
        double[][] contributionRate = new double[targetCnt][regionCnt];
        for (int i = 0; i < targetCnt; i++) {
            for (int j = 0; j < regionCnt; j++) {
                System.out.println(deviationRateMatrix[i][j]);
                contributionRate[i][j] = deviationRateMatrix[i][j] / sumOfColumn[j];
            }
            System.out.println("sss");
        }
        return contributionRate;
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


//        /*
        double[][] zhuanzhijuzhen = {
                {0.6018, 	0.5236, 	0.6639, 	0.5853, 	0.4856 },
                {0.3784, 	0.0147, 	0.4828, 	0.0604, 	0.2015},
                {0.0335, 	0.3167, 	0.0793, 	0.3362, 	0.1157}
        };
//*/


        /*
        double[][] zhuanzhijuzhen = {
                {0.7515, 	0.8210, 	0.8043, 	0.7227,	0.6475},
                {0.5783, 	0.1314, 	0.6408, 	0.4001,	0.3196},
                {0.4449, 	0.5142, 	0.2817, 	0.4296,	0.1998}
        };
//        */

/*
        double[][] zhuanzhijuzhen = {
                {0.9457, 0.9853, 0.8793, 0.9089, 1.0000},
                {0.8879, 0.7199, 0.8191, 0.7538, 0.3532},
                {0.6279, 0.7226, 0.6552, 0.5136, 0.7926},
        };

 */
        for (int i = 0; i < scoresOfTarget.length; i++) {
            for (int j = 0; j < scoresOfTarget[0].length; j++) {
                scoresOfTarget[i][j] = zhuanzhijuzhen[j][i];
            }
        }

        /*
        //1
        scoresOfTarget[0] = new double[]{0.5970, 0.3808, 0.0330};
        scoresOfTarget[1] = new double[]{0.4918, 0.0141, 0.3003};
        scoresOfTarget[2] = new double[]{0.6842, 0.3457, 0.0906};
        scoresOfTarget[3] = new double[]{0.5853, 0.0745, 0.3362};
        scoresOfTarget[4] = new double[]{0.4753, 0.1940, 0.1190};
        */



        /*
        //2
        scoresOfTarget[0] = new double[]{0.7474, 0.5758, 0.4457};
        scoresOfTarget[1] = new double[]{0.8066, 0.0964, 0.4893};
        scoresOfTarget[2] = new double[]{0.8080, 0.6267, 0.4632};
        scoresOfTarget[3] = new double[]{0.7227, 0.3937, 0.4296};
        scoresOfTarget[4] = new double[]{0.6456, 0.5043, 0.1981};
*/

        /*
        //3
        scoresOfTarget[0] = new double[]{0.9288, 0.6975, 0.7589};
        scoresOfTarget[1] = new double[]{0.9859, 0.6835, 0.6991};
        scoresOfTarget[2] = new double[]{0.8707, 0.6863, 0.7836};
        scoresOfTarget[3] = new double[]{0.9089, 0.7538, 0.5136};
        scoresOfTarget[4] = new double[]{1.0000, 0.5329, 0.2839};
*/
        //属性权重
        List<Double> weightsOfTarget = Arrays.asList(0.2987, 0.1593, 0.2987, 0.1040, 0.1393);

        double[][] contributionRate = deviation.computeDevaition(scoresOfTarget, weightsOfTarget);


        List<List<Double>> matrix = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("0209.txt"))) {
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
        weigtsss.add(Arrays.asList(0.1383,
                0.1377,
                0.2383,
                0.1402,
                0.2046,
                0.1409
        ));
        weigtsss.add(Arrays.asList(0.2569,
                0.2413,
                0.2186,
                0.2832
        ));
        weigtsss.add(Arrays.asList(0.1541,
                0.1594,
                0.1972,
                0.1671,
                0.1705,
                0.1517
        ));
        weigtsss.add(Arrays.asList(0.1618,
                0.1336,
                0.1937,
                0.1398,
                0.1446,
                0.2264
        ));
        weigtsss.add(Arrays.asList(0.2350,
                0.2931,
                0.2246,
                0.2473
        ));

        deviation.analyzeIndex(contributionRate, 2, matrix, dataLocation, weigtsss, 2, 3);


    }
}
