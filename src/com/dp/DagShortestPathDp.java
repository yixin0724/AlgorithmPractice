package com.dp;

/**
 * 有向无环图最短路径问题（经典动态规划）
 *
 * ═══════════════════════════════════════════════════════════
 * 人为思考过程：
 * ═══════════════════════════════════════════════════════════
 * 1. 首先明确问题：给定一个有向无环图（DAG），求从源点（顶点 0）到终点（顶点 len-1）的最短
 *    路径长度和具体路径。
 * 2. DAG 的一个重要性质是顶点可以按拓扑顺序排列，这意味着当我们计算到某个顶点的最短路径时，
 *    它的所有前驱顶点的最短路径都已经计算完毕。
 * 3. 这正好满足动态规划的"无后效性"：当前顶点的最短路径只取决于前驱顶点，而不会被后续计算
 *    影响。
 * 4. 用邻接矩阵存储图的边权：arr[i][j] 表示从顶点 i 到顶点 j 的权值。不存在的边初始化为一个
 *    很大的数（无穷大）。
 * 5. 设 minpath[j] 表示从源点到顶点 j 的最短距离。初始时 minpath[0] = 0，其他为无穷大。
 * 6. 按拓扑序依次计算每个顶点 j（从 1 到 len-1）：枚举所有可能的前驱 i（i < j），尝试用
 *    minpath[i] + arr[i][j] 更新 minpath[j]。如果这个值更小，就更新最短距离并记录前驱。
 * 7. 最后根据前驱数组 path 回溯输出完整路径。
 *
 * ═══════════════════════════════════════════════════════════
 * 具体措施（算法步骤）：
 * ═══════════════════════════════════════════════════════════
 * 1. 读入顶点数 len 和边数 side。
 * 2. 初始化 len*len 的邻接矩阵 arr，所有元素设为无穷大（9999），表示无边。
 * 3. 依次读入每条边的起点 i、终点 j 和权值 weight，赋值到 arr[i][j]。
 * 4. 调用 shortPath 方法进行计算：
 *    a. 初始化 path 数组（前驱记录，初始 -1）和 minpath 数组（最短距离，初始无穷大），
 *       minpath[0] = 0。
 *    b. 外层循环按拓扑序遍历顶点 j（1 到 len-1）；内层循环枚举所有前驱 i（0 到 j-1），
 *       检查和更新最短距离。
 *    c. 用 list 回溯收集路径：从终点开始，不断沿着 path 数组反向追踪到源点，再倒序输出。
 * 5. 输出最短路径和路径串。
 *
 * ═══════════════════════════════════════════════════════════
 * 复杂度分析：
 * ═══════════════════════════════════════════════════════════
 * 时间复杂度：O(V^2)
 *   - 外层循环遍历 V 个顶点，内层循环最多检查 j 个前驱，总操作数约为 V*(V-1)/2 = O(V^2)。
 * 空间复杂度：O(V^2)
 *   - 邻接矩阵占用 O(V^2) 空间。两个辅助数组 path 和 minpath 各占用 O(V)。
 */

import java.util.ArrayList;
import java.util.Scanner;

public class DagShortestPathDp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入有向图的顶点个数：");
        int len = sc.nextInt();
        System.out.println("请输入边长的个数：");
        int side = sc.nextInt();

        // 初始化邻接矩阵，9999 表示无穷大（无边）
        int arr[][] = new int[len][len];
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                arr[i][j] = 9999;
            }
        }

        // 读入每条边的起点、终点和权值
        System.out.println("请依次输入两个顶点和边的权值：");
        for (int k = 0; k < side; k++) {
            int i = sc.nextInt();
            int j = sc.nextInt();
            int weight = sc.nextInt();
            arr[i][j] = weight;
        }
        System.out.println("所得最短路径和为：" + shortPath(arr, len));
    }

    public static int shortPath(int[][] arr, int len) {
        // list 用于正序输出路径（先反向收集，再倒序遍历）
        ArrayList<String> list = new ArrayList<String>();

        int path[] = new int[len];          // path[j] 记录到达 j 的前驱顶点编号，初始 -1 表示无前驱
        int minpath[] = new int[len];       // minpath[j] 记录从源点 0 到顶点 j 的最短距离
        for (int i = 0; i < len; i++) {
            minpath[i] = 9999;              // 初始化为无穷大
            path[i] = -1;                   // 初始化为无前驱
        }
        minpath[0] = 0;                     // 源点到自身距离为 0

        // 按拓扑序填表，j 代表当前计算的目标顶点（入边方向）
        for (int j = 1; j < len; j++) {
            // 枚举所有可能的前驱顶点 i，尝试更新最短距离
            for (int i = j - 1; i >= 0; i--) {
                if (arr[i][j] + minpath[i] < minpath[j]) {
                    minpath[j] = arr[i][j] + minpath[i];
                    path[j] = i;            // 记录前驱，用于回溯路径
                }
            }
        }

        // 回溯收集路径：从终点 len-1 反向追踪到源点
        int i = len - 1;
        String k = Integer.toString(i);
        list.add(k);
        while (path[i] >= 0) {
            list.add(Integer.toString(path[i]));
            i = path[i];
        }

        // 倒序遍历 list 输出正序路径
        System.out.print("求得最短路径为：");
        for (int l = list.size() - 1; l >= 0; l--) {
            if (l == 0) {
                System.out.print(list.get(l) + "]");
            } else if (l == list.size() - 1) {
                System.out.print("[" + list.get(l) + "-");
            } else {
                System.out.print(list.get(l) + "-");
            }
        }
        System.out.println("");
        return minpath[len - 1];
    }
}

