package com.sure.algorithm;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by SURE on ${DATA}.
 */
public class Deviation {

    public int targetCnt;     //属性个数

    public int regionCnt;    //地区个数

    public Deviation(int targetCnt, int regionCnt) {
        this.targetCnt = targetCnt;
        this.regionCnt = regionCnt;
    }

    int bestRegionIdx = 0;

    public double[][] computeDevaition(List<Integer> indexCnts, List<List<Topsis.Alternative>> als, List<List<Double>> weightsOfIndex, List<Double> weightsOfTarget) {
        //1.计算各属性的topsis得分
        double[][] scoresOfTarget = new double[targetCnt][regionCnt];
        for (int i = 0; i < targetCnt; i++) {
            List<Double> weights = weightsOfIndex.get(i);
            List<Topsis.Alternative> al = als.get(i);
            Topsis topsis = new Topsis(indexCnts.get(i), weights);
            topsis.scoreAndsort(al, topsis.indexCnt);
            for (int j = 0; j < regionCnt; j++) {
                scoresOfTarget[i][j] = al.get(j).c;
            }
        }
        //计算总体得分，选择最优者
        Topsis topsis = new Topsis(indexCnts.get(indexCnts.size() - 1), weightsOfIndex.get(indexCnts.size() - 1));
        List<Topsis.Alternative> totalScores = topsis.scoreAndsort(als.get(als.size() - 1), topsis.indexCnt);
        double mostScore = 0;
        for (int i = 0; i < totalScores.size(); i++) {
            if (totalScores.get(i).c > mostScore) {
                mostScore = totalScores.get(i).c;
                bestRegionIdx = i;
            }
        }
        //2.计算基本偏差率，偏差贡献
        double[][] deviationRateMatrix = new double[targetCnt][regionCnt];
        double[] sumOfColumn = new double[regionCnt];
        for (int i = 0; i < targetCnt; i++) {
            for (int j = 0; j < regionCnt; j++) {
                deviationRateMatrix[i][j] = (scoresOfTarget[i][bestRegionIdx] - scoresOfTarget[i][j]) / (scoresOfTarget[i][bestRegionIdx] + 0.1) * weightsOfTarget.get(i);
                sumOfColumn[j] += deviationRateMatrix[i][j];
            }
        }
        //3.计算评价对象各属性偏差贡献率
        double[][] contributionRate = new double[targetCnt][regionCnt];
        for (int i = 0; i < targetCnt; i++) {
            for (int j = 0; j < regionCnt; j++) {
                contributionRate[i][j] = deviationRateMatrix[i][j] / sumOfColumn[j];
            }
        }
        return contributionRate;
    }

