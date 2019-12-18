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

    public List<Alternative> scoreAndsort(List<Alternative> al, int indexCnt) {
        double x[] = new double[indexCnt], y[] = new double[indexCnt];
        for (int i = 0; i < indexCnt; i++) {
            x[i] = 0;
            y[i] = 0;
        }
        for (Alternative a : al) {
            for (int i = 0; i < indexCnt; i++) {
                x[i] += Math.pow(a.attribute[i], 2);
            }
        }
        for (int i = 0; i < indexCnt; i++) {
            x[i] = Math.sqrt(x[i]);
        }
        for (Alternative a : al) {
            for (int i = 0; i < indexCnt; i++) {
                a.attribute[i] = a.attribute[i] / x[i];
            }
            a.weighted();
        }
        //计算正负理想解
        for (int i = 0; i < indexCnt; i++) {
            for (Alternative a : al) {
                a.comp = i;
            }
            x[i] = Collections.max(al).attribute[i];
            y[i] = Collections.min(al).attribute[i];
        }

        Alternative best = new Alternative("", x);
        Alternative worse = new Alternative("", y);
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
            System.out.println(a.name + " " + a.c);
        }
        return al;
    }

    public static void main(String[] args) {
        List<Alternative> al = new LinkedList<Alternative>();
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
        List<Double> weightsList = Arrays.asList(weights);
        Topsis topsis = new Topsis(22, weightsList);
        //从文件录入数据
        /*
        try (BufferedReader br = new BufferedReader(new FileReader("1.txt"))) {
            String tmp = null;
            while ((tmp = br.readLine()) != null) {
                s = new Scanner(tmp);
                double[] data = new double[D];
                String num = s.next();
                for (int i = 0; i < D; i++) {
                    data[i] = s.nextDouble();
                }
                al.add(new Alternative(num, data));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        */

        double[] A1 = new double[topsis.indexCnt];
        double[] A2 = new double[topsis.indexCnt];
        double[] A3 = new double[topsis.indexCnt];
        double[] B1 = new double[topsis.indexCnt];
        double[] B2 = new double[topsis.indexCnt];
        double[] B3 = new double[topsis.indexCnt];
        double[] C1 = new double[topsis.indexCnt];
        double[] C2 = new double[topsis.indexCnt];
        double[] C3 = new double[topsis.indexCnt];
        try (BufferedReader br = new BufferedReader(new FileReader("originData.txt"))) {
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

        topsis.scoreAndsort(al, topsis.indexCnt);

    }
}