package com.sure.algorithm;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.*;

/**
 * 改进的熵权法
 * Created by SURE on ${DATA}.
 */
public class EntropyIm {

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
                System.out.println(String.format("%.4f", temp));
            }
            System.out.println("---");
            listSum.add(sum);
        }

//        EntropyIm.judgeSame(data);

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
//            System.out.println(e);
            //计算第j项指标的差异系数
            double g = 1 - e;
            sumLast += g;
            differences.add(g);
        }


        //计算每项指标的权重
        double thygema = 0.0;
        for (double x : entropys) {
            thygema = thygema + 1- x;
        }
        thygema = thygema / entropys.size() / 10 + 0.1;
        double sumEntropy = 0;

        for (double x : entropys) {
            sumEntropy += 1 - x + thygema;
        }

        double sum = 0;
        for (double x : entropys) {
            double weight = (1 - x + thygema) / sumEntropy;
            weights.add(weight);
            sum += weight;
        }
//        System.out.println("权重之和" + sum);
        return weights;
    }

    private static String formatDouble3(double d) {
        NumberFormat nf = NumberFormat.getNumberInstance(); // 保留3位小数
        nf.setMaximumFractionDigits(3);
        // 如果不需要四舍五入，可以使用RoundingMode.DOWN
        nf.setRoundingMode(RoundingMode.UP);
        return nf.format(d);
    }

    public static void helper(int start, int end) {
        List<List<Double>> matrix = new ArrayList<>();
        Scanner sc;
        try (BufferedReader br = new BufferedReader(new FileReader("indexCooidinate.txt"))) {
            String tmp = null;
            for (int j = 0; j < end; j++) {
                tmp = br.readLine();
                sc = new Scanner(tmp);
                if (j >= start) {
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
        List<Double> ret = EntropyIm.getWeight(matrix);
        for (int i = 0; i < ret.size(); i++) {
            System.out.println(String.format("%.4f", ret.get(i)));
        }
    }

    public static void judgeSame(List<List<Double>> data) {
        List<List<String>> lists = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            List<String> one = new ArrayList<>();
            lists.add(one);
            for (int j = 0; j < data.get(0).size(); j++) {
                one.add(        String.format("%.4f", data.get(i).get(j)));
            }
        }
        HashSet<String> hashSet = new HashSet<>();

        for (int i = 0; i < lists.size(); i++) {
            for (int j = 0; j < lists.get(i).size(); j++) {
                String biaozhun = lists.get(i).get(j);
                for (int k = i + 1; k < lists.size(); k++) {
                    for (int l = 0; l < lists.get(k).size(); l++) {
                        if (biaozhun.equals(lists.get(k).get(l))) {
                            hashSet.add(biaozhun);
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        EntropyIm.helper(0, 26);
        System.out.println("----");
//

        /*
        EntropyIm.helper(0, 6);
        System.out.println("----");
        EntropyIm.helper(6, 10);
        System.out.println("----");
        EntropyIm.helper(10, 16);
        System.out.println("----");
        EntropyIm.helper(16, 22);
        System.out.println("----");
        EntropyIm.helper(22, 26);
        System.out.println("----");
//        */
//        List<List<Double>> matrix = new ArrayList<>();
//        Scanner sc;
//        try (BufferedReader br = new BufferedReader(new FileReader("0209.txt"))) {
//            String tmp = null;
//            for (int j = 0; j < 26; j++) {
//                tmp = br.readLine();
//                sc = new Scanner(tmp);
//                if (j >= 22) {
//                    List<Double> one = new ArrayList<>();
//                    for (int i = 0; i < 9; i++) {
//                        one.add(sc.nextDouble());
//                    }
//                    matrix.add(one);
//                }
//            }
////            while ((tmp = br.readLine()) != null) {
////                sc = new Scanner(tmp);
////                List<Double> one = new ArrayList<>();
////                for (int i = 0; i < 9; i++) {
////                    one.add(sc.nextDouble());
////                }
////                matrix.add(one);
////            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        System.out.println("sss");
//
//        List<Double> ret = EntropyIm.getWeight(matrix);
//        System.out.println(ret);
//        for (int i = 0; i < ret.size(); i++) {
//            System.out.println(String.format("%.4f", ret.get(i)));
//        }


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

        /*
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
        */

        //第一阶段供能可靠性
        /*
        List<Double> A = new ArrayList<>();
        A.add(71.0);
        A.add(40.0);
        A.add(33.0);
        List<Double> B = new ArrayList<>();
        B.add(69.0);
        B.add(38.0);
        B.add(32.0);
        List<Double> C = new ArrayList<>();
        C.add(51.0);
        C.add(31.0);
        C.add(25.0);
        List<Double> D = new ArrayList<>();
        D.add(66.0);
        D.add(45.0);
        D.add(43.0);
        List<Double> E = new ArrayList<>();
        E.add(69.0);
        E.add(46.0);
        E.add(24.0);
        List<Double> F = new ArrayList<>();
        F.add(40.0);
        F.add(32.0);
        F.add(30.0);
        List<Double> G = new ArrayList<>();
        G.add(70.0);
        G.add(33.0);
        G.add(22.0);
        matrix.add(A);
        matrix.add(B);
        matrix.add(C);
        matrix.add(D);
        matrix.add(E);
        matrix.add(F);
        matrix.add(G);

*/

        //第一阶经济效益性
        /*
        List<Double> A = new ArrayList<>();
        A.add(65.0);
        A.add(43.0);
        A.add(34.0);
        List<Double> B = new ArrayList<>();
        B.add(67.0);
        B.add(42.0);
        B.add(37.0);
        List<Double> C = new ArrayList<>();
        C.add(64.0);
        C.add(38.0);
        C.add(35.0);
        List<Double> D = new ArrayList<>();
        D.add(63.0);
        D.add(44.0);
        D.add(23.0);
        matrix.add(A);
        matrix.add(B);
        matrix.add(C);
        matrix.add(D);
        */

        //第一阶段资源节约性
        /*
        List<Double> A = new ArrayList<>();
        A.add(38.0);
        A.add(57.0);
        A.add(25.0);
        List<Double> B = new ArrayList<>();
        B.add(47.0);
        B.add(67.0);
        B.add(41.0);
        matrix.add(A);
        matrix.add(B);
        */

        //第一阶段环境友好性
        /*
        List<Double> A = new ArrayList<>();
        A.add(58.0);
        A.add(80.0);
        A.add(51.0);
        List<Double> B = new ArrayList<>();
        B.add(54.0);
        B.add(82.0);
        B.add(48.0);
        matrix.add(A);
        matrix.add(B);
*/
        //第一阶段社会效益性
        /*
        List<Double> A = new ArrayList<>();
        A.add(37.0);
        A.add(51.0);
        A.add(78.0);
        List<Double> B = new ArrayList<>();
        B.add(27.0);
        B.add(34.0);
        B.add(74.0);
        List<Double> C = new ArrayList<>();
        C.add(34.0);
        C.add(43.0);
        C.add(36.0);
        matrix.add(A);
        matrix.add(B);
        matrix.add(C);
        */

        //第一阶段智能互动性
        /*
        List<Double> A = new ArrayList<>();
        A.add(35.0);
        A.add(42.0);
        A.add(62.0);
        List<Double> B = new ArrayList<>();
        B.add(47.0);
        B.add(56.0);
        B.add(72.0);
        List<Double> C = new ArrayList<>();
        C.add(27.0);
        C.add(38.0);
        C.add(49.0);
        List<Double> D = new ArrayList<>();
        D.add(34.0);
        D.add(40.0);
        D.add(51.0);
        matrix.add(A);
        matrix.add(B);
        matrix.add(C);
        matrix.add(D);
        */


        /*
        List<Double> H = new ArrayList<>();
        H.add();
        H.add();
        H.add();
        List<Double> I = new ArrayList<>();
        I.add();
        I.add();
        I.add();
        List<Double> J = new ArrayList<>();
        J.add();
        J.add();
        J.add();
        List<Double> K = new ArrayList<>();
        K.add();
        K.add();
        K.add();
        List<Double> L = new ArrayList<>();
        L.add();
        L.add();
        L.add();
        List<Double> M = new ArrayList<>();
        M.add();
        M.add();
        M.add();
        List<Double> N = new ArrayList<>();
        N.add();
        N.add();
        N.add();
        List<Double> O = new ArrayList<>();
        O.add();
        O.add();
        O.add();
        List<Double> P = new ArrayList<>();
        P.add();
        P.add();
        P.add();
        List<Double> Q = new ArrayList<>();
        Q.add();
        Q.add();
        Q.add();
        List<Double> R = new ArrayList<>();
        R.add();
        R.add();
        R.add();
        List<Double> S = new ArrayList<>();
        S.add();
        S.add();
        S.add();
        List<Double> T = new ArrayList<>();
        T.add();
        T.add();
        T.add();
        List<Double> U = new ArrayList<>();
        U.add();
        U.add();
        U.add();
        List<Double> V = new ArrayList<>();
        V.add();
        V.add();
        V.add();

*/

//        System.out.println(EntropyImproved.getWeight(matrix));

    }

}
