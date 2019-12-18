package com.sure.algorithm;

import java.math.BigDecimal;
import java.util.Arrays;

/**
 * Created by SURE on ${DATA}.
 */
public class AHPCompute2 {
    /**
     * @param args
     */
    public static void main(String[] args) {
        /** a为N*N矩阵 */
        double[][] a = new double[][] { { 1, 1, 1, 1 }, { 1, 1, 1, 1 },{ 1, 1, 1, 1 },{ 1, 1, 1, 1 }};
        int N = a[0].length;
        double[] weight = new double[N];
        AHPCompute2 instance = AHPCompute2.getInstance();
        instance.weight(a, weight, N);
        System.out.println(Arrays.toString(weight));
    }

    // 单例
    private static final AHPCompute2 acw = new AHPCompute2();

    // 平均随机一致性指针
    private double[] RI = { 0.00, 0.00, 0.58, 0.90, 1.12, 1.21, 1.32, 1.41,
            1.45, 1.49 };

    // 随机一致性比率
    private double CR = 0.0;

    // 最大特征值
    private double lamdba = 0.0;

    /**
     * 私有构造
     */
    private AHPCompute2() {

    }

    /**
     * 返回单例
     *
     * @return
     */
    public static AHPCompute2 getInstance() {
        return acw;
    }

    /**
     * 计算权重
     *
     * @param a
     * @param weight
     * @param N
     */
    public void weight(double[][] a, double[] weight, int N) {

        double[] w1 = new double[N];
        double[] w2 = new double[N];
        double sum = 0.0;

        //按行求和
        for (int j = 0; j < N; j++) {
            double t = 0.0;
            for (int l = 0; l < N; l++)
                t += a[l][j];
            w1[j] = t;
        }

        //按行归一化，然后按列求和，最后得到特征向量w2
        for (int k = 0; k < N; k++) {
            sum = 0;
            for (int i = 0; i < N; i++) {
                sum = sum + a[k][i] / w1[i];
            }
            w2[k] = sum / N;
        }

        lamdba = 0.0;

        //求矩阵和特征向量的积,然后求出特征值
        for (int k = 0; k < N; k++) {
            sum = 0;
            for (int i = 0; i < N; i++) {
                sum = sum + a[k][i] * w2[i];
            }
            w1[k] = sum;
            lamdba = lamdba + w1[k] / w2[k];
        }

        // 计算矩阵最大特征值lamta，CI，RI
        lamdba = lamdba / N;

        double CI = (lamdba - N) / (N - 1);

        if (RI[N - 1] != 0) {
            CR = CI / RI[N - 1];
        }

        // 四舍五入处理
        lamdba = round(lamdba, 3);
        CI = round(CI, 3);
        CR = round(CR, 3);

        for (int i = 0; i < N; i++) {
            w1[i] = round(w1[i], 4);
            w2[i] = round(w2[i], 4);
        }
        // 控制台打印输出

        System.out.println("lamdba=" + lamdba);
        System.out.println("CI=" + CI);
        System.out.println("CR=" + CR);

        // 控制台打印权重
        System.out.println("");

        System.out.println("w1[]=");
        for (int i = 0; i < N; i++) {
            System.out.print(w1[i] + " ");
        }
        System.out.println("");

        System.out.println("w2[]=");
        for (int i = 0; i < N; i++) {
            weight[i] = w2[i];
            System.out.print(weight[i] + " ");
        }
        System.out.println("");
    }

    /**
     * 四舍五入
     *
     * @param v
     * @param scale
     * @return
     */
    public double round(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 返回随机一致性比率
     *
     * @return
     */
    public double getCR() {
        return CR;
    }
}

