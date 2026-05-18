package com.simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * LeetCode Hot 100 - 54. Spiral Matrix（螺旋矩阵）
 *
 * ═══════════════════════════════════════════════════════════
 * 人为思考过程：
 * ═══════════════════════════════════════════════════════════
 * 1. 题目要求按顺时针螺旋顺序输出矩阵的所有元素。
 *    例如对于一个 3x3 矩阵，顺序是：上边（左到右）、右边（上到下）、
 *    下边（右到左）、左边（下到上），然后向内收缩继续。
 * 2. 想象自己正站在一个矩形的边界上，顺时针漫步：
 *    先沿着上边走完一整行，然后顺着右边往下走一列，
 *    再沿着下边反向走一行，最后沿着左边往上走一列回到起点附近，
 *    但起点已经往里缩了一点。
 * 3. 这个"行走"的过程可以用四个边界变量来抽象：
 *    top（上边界）、bottom（下边界）、left（左边界）、right（右边界）。
 * 4. 每次走完一条边后就把对应的边界缩一格：
 *    走完上边后 top++，走完右边后 right--，
 *    走完下边后 bottom--，走完左边后 left++。
 * 5. 注意：走完上边和右边后，下边和左边的遍历需要确保
 *    剩余的行和列还有元素（即 top <= bottom 和 left <= right），
 *    否则对于只有一行或一列的矩阵会重复输出。
 * 6. 这种"边界收缩"的模拟方法非常直观，
 *    把复杂的螺旋路径分解成了四个独立的线段，每条线段按顺序处理。
 *
 * ═══════════════════════════════════════════════════════════
 * 具体措施（算法步骤）：
 * ═══════════════════════════════════════════════════════════
 * 1. 初始化四个边界：top=0, bottom=m-1, left=0, right=n-1。
 * 2. while (top <= bottom && left <= right) 循环：
 *    a. 从左到右遍历上边 (top, left->right)，然后 top++。
 *    b. 从上到下遍历右边 (top->bottom, right)，然后 right--。
 *    c. 若 top <= bottom，从右到左遍历下边 (bottom, right->left)，然后 bottom--。
 *    d. 若 left <= right，从下到上遍历左边 (bottom->top, left)，然后 left++。
 * 3. 返回结果列表。
 *
 * ═══════════════════════════════════════════════════════════
 * 复杂度分析：
 * ═══════════════════════════════════════════════════════════
 * 时间复杂度：O(m * n)
 *   - 每个矩阵元素恰好被访问一次，m 行 n 列共 m*n 个元素。
 * 空间复杂度：O(1)
 *   - 除输出结果列表外，只使用 top、bottom、left、right
 *     四个常数边界变量。
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
        // 定义四个边界
        int top = 0;
        int bottom = matrix.length - 1;
        int left = 0;
        int right = matrix[0].length - 1;

        while (top <= bottom && left <= right) {
            // 1. 从左到右遍历上边
            for (int col = left; col <= right; col++) {
                result.add(matrix[top][col]);
            }
            top++;      // 上边界下移

            // 2. 从上到下遍历右边
            for (int row = top; row <= bottom; row++) {
                result.add(matrix[row][right]);
            }
            right--;    // 右边界左移

            // 3. 从右到左遍历下边（需确保还有剩余行）
            if (top <= bottom) {
                for (int col = right; col >= left; col--) {
                    result.add(matrix[bottom][col]);
                }
                bottom--;   // 下边界上移
            }

            // 4. 从下到上遍历左边（需确保还有剩余列）
            if (left <= right) {
                for (int row = bottom; row >= top; row--) {
                    result.add(matrix[row][left]);
                }
                left++;     // 左边界右移
            }
        }
        return result;
    }
}
