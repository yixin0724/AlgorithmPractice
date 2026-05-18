package com.greedy;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Prim 最小生成树（Prim's Minimum Spanning Tree）
 *
 * ═══════════════════════════════════════════════════════════
 * 人为思考过程：
 * ═══════════════════════════════════════════════════════════
 * 1. 问题本质：给定一个加权连通无向图，求一棵生成树使得总权值最小。
 *    相比 Kruskal 从"边"的角度贪心，Prim 从"顶点集合"的角度贪心。
 * 2. 直觉告诉我们：从一个起点出发，维护一个"已访问顶点集合"，每次从
 *    集合往外扩展一条最短的边，把边的另一端顶点加入集合。
 * 3. 为什么这样是正确的？MST 的 cut property：任意连通分量（已访问集合
 *    可看作一个连通分量）的最小权值出边一定属于 MST。
 * 4. 重复这个过程，每轮把一个新顶点加入已访问集合，直到所有顶点都加入。
 *    每次选择的边必然是最小生成树的边。
 * 5. 当前实现比较朴素：每轮都遍历已访问集合中的每个顶点到未访问顶点的
 *    所有边，选出权值最小的一条。可通过优先队列优化。
 *
 * ═══════════════════════════════════════════════════════════
 * 具体措施（算法步骤）：
 * ═══════════════════════════════════════════════════════════
 * 1. 输入：加权连通图的邻接矩阵 G，-1 表示无边。
 * 2. 初始化：选择顶点 0 作为起点，将其标为已访问（vertix[0] = -2），
 *    加入已访问集合 listV。
 * 3. 当已访问顶点数 < 总顶点数时，重复：
 *    a. 遍历已访问集合中的每个顶点 v1，检查 v1 到每个未访问顶点 j 的边 G[v1][j]。
 *    b. 记录其中权值最小的一条边及其对应的顶点 minV 和 minI。
 *    c. 将 minV 标为已访问，加入 listV。
 *    d. 将边 (minI, minV) 在结果矩阵中标记为 0（表示属于 MST 的边）。
 * 4. 输出时累加最短路径总长度和顶点访问顺序。
 *
 * ═══════════════════════════════════════════════════════════
 * 复杂度分析：
 * ═══════════════════════════════════════════════════════════
 * 时间复杂度：O(V^3)
 *   - 外层循环执行 V-1 次。
 *   - 每轮遍历已访问集合（最多 V 个）对每个未访问顶点（最多 V 个），
 *     即 O(V^2) 每轮，总计 O(V^3)。
 *   - 若使用优先队列优化，可降至 O((V+E) log V) 或 O(V^2)（稠密图）。
 * 空间复杂度：O(V^2)
 *   - 邻接矩阵 G 和结果矩阵 result 各占 O(V^2)。
 *   - 顶点标记数组 vertix：O(V)。
 *   - 已访问集合 listV：O(V)。
 */
