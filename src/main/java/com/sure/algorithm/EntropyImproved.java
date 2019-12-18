package com.sure.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by SURE on ${DATA}.
 * 改进熵权法在岩石边坡生态护坡效果综合评价中的应用
 */
public class EntropyImproved {

    public static List<Double> getWeight(List<List<Double>> data) {
        List<Double> listSum = new ArrayList<Double>();    //用于存放每种指标下所有记录归一化后的和
        List<Double> entropys = new ArrayList<>();    //用于存在各个指标的熵
        List<Double> differences = new ArrayList<Double>();    //用于存放每种指标的差异系数
        List<Double> weights = new ArrayList<Double>();    //用于存放每种指标的最终权重系数
        int n = data.get(0).size();
        double sumLast = 0;
        double k = 1 / Math.log(n); //计算k值 k= 1/ln(n)
        //数据归一化处理	(i-min)/(max-min)
        for (int i = 0; i < data.size(); i++) {
            double sum = 0;
            double max = Collections.max(data.get(i));
            double min = Collections.min(data.get(i));
            for (int j = 0; j < data.get(i).size(); j++) {
                double temp = (data.get(i).get(j) - min) / (max - min);
                sum += temp;
                data.get(i).set(j, temp);
            }
            listSum.add(sum);
        }


        //计算每项指标下每个记录所占比重
        for (int i = 0; i < data.size(); i++) {
            double sum = 0;    //每种指标下所有记录权重和
            for (int j = 0; j < data.get(i).size(); j++) {
                if (data.get(i).get(j) / listSum.get(i) == 0) {
                    sum += 0;
                } else {
                    sum += (data.get(i).get(j) / listSum.get(i)) * Math.log(data.get(i).get(j) / listSum.get(i));
                }

            }
            //计算第i项指标的熵值
            double e = -k * sum;
            entropys.add(e);
            System.out.println(e);
            //计算第j项指标的差异系数
            double g = 1 - e;
            sumLast += g;
            differences.add(g);
        }


        //计算每项指标的权重
        //1.熵不为1的平均值
        double entropyAverage = 0;
        double entropySum = 0;
        for (double x : entropys) {
            entropySum += x;
        }
        entropyAverage = entropySum / n;

        //2. 1 - ej 求和， 1 - ej + entropyAverage 求和
        double sumfirst = 0;
        double sumSecond = 0;
        for (double x : entropys) {
            sumfirst += 1 - x;
            sumSecond += 1 - x + entropyAverage;
        }

        //计算权重
        int sum = 0;
        for (double x : entropys) {
            double weight = (1 - entropyAverage) * (1 - x) / sumfirst + entropyAverage * (1 - x + entropyAverage) / sumSecond;
            weights.add(weight);
            sum += weight;
        }
        System.out.println(sum);
        return weights;
    }

    public static void main(String[] args) {
        List<List<Double>> matrix = new ArrayList<>();
        /*论文四个
        List<Double> first = new ArrayList<>();
        first.add(1.0);
        first.add(1.0);
        first.add(1.0);
        first.add(5.0);
        first.add(1.0);
        List<Double> second = new ArrayList<>();
        second.add(1.0);
        second.add(2.0);
        second.add(3.0);
        second.add(4.0);
        second.add(5.0);

        List<Double> third = new ArrayList<>();
        third.add(5.0);
        third.add(1.0);
        third.add(5.0);
        third.add(5.0);
        third.add(5.0);

        List<Double> forth = new ArrayList<>();
        forth.add(3.0);
        forth.add(3.0);
        forth.add(3.0);
        forth.add(3.0);
        forth.add(3.0);
        matrix.add(first);
        matrix.add(second);
        matrix.add(third);
        matrix.add(forth);
        */

        List<Double> A = new ArrayList<>();
        A.add(100.0);
        A.add(100.0);
        A.add(75.0);
        A.add(100.0);
        A.add(100.0);
        A.add(100.0);
        A.add(100.0);
        A.add(87.5);
        A.add(100.0);
        A.add(100.0);
        A.add(100.0);
        List<Double> B = new ArrayList<>();
        B.add(90.0);
        B.add(100.0);
        B.add(100.0);
        B.add(100.0);
        B.add(90.0);
        B.add(100.0);
        B.add(100.0);
        B.add(100.0);
        B.add(100.0);
        B.add(90.0);
        B.add(100.0);
        List<Double> C = new ArrayList<>();
        C.add(100.0);
        C.add(78.6);
        C.add(85.7);
        C.add(78.6);
        C.add(100.0);
        C.add(100.0);
        C.add(78.6);
        C.add(85.7);
        C.add(92.9);
        C.add(100.0);
        C.add(92.9);
        List<Double> D = new ArrayList<>();
        D.add(84.0);
        D.add(100.0);
        D.add(100.0);
        D.add(100.0);
        D.add(100.0);
        D.add(100.0);
        D.add(100.0);
        D.add(100.0);
        D.add(100.0);
        D.add(100.0);
        D.add(100.0);
        List<Double> E = new ArrayList<>();
        E.add(90.0);
        E.add(90.0);
        E.add(90.0);
        E.add(90.0);
        E.add(100.0);
        E.add(90.0);
        E.add(90.0);
        E.add(100.0);
        E.add(80.0);
        E.add(100.0);
        E.add(90.0);
        List<Double> F = new ArrayList<>();
        F.add(100.0);
        F.add(100.0);
        F.add(100.0);
        F.add(100.0);
        F.add(90.0);
        F.add(100.0);
        F.add(100.0);
        F.add(100.0);
        F.add(100.0);
        F.add(100.0);
        F.add(100.0);
        List<Double> G = new ArrayList<>();
        G.add(100.0);
        G.add(100.0);
        G.add(100.0);
        G.add(94.4);
        G.add(100.0);
        G.add(100.0);
        G.add(55.6);
        G.add(100.0);
        G.add(100.0);
        G.add(100.0);
        G.add(100.0);
        List<Double> H = new ArrayList<>();
        H.add(100.0);
        H.add(100.0);
        H.add(100.0);
        H.add(100.0);
        H.add(100.0);
        H.add(85.7);
        H.add(100.0);
        H.add(100.0);
        H.add(100.0);
        H.add(100.0);
        H.add(100.0);
        List<Double> I = new ArrayList<>();
        I.add(100.0);
        I.add(100.0);
        I.add(100.0);
        I.add(100.0);
        I.add(80.0);
        I.add(100.0);
        I.add(100.0);
        I.add(100.0);
        I.add(100.0);
        I.add(100.0);
        I.add(100.0);

        matrix.add(A);
        matrix.add(B);
        matrix.add(C);
        matrix.add(D);
        matrix.add(E);
        matrix.add(F);
        matrix.add(G);
        matrix.add(H);
        matrix.add(I);


        System.out.println(EntropyImproved.getWeight(matrix));

    }


}
