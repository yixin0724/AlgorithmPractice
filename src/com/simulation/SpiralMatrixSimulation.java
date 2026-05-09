package com.simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 问题：按顺时针螺旋顺序输出矩阵元素。
 * 方法：使用边界模拟。
 * 解题思路：维护上、下、左、右四个边界，依次从左到右、从上到下、从右到左、从下到上遍历。
 * 每走完一条边就收缩对应边界，直到所有元素都被输出。
 * 时间复杂度：O(nm)，n 和 m 分别为矩阵行数和列数。
 * 空间复杂度：O(1)，除输出结果外只使用常数个边界变量。
 */
public class SpiralMatrixSimulation {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入矩阵行数和列数：");
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        int[][] matrix = new int[n][m];
        System.out.println("请输入矩阵元素：");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                matrix[i][j] = scanner.nextInt();
            }
        }
        System.out.println("螺旋顺序为：" + spiralOrder(matrix));
    }

    public static List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> result = new ArrayList<Integer>();
        int top = 0;
        int bottom = matrix.length - 1;
        int left = 0;
        int right = matrix[0].length - 1;
        while (top <= bottom && left <= right) {
            for (int col = left; col <= right; col++) {
                result.add(matrix[top][col]);
            }
            top++;
            for (int row = top; row <= bottom; row++) {
                result.add(matrix[row][right]);
            }
            right--;
            if (top <= bottom) {
                for (int col = right; col >= left; col--) {
                    result.add(matrix[bottom][col]);
                }
                bottom--;
            }
            if (left <= right) {
                for (int row = bottom; row >= top; row--) {
                    result.add(matrix[row][left]);
                }
                left++;
            }
        }
        return result;
    }
}
