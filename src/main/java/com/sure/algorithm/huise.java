package com.sure.algorithm;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 灰色关联度计算
 * Created by SURE on ${DATA}.
 */
public class huise {


    public static void biaozhunhua(double[][] data){
        int m = data.length;
        int n = data[0].length;
        for (int i = m - 1; i >= 0; i--) {
            for (int j = 0; j < n; j++) {
                data[i][j] = data[i][j] / data[0][j];
            }
        }
    }

    public static void calculate(double[][] data, int cankao) {
        int m = data.length;
        int n = data[0].length;
        double[][] sanjiaxoing = new double[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                sanjiaxoing[i][j] = Math.abs(data[i][cankao] - data[i][j]);
            }
        }
        double max = 0.0;
        double min = 1.000;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (j != cankao) {
                    max = Math.max(sanjiaxoing[i][j], max);
                    min = Math.min(sanjiaxoing[i][j], min);
                }
            }
        }

        double[][] beita = new double[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (j != cankao) {
                    beita[i][j] = (min  + 0.5 * max) / (sanjiaxoing[i][j] + 0.5 * max);
                }
            }
        }

        for (int i = 0; i < beita.length; i++) {
            for (int j = 0; j < beita[0].length; j++) {
                System.out.println(String.format("%.4f", beita[i][j]));
            }
            System.out.println("----");
        }


        double[] guanliandu = new double[n];
        for (int j = 0; j < n; j++) {
            for (int i = 0; i < m; i++) {
                guanliandu[j] += beita[i][j];
            }
            guanliandu[j] = guanliandu[j] / m;
            guanliandu[cankao] = 1;
//            System.out.println(String.format("%.4f", guanliandu[j]));


        }

        System.out.println("---");
        double sum = 0.0;
        for (int i = 0; i < 1; i++) {
            sum+= guanliandu[i];
        }
        System.out.println(String.format("%.4f", sum / n));
    }

    public static void helper() {
        Scanner sc;
        List<List<Double>> matrix = new ArrayList<>();
        List<List<Double>> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("huise2.txt"))) {
            String tmp = null;
            for (int i = 0; i < 5; i++) {
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

        double[][] ddd = new double[5][9];
        for (int i = 0; i < 5; i++) {
            List<Double> one = data.get(i);
            for (int j = 0; j < 9; j++) {
                ddd[i][j] = one.get(j);
            }
            System.out.println("---");
        }
        double[][] zhuanhuan = new double[9][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 9; j++) {
                zhuanhuan[j][i] = ddd[i][j];
            }
        }

        for (int i = 0; i < 1; i++) {
            calculate(zhuanhuan,i);
        }

    }

    public static void main(String[] args) {

        double[][] data = {
                {1,1,1,1,1,1,1,1},
        {0.853,0.595,1.201,0.835,0.805,1.205,0.985,1.305},
                {1.395,2.093,1.402,1.803,1.592,1.115,1.103,1.034},
                {1.51,1.991,1.211,1.201,1.525,1.395,0.812,1.401},
        };
//        huise.calculate(data,0);
        huise.helper();
    }



}
