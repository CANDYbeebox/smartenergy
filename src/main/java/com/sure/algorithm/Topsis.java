package com.sure.algorithm;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by SURE on ${DATA}.
 */
public class Topsis {

    private static Scanner s;

    public int indexCnt;//指标数

    public List<Double> weights;

//    public double[] weights;//权重

    public Topsis(int indexCnt, List<Double> weights) {
        this.indexCnt = indexCnt;
        this.weights = weights;
    }

    class Alternative implements Comparable<Alternative> {
        int comp;
        String name;
        double[] attribute = new double[indexCnt];    //指标数值
        double bestdis, worsedis, c;    //与正、负理想解的欧式距离,c为贴进度

        public Alternative(String name, double[] d) {
            this.name = name;
            for (int i = 0; i < indexCnt; i++) {
                this.attribute[i] = d[i];
            }
        }

        public void weighted() {        //赋权
            for (int i = 0; i < indexCnt; i++) {
                this.attribute[i] *= weights.get(i);
            }
        }

        @Override
        public int compareTo(Alternative a) {
            return (int) (this.attribute[comp] - a.attribute[comp]);
        }
    }

    public List<Alternative> score(List<Alternative> al, int indexCnt) {
        double x[] = new double[indexCnt], y[] = new double[indexCnt];
        for (int i = 0; i < indexCnt; i++) {
            x[i] = 0;
            y[i] = Double.MAX_VALUE;
        }
        for (Alternative a : al) {
            for (int i = 0; i < indexCnt; i++) {
//                x[i] += Math.pow(a.attribute[i], 2);
                x[i] = Math.max(a.attribute[i], x[i]);
                y[i] = Math.min(a.attribute[i], y[i]);
            }
        }
//        for (int i = 0; i < indexCnt; i++) {
//            x[i] = Math.sqrt(x[i]);
//        }
        for (Alternative a : al) {
            for (int i = 0; i < indexCnt; i++) {
                a.attribute[i] = (a.attribute[i] - y[i]) / (x[i] - y[i]);
            }
            a.weighted();
        }
        //计算正负理想解
        double[] worsts = new double[indexCnt];
        for (int i = 0; i < worsts.length; i++) {
            worsts[i] = Double.MAX_VALUE;
        }
        double[] bests = new double[indexCnt];
        for (int i = 0; i < indexCnt; i++) {
            for (int j = 0; j < al.size(); j++) {
                worsts[i] = Math.min(worsts[i], al.get(j).attribute[i]);
                bests[i] = Math.max(bests[i], al.get(j).attribute[i]);
            }
        }
        Alternative best = new Alternative("", bests);
        Alternative worse = new Alternative("", worsts);
        //计算正负理想解的距离、贴进度
        ListIterator<Alternative> it = al.listIterator();
        while (it.hasNext()) {
            Alternative t = it.next();
            t.bestdis = 0;
            for (int j = 0; j < indexCnt; j++) {
                t.bestdis += Math.pow(t.attribute[j] - best.attribute[j], 2);
            }
            t.bestdis = Math.sqrt(t.bestdis);
        }
        it = al.listIterator();
        while (it.hasNext()) {
            Alternative t = it.next();
            t.worsedis = 0;
            for (int j = 0; j < indexCnt; j++) {
                t.worsedis += Math.pow(t.attribute[j] - worse.attribute[j], 2);
            }
            t.worsedis = Math.sqrt(t.worsedis);
            t.c = t.worsedis / (t.worsedis + t.bestdis);
        }
        //按照贴进度排序
//        Collections.sort(al, new Comparator<Alternative>() {
//            @Override
//            public int compare(Alternative a1, Alternative a2) {
//                return a2.c > a1.c ? 1 : -1;
//            }
//        });
        for (Alternative a : al) {
//            System.out.println(a.name + " " + a.c);
            System.out.println(String.format("%.4f", a.c));

        }
        System.out.println("-----");

        return al;
    }