    /**
     * @param contributionRate
     * @param regionIdx        确定比较哪个对象
     * @param
     * @param data
     */
    public double[] analyzeIndex(double[][] contributionRate, int regionIdx, List<List<Double>> data, int[][] dataLocation, List<List<Double>> weightsOfIndex) {
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
            List<Double> curIndexData = data.get(i);
            indexContribute[i - start] = currentWeight.get(i - start) * (curIndexData.get(bestRegionIdx) - curIndexData.get(regionIdx)) / (curIndexData.get(bestRegionIdx) + 0.1);
            indexContributeSum += indexContribute[i - start];
        }
        //4.计算指标贡献偏差
        double[] indexContributeRate = new double[end - start];
        for (int i = 0; i < end - start; i++) {
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
    private static  Scanner sc;

    public static void main(String[] args) {
        List<List<Double>> matrix = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("originData.txt"))) {
            String tmp = null;
            for (int j = 0; j < 22; j++) {
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

        List<Topsis.Alternative> al = new LinkedList<Topsis.Alternative>();
        Double[] weights = {24.81,
                25.515,
                24.93,
                26.655,
                24.15,
                23.94,
                29.7,
                29.835,
                31.185,
                30.645,
                28.635,
                48.64,
                51.36,
                50.17,
                49.83,
                82.7,
                83.75,
                83.55,
                61.65,
                64.325,
                60.725,
                63.3,
        };    //各指标权重
        List<Double> weigt = Arrays.asList(weights);
        Topsis topsis = new Topsis(22, weigt);
        double[] A1 = new double[topsis.indexCnt];
        double[] A2 = new double[topsis.indexCnt];
        double[] A3 = new double[topsis.indexCnt];
        double[] B1 = new double[topsis.indexCnt];
        double[] B2 = new double[topsis.indexCnt];
        double[] B3 = new double[topsis.indexCnt];
        double[] C1 = new double[topsis.indexCnt];
        double[] C2 = new double[topsis.indexCnt];
        double[] C3 = new double[topsis.indexCnt];
        try (BufferedReader br = new BufferedReader(new FileReader("deviationData.txt"))) {
            String tmp = null;
            for (int i = 0; i < 22; i++) {
                tmp = br.readLine();
                s = new Scanner(tmp);
                A1[i] = s.nextDouble();
                A2[i] = s.nextDouble();
                A3[i] = s.nextDouble();
                B1[i] = s.nextDouble();
                B2[i] = s.nextDouble();
                B3[i] = s.nextDouble();
                C1[i] = s.nextDouble();
                C2[i] = s.nextDouble();
                C3[i] = s.nextDouble();
            }
//            al.add(new Alternative("A1", A1));
//            al.add(new Alternative("A2", A2));
            al.add(topsis.new Alternative("A3", A3));
//            al.add(new Alternative("B1", B1));
//            al.add(new Alternative("B2", B2));
            al.add(topsis.new Alternative("B3", B3));
//            al.add(new Alternative("C1", C1));
//            al.add(new Alternative("C2", C2));
            al.add(topsis.new Alternative("C3", C3));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Deviation deviation = new Deviation(6, 3);
        List<List<Topsis.Alternative>> als = new ArrayList<>();
        List<Topsis.Alternative> alternative1 = new ArrayList<>();
        als.add(alternative1);
        Topsis topsis1 = new Topsis(6, (weigt.subList(0, 6)));
        alternative1.add(topsis1.new Alternative("A3ONE", new double[]{A3[0], A3[1], A3[2], A3[3], A3[4], A3[5]}));
        alternative1.add(topsis1.new Alternative("B3ONE", new double[]{B3[0], B3[1], B3[2], B3[3], B3[4], B3[5]}));
        alternative1.add(topsis1.new Alternative("C3ONE", new double[]{C3[0], C3[1], C3[2], C3[3], C3[4], C3[5]}));

        List<Topsis.Alternative> alternative2 = new ArrayList<>();
        als.add(alternative2);
        Topsis topsis2 = new Topsis(5, weigt.subList(6, 11));
        alternative2.add(topsis2.new Alternative("A3TWO", new double[]{A3[6], A3[7], A3[8], A3[9], A3[10]}));
        alternative2.add(topsis2.new Alternative("B3TWO", new double[]{B3[6], B3[7], B3[8], B3[9], B3[10]}));
        alternative2.add(topsis2.new Alternative("C3TWO", new double[]{C3[6], C3[7], C3[8], C3[9], C3[10]}));

        List<Topsis.Alternative> alternative3 = new ArrayList<>();
        als.add(alternative3);
        Topsis topsis3 = new Topsis(2, weigt.subList(11, 13));
        alternative3.add(topsis3.new Alternative("A3THREE", new double[]{A3[11], A3[12]}));
        alternative3.add(topsis3.new Alternative("B3THREE", new double[]{B3[11], B3[12]}));
        alternative3.add(topsis3.new Alternative("C3THREE", new double[]{C3[11], C3[12]}));

        List<Topsis.Alternative> alternative4 = new ArrayList<>();
        als.add(alternative4);
        Topsis topsis4 = new Topsis(2, weigt.subList(13, 15));
        alternative4.add(topsis4.new Alternative("A3FOUR", new double[]{A3[13], A3[14]}));
        alternative4.add(topsis4.new Alternative("B3FOUR", new double[]{B3[13], B3[14]}));
        alternative4.add(topsis4.new Alternative("C3FOUR", new double[]{C3[13], C3[14]}));

        List<Topsis.Alternative> alternative5 = new ArrayList<>();
        als.add(alternative5);
        Topsis topsis5 = new Topsis(3, weigt.subList(15, 18));
        alternative5.add(topsis5.new Alternative("A3FIVE", new double[]{A3[15], A3[16], A3[17]}));
        alternative5.add(topsis5.new Alternative("B3FIVE", new double[]{B3[15], B3[16], B3[17]}));
        alternative5.add(topsis5.new Alternative("C3FIVE", new double[]{C3[15], C3[16], C3[17]}));

        List<Topsis.Alternative> alternative6 = new ArrayList<>();
        als.add(alternative6);
        Topsis topsis6 = new Topsis(4, weigt.subList(18, 22));
        alternative6.add(topsis6.new Alternative("A3SIX", new double[]{A3[18], A3[19], A3[20], A3[21]}));
        alternative6.add(topsis6.new Alternative("B3SIX", new double[]{B3[18], B3[19], B3[20], B3[21]}));
        alternative6.add(topsis6.new Alternative("C3SIX", new double[]{C3[18], C3[19], C3[20], C3[21]}));
        als.add(al);


        List<List<Double>> weigtsss = new ArrayList<>();
        weigtsss.add(weigt.subList(0, 6));
        weigtsss.add(weigt.subList(6, 11));
        weigtsss.add(weigt.subList(11, 13));
        weigtsss.add(weigt.subList(13, 15));
        weigtsss.add(weigt.subList(15, 18));
        weigtsss.add(weigt.subList(18, 22));
        weigtsss.add(weigt);

        List<Double> weightsOfTarget = Arrays.asList(0.15, 0.15, 0.10, 0.10, 0.25, 0.25);

        double[][] contributionRate = deviation.computeDevaition(Arrays.asList(6, 5, 2, 2, 3, 4, 22), als, weigtsss, weightsOfTarget);

        int[][] dataLocation = {{0, 6}, {6, 11}, {11, 13}, {13, 15}, {15, 18}, {18, 22}};
        deviation.analyzeIndex(contributionRate, 0, matrix, dataLocation, weigtsss);


    }
}
