package com.sure.algorithm;

import javax.xml.transform.Source;
import java.net.SocketTimeoutException;

/**
 * Created by SURE on ${DATA}.
 */
public class AHP {

    // 平均随机一致性指针
    private double[] RI = {0.00, 0.00, 0.58, 0.90, 1.12, 1.21, 1.32, 1.41, 1.45, 1.49};

    // 随机一致性比率
    private double CR = 0.0;

    // 最大特征值
    private double lamdba = 0.0;


    public double[] weight(double[][] judgeMatrix) {
        int N = judgeMatrix.length;
        //1.按列做归一化处理
        double[] columnSum = new double[N];
        for (int j = 0; j < N; j++) {
            double t = 0.0;
            for (int i = 0; i < N; i++) {
                t += judgeMatrix[i][j];
            }
            columnSum[j] = t;
        }
        double[][] Q = new double[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                Q[i][j] = judgeMatrix[i][j] / columnSum[j];
            }
        }
        //2.将Q的元素按行相加
        double[] sumRowOfQ = new double[N];
        double sum = 0.0;
        for (int i = 0; i < N; i++) {
            double t = 0.0;
            for (int j = 0; j < N; j++) {
                t += Q[i][j];
            }
            sumRowOfQ[i] = t;
            sum += t;
        }
        //3.归一化sumRowOfQ，得权重向量w
        double[] w = new double[N];
        for (int i = 0; i < N; i++) {
            w[i] = sumRowOfQ[i] / sum;
        }
        //4.求最大特征向量
        for (int i = 0; i < N; i++) {
            double t = 0.0;
            for (int j = 0; j < N; j++) {
                t += judgeMatrix[i][j] * w[j];
            }
            lamdba += t / w[i];
        }
        lamdba = lamdba / N;
        System.out.println("lamdba:" + lamdba);
        //5.一致性检验
        double CI = (lamdba - N) / (N - 1);
        if (RI[N - 1] != 0) {
            CR = CI / RI[N - 1];
        }
        System.out.println("一致性校验参数" + CR);
        if (CR >= 0.1) {
            System.out.println("一致性检验不通过");
            return new double[N];
        }
        return w;
    }

    class fraction{
        private int numerator;
        private int denominator;
        public fraction(int numerator, int denominator){
            this.numerator=numerator;
            this.denominator=denominator;
        }
        public double getRet(){
            return (double)numerator/denominator;
        }
        @Override
        public String toString(){
            return numerator+"/"+denominator;
        }
    }

    public static void main(String[] args) {
        AHP ahp = new AHP();
        double[][] judgeMatrix = new double[][]{{1,   2,   2,   1,   1},
                                                {1/2, 1,   1,   1/2,   1/2},
                                                {1/2, 1,   1,   1/2,   1/2},
                                                {1,   2,   2,   1,   1},
                                                {1,   2,   2,   1,   1}};
        double[][] test = new double[][]{{1,   ahp.new fraction(1,3).getRet(), ahp.new fraction(1,3).getRet()},
                {3,   1,   1},
                {3,   1,   1}};
        double[][] judgeMatrix1 = new double[][]{{1,   2,   2,   2,   1},
                                                 {ahp.new fraction(1,2).getRet(), 1,   1,   1,   ahp.new fraction(1,2).getRet()},
                                                 {ahp.new fraction(1,2).getRet(), 1,   1,   1,   ahp.new fraction(1,2).getRet()},
                                                 {ahp.new fraction(1,2).getRet(), 1,   1,   1,   ahp.new fraction(1,2).getRet()},
                                                 {1,   2,   2,   2,   1}};
        double[] w = ahp.weight(judgeMatrix1);

        for (int i = 0; i < w.length; i++) {
            System.out.print(w[i] + " ");
        }

    }

}