    public void helper(int start, int end, Double[] weights) {
        List<Alternative> al = new LinkedList<Alternative>();

        List<Double> weightsList = Arrays.asList(weights);
        Topsis topsis = new Topsis(end - start, weightsList);
        double[] A1 = new double[topsis.indexCnt];
        double[] A2 = new double[topsis.indexCnt];
        double[] A3 = new double[topsis.indexCnt];
        double[] B1 = new double[topsis.indexCnt];
        double[] B2 = new double[topsis.indexCnt];
        double[] B3 = new double[topsis.indexCnt];
        double[] C1 = new double[topsis.indexCnt];
        double[] C2 = new double[topsis.indexCnt];
        double[] C3 = new double[topsis.indexCnt];
        try (BufferedReader br = new BufferedReader(new FileReader("0209.txt"))) {
            String tmp = null;
            int idx = 0;
            for (int j = 0; j < end; j++) {
                tmp = br.readLine();
                s = new Scanner(tmp);
                if (j >= start) {
                    s = new Scanner(tmp);
                    A1[idx] = s.nextDouble();
                    A2[idx] = s.nextDouble();
                    A3[idx] = s.nextDouble();
                    B1[idx] = s.nextDouble();
                    B2[idx] = s.nextDouble();
                    B3[idx] = s.nextDouble();
                    C1[idx] = s.nextDouble();
                    C2[idx] = s.nextDouble();
                    C3[idx] = s.nextDouble();
                    idx++;
                }
            }
            al.add(topsis.new Alternative("A1", A1));
            al.add(topsis.new Alternative("A2", A2));
            al.add(topsis.new Alternative("A3", A3));
            al.add(topsis.new Alternative("B1", B1));
            al.add(topsis.new Alternative("B2", B2));
            al.add(topsis.new Alternative("B3", B3));
            al.add(topsis.new Alternative("C1", C1));
            al.add(topsis.new Alternative("C2", C2));
            al.add(topsis.new Alternative("C3", C3));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        topsis.score(al, topsis.indexCnt);

    }

    public static void main(String[] args) {

        Double[] weights1 = {0.1383,
                0.1377,
                0.2383,
                0.1402,
                0.2046,
                0.1409,


        };
        Double[] weights2 = {0.2569,
                0.2413,
                0.2186,
                0.2832,


        };
        Double[] weights3 = {0.1541,
                0.1594,
                0.1972,
                0.1671,
                0.1705,
                0.1517,


        };
        Double[] weights4 = {0.1618,
                0.1336,
                0.1937,
                0.1398,
                0.1446,
                0.2264,
        };
        Double[] weights5 = {0.2350,
                0.2931,
                0.2246,
                0.2473,

        };
        Topsis topsis = new Topsis(0, new ArrayList<>());
        topsis.helper(0, 6, weights1);
        topsis.helper(6, 10, weights2);
        topsis.helper(10, 16, weights3);
        topsis.helper(16, 22, weights4);
        topsis.helper(22, 26, weights5);



        /*
        List<Alternative> al = new LinkedList<Alternative>();

        Double[] weights = {0.2255,
                0.3150,
                0.2177,
                0.2418,
        };    //各指标权重
        List<Double> weightsList = Arrays.asList(weights);
        Topsis topsis = new Topsis(4, weightsList);
        double[] A1 = new double[topsis.indexCnt];
        double[] A2 = new double[topsis.indexCnt];
        double[] A3 = new double[topsis.indexCnt];
        double[] B1 = new double[topsis.indexCnt];
        double[] B2 = new double[topsis.indexCnt];
        double[] B3 = new double[topsis.indexCnt];
        double[] C1 = new double[topsis.indexCnt];
        double[] C2 = new double[topsis.indexCnt];
        double[] C3 = new double[topsis.indexCnt];
        try (BufferedReader br = new BufferedReader(new FileReader("0209.txt"))) {
            String tmp = null;
            int idx = 0;
            for (int j = 0; j < 26; j++) {
                tmp = br.readLine();
                s = new Scanner(tmp);
                if (j >= 22) {
                        s = new Scanner(tmp);
                        A1[idx] = s.nextDouble();
                        A2[idx] = s.nextDouble();
                        A3[idx] = s.nextDouble();
                        B1[idx] = s.nextDouble();
                        B2[idx] = s.nextDouble();
                        B3[idx] = s.nextDouble();
                        C1[idx] = s.nextDouble();
                        C2[idx] = s.nextDouble();
                        C3[idx] = s.nextDouble();
                        idx++;
                }
            }
            al.add(topsis.new Alternative("A1", A1));
            al.add(topsis.new Alternative("A2", A2));
            al.add(topsis.new Alternative("A3", A3));
            al.add(topsis.new Alternative("B1", B1));
            al.add(topsis.new Alternative("B2", B2));
            al.add(topsis.new Alternative("B3", B3));
            al.add(topsis.new Alternative("C1", C1));
            al.add(topsis.new Alternative("C2", C2));
            al.add(topsis.new Alternative("C3", C3));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        topsis.score(al, topsis.indexCnt);

*/
    }
}