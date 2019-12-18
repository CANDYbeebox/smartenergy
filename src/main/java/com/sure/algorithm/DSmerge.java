package com.sure.algorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SURE on ${DATA}.
 */
public class DSmerge {

    //评判等级
    public static final int judgeLevel = 3;

    //每个指标多个专家的结果融合
    //最后一行代表不确定度
    public static double[] Merge(double[][] matrix) {
        //1.校验初始矩阵
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            throw new RuntimeException("矩阵为空");
        }
        //2.全部组合筛选后后计算归一化系数 1-K
        int row = matrix.length;
        //组合
        List<List<Integer>> lists = new ArrayList<>();
        List<Integer> one = new ArrayList<>();
        fullCombination(row - 1, lists, one, matrix[0].length);
        //筛选出交集为null的组合，即list的值都一样
        double k = 0;
        for (List<Integer> list : lists) {
            //判断list中的数字是否都相等，都相等需要排除
            int curColumn = 0;
            int standard = list.get(0);
            double product = 1;
            boolean equal = true;
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i) != standard) {
                    equal = false;
                }
                product *= matrix[list.get(i)][curColumn++];
            }
            k = k + (equal ? 0 : product);
        }

        //3.计算各焦元的组合m
        double[] combinationMass = new double[judgeLevel + 1];
        for (int i = 0; i < judgeLevel; i++) {
            lists = new ArrayList<>();
            one = new ArrayList<>();
            fullCombination(2, lists, one, matrix[0].length);
            double numerator = 0;
            lists.remove(lists.size() - 1);
            for(List<Integer> list : lists) {
                double temp = 1;
                int curColumn = 0;
                for (int idx :list) {
                    int curRow = (idx == 0 ? i : row - 1);
                    temp *= matrix[curRow][curColumn++];
                }
                numerator += temp;
            }
            combinationMass[i] = numerator / (1 - k);
        }
        //4.计算融合之后的不确定度
        combinationMass[judgeLevel] = 1;
        for (int i = 0; i < judgeLevel; i++) {
            combinationMass[judgeLevel] -= combinationMass[i];
        }

        return combinationMass;

    }


    public static void fullCombination(int n, List<List<Integer>> lists, List<Integer> one, int number) {
        if (one.size() == number) {
            lists.add(new ArrayList<>(one));
            return;
        }
        for (int i = 0; i < n; i++) {
            one.add(i);
            fullCombination(n, lists, one, number);
            one.remove(one.size() - 1);
        }
    }

    public static void main(String[] args) {
        List<List<Integer>> lists = new ArrayList<>();
        List<Integer> one = new ArrayList<>();
        double[][] matrix = {{0.98, 0},{0.01, 0.01},{0, 0.98},{0.01, 0.01}};
        DSmerge.Merge(matrix);
//        DSmerge.fullCombination(3, lists, one);
        System.out.println("ss");
    }

}