public class PrimMinimumSpanningTree {
    /*
     * 参数G：给定的图，其顶点分别为0~G.length-1，相应权值为具体元素的值
     * 函数功能：返回构造生成的最小生成树，以二维数组形式表示，其中元素为0表示最小生成树的边
     */
    public void getMinTree(int[][] G) {
        int count = 0;
        int[][] result = G;
        int[] vertix = new int[G.length];   //记录顶点是否被访问，如果已被访问，则置相应顶点的元素值为-2
        for (int i = 0; i < G.length; i++)
            vertix[i] = i;
        ArrayList<Integer> listV = new ArrayList<Integer>(); //保存已经遍历过的顶点
        listV.add(0);      //初始随意选择一个顶点作为起始点，此处选择顶点0
        vertix[0] = -2;    //表示顶点0被访问
        // 主循环：每次扩展一个顶点加入 MST
        while (listV.size() < G.length) {  //当已被遍历的顶点数等于给定顶点数时，退出循环
            int minDistance = Integer.MAX_VALUE;    //用于寻找最小权值，初始化为int最大值，相当于无穷大的意思
            int minV = -1;   //用于存放未被遍历的顶点中与已被遍历顶点有最小权值的顶点
            int minI = -1;   //用于存放已被遍历的顶点与未被遍历顶点有最小权值的顶点  ；即G[minI][minV]在剩余的权值中最小
            // 遍历所有"已访问 -> 未访问"的边，选出权值最小的
            for (int i = 0; i < listV.size(); i++) {   //i 表示已被访问的顶点
                int v1 = listV.get(i);
                for (int j = 0; j < G.length; j++) {
                    if (vertix[j] != -2) {    //满足此条件的表示，顶点j未被访问
                        if (G[v1][j] != -1 && G[v1][j] < minDistance) {//G[v1][j]值为-1表示v1和j是非相邻顶点
                            minDistance = G[v1][j];
                            minV = j;
                            minI = v1;
                        }
                    }
                }
            }
            count = count + minDistance;             //把每次选出的最小路径保存
            vertix[minV] = -2;                      //表示minv这个点访问过了
            listV.add(minV);                        //加入到已访问的集合
            // 在结果矩阵中标记 MST 的边
            result[minI][minV] = 0;                 //表示最小生成树的边
            result[minV][minI] = 0;
        }
        System.out.print("使用Prim算法，对于给定图中的顶点访问顺序为：");
        System.out.println(listV);
        System.out.println("最短路径为：" + count);
//        System.out.println("使用Prim算法，构造的最小生成树的二维数组表示如下（PS：元素为0表示生成树的边）：");
//        for (int i = 0; i < result.length; i++) {
//            for (int j = 0; j < result[0].length; j++) {
//                System.out.print(result[i][j] + "\t");
//            }
//            System.out.println();
//        }
    }

    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(System.in);
            PrimMinimumSpanningTree test = new PrimMinimumSpanningTree();
            //用一个二阶矩阵来表示无向连通网
            System.out.println("请输入顶点的个数：");
            int n = sc.nextInt();
            int[][] G = new int[n][n];
            //初始化矩阵，-1 表示无边
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    G[i][j] = -1;
                }
            }
            System.out.println("请输入边的个数：");
            int edge = sc.nextInt();
            System.out.println("请依次输入各个顶点和权值：");
            // 读入边的信息，填充邻接矩阵
            for (int i = 0; i < edge; i++) {
                int start = sc.nextInt();
                int end = sc.nextInt();
                int weight = sc.nextInt();
                G[start][end] = weight;
                G[end][start] = weight;
            }
            test.getMinTree(G);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("条件不足，无法找到最小的。");
        }
    }
}


/**
 * 0 1 34
 * 0 5 19
 * 0 2 46
 * 1 4 12
 * 4 5 26
 * 4 3 38
 * 5 2 25
 * 5 3 25
 * 2 3 17
 */

//import java.util.Arrays;
//import java.util.Scanner;
//
//public class PrimMinimumSpanningTree {
//    public static void main(String[] args) {
//        Scanner sc = new Scanner(System.in);
//        //用一个二阶矩阵来表示无向连通网
//        System.out.println("请输入顶点的个数：");
//        int n = sc.nextInt();
//        int[][] arr = new int[n][n];
//        //初始化矩阵
//        for (int i = 0; i < n; i++) {
//            for (int j = 0; j < n; j++) {
//                arr[i][j] = 999;
//            }
//        }
//        System.out.println("请输入边的个数：");
//        int edge = sc.nextInt();
//        System.out.println("请依次输入各个顶点和权值：");
//        for (int i = 0; i < edge; i++) {
//            int start = sc.nextInt();
//            int end = sc.nextInt();
//            int weight = sc.nextInt();
//            arr[start][end] = weight;
//            arr[end][start] = weight;
//        }
//        System.out.println(Prim_m(arr, n));
//    }
//
//    public static int Prim_m(int[][] arr, int n) {
//        //创建一个lowcost数组进行保存每一个顶点到生成树中所有顶点的最短权值
//        int[] lowcost = new int[n];
//        //创建一个adjvex数组保存最短边的顶点
//        int[] adjvex = new int[n];
//        //初始化lowcost
//        //接下来进行为lowcost数组贺adjvex数组赋值
//        for (int i = 0; i < n; i++) {
//            int min = 999;
//            for (int j = 0; j < n; j++) {
//                if (arr[i][j] < min ) {
//                    min = arr[i][j];
//                    adjvex[i] = j;
//                    arr[j][i] = 999;
//                }
//            }
//            lowcost[i] = min;
//        }
////        System.out.println(Arrays.toString(lowcost));
////        System.out.println(Arrays.toString(adjvex));
//        return 1;
//    }
//
//}
