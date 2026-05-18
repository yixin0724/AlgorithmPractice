package com.dp;
import java.util.Scanner;

/**
 * LeetCode Hot 100 - 120. 三角形最小路径和 (Triangle)
 *
 * ═══════════════════════════════════════════════════════════
 * 人为思考过程：
 * ═══════════════════════════════════════════════════════════
 * 1. 数塔（数字三角形）从上到下，每个位置 (i,j) 只能走到下一层的 (i+1,j) 或 (i+1,j+1)。
 *    目标是找到从顶层到底层的路径，使得路径上数字之和最小。
 * 2. 与求最大路径和类似，如果从头往下 DFS，路径数量呈指数级增长，n 稍大就不可行。
 * 3. 反过来从底层向上推导：如果已经知道第 i+1 层每个位置到底层的最小路径和，那么在第 i 层
 *    的 (i,j) 位置，只需要比较 (i+1,j) 和 (i+1,j+1) 两个位置的最小值，加上当前位置的数值
 *    即可。
 * 4. 这个自底向上的递推过程天然满足动态规划的"最优子结构"：无论之前如何走到 (i,j)，从 (i,j)
 *    到底层的最优路径只取决于下面两层的状态，与之前路径无关。
 * 5. 用 minAdd[i][j] 表示从 (i,j) 出发到达底层的最小路径和。底层初始化后，逐层向上用
 *    minAdd[i][j] = a[i][j] + min(minAdd[i+1][j], minAdd[i+1][j+1]) 计算。
 * 6. 最后 minAdd[0][0] 就是答案。如果要输出具体路径，用 path[i][j] 记录每步选择了哪个方向。
 *
 * ═══════════════════════════════════════════════════════════
 * 具体措施（算法步骤）：
 * ═══════════════════════════════════════════════════════════
 * 1. 读入数塔层数 n 和数塔数据 a[n][n]。
 * 2. 用 minAdd[n-1][*] 初始化为 a[n-1][*]（底层到达自身的最短路）。
 * 3. 从第 n-2 层到第 0 层，对每个 (i,j)：
 *    a. 比较 minAdd[i+1][j] 和 minAdd[i+1][j+1]，取较小值。
 *    b. minAdd[i][j] = a[i][j] + 较小值。
 *    c. 记录 path[i][j] 指向选择的方向。
 * 4. 输出 minAdd[0][0] 作为最小路径和，并沿 path 逐层输出具体路径。
 *
 * ═══════════════════════════════════════════════════════════
 * 复杂度分析：
 * ═══════════════════════════════════════════════════════════
 * 时间复杂度：O(n^2)
 *   - 遍历下三角矩阵的所有位置，共 n(n+1)/2 次操作。
 * 空间复杂度：O(n^2)
 *   - minAdd 表和 path 表各占用 n*n 空间。
 */

public class NumberTriangleMinPathDp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入数塔最后一层的个数：");
        int n = sc.nextInt();
        int a[][] = new int[n][n];
        System.out.println("请从上往下依次赋值：");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= i; j++) {
                a[i][j] = sc.nextInt();
            }
        }
        System.out.println("所得最小路径之和为：" + countTower(a, n));
    }

    public static int countTower(int a[][], int n) {
        // minAdd[i][j] 表示从 (i,j) 出发到达底层的最小路径和
        int minAdd[][] = new int[n][n];
        // path[i][j] 记录在 (i,j) 位置时选择了下一层的哪一列（用于输出路径）
        int path[][] = new int[n][n];

        // 初始化：最后一层每个位置的最短路径就是自身的值
        for (int i = 0; i < n; i++) {
            minAdd[n - 1][i] = a[n - 1][i];
        }

        // 自底向上逐层计算最小路径和
        for (int i = n - 2; i >= 0; i--) {
            for (int j = 0; j <= i; j++) {
                // 比较下一层的两个可达位置，选择较小的路径
                if (minAdd[i + 1][j] < minAdd[i + 1][j + 1]) {
                    minAdd[i][j] = a[i][j] + minAdd[i + 1][j];
                    path[i][j] = j;                 // 选择左下方
                } else {
                    minAdd[i][j] = a[i][j] + minAdd[i + 1][j + 1];
                    path[i][j] = j + 1;             // 选择右下方
                }
            }
        }

        // 输出路径：从顶层开始，沿着 path 逐层追踪
        System.out.printf("路径为：" + a[0][0]);
        int j = path[0][0];                         // 顶层决策指向的下一层列号
        for (int i = 1; i < n; i++) {
            System.out.printf("-->" + a[i][j]);
            j = path[i][j];                         // 本层决策指向的下一层列号
        }
        System.out.println("");
        return minAdd[0][0];                        // 顶层的最优值即为全局最小路径和
    }
}

