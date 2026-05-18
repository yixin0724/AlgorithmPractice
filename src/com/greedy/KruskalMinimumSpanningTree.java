package com.greedy;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Kruskal 最小生成树（Kruskal's Minimum Spanning Tree）
 *
 * ═══════════════════════════════════════════════════════════
 * 人为思考过程：
 * ═══════════════════════════════════════════════════════════
 * 1. 问题本质：给定一个加权连通无向图，求一棵生成树使得所有边权值之和最小。
 *    生成树必须包含所有 n 个顶点，且有且仅有 n-1 条边，且不能有环。
 * 2. 直觉告诉我们：要权值最小，那应该优先选最短的边。从全集出发，按边权
 *    从小到大依次考虑每条边 —— 如果加入后不形成环，就把它收进生成树。
 * 3. 怎么判断是否成环？关键观察：如果一条边的两个端点当前已经连通（即在
 *    同一个连通分量中），再加入这条边一定会形成环。反之则安全。
 * 4. 这就引出了并查集（Union-Find）数据结构：它能快速判断两个顶点是否
 *    属于同一连通分量，并且能快速合并两个分量。
 * 5. 算法就是：先对边按权值排序，然后用并查集逐边判断，选够 n-1 条边即止。
 *    正确性由 MST 的 cut property 保证：任意连通分量的最小出边必然属于 MST。
 *
 * ═══════════════════════════════════════════════════════════
 * 具体措施（算法步骤）：
 * ═══════════════════════════════════════════════════════════
 * 1. 将所有边按权值从小到大排序（当前实现使用归并排序）。
 * 2. 初始化并查集：每个顶点自成一个连通分量（id[i] = -1）。
 * 3. 遍历排序后的每条边 (a, b, value)：
 *    a. 用并查集 find 操作查询 a 和 b 的根节点。
 *    b. 若根节点不同（不在同一连通分量），则将该边加入 MST，
 *       并通过 union 操作合并两个连通分量，已选边数 count++。
 *    c. 若根节点相同，则跳过（否则会成环）。
 *    d. 当 count == n-1 时，MST 构造完成，提前结束。
 * 4. 返回结果边集合。
 *
 * ═══════════════════════════════════════════════════════════
 * 复杂度分析：
 * ═══════════════════════════════════════════════════════════
 * 时间复杂度：O(E log E)
 *   - 边排序：归并排序 O(E log E)，E 为边数。
 *   - 并查集操作：find 使用了路径压缩，union 使用了按大小合并，
 *     均摊复杂度接近 O(alpha(V)) 即近乎常数，遍历 E 条边为 O(E)。
 * 空间复杂度：O(V + E)
 *   - 并查集 id 数组：O(V)，V 为顶点数。
 *   - 边数组 A：O(E)。
 *   - 结果边集合 list：O(V)（最多 V-1 条边）。
 */
public class KruskalMinimumSpanningTree {
    //内部类，其对象表示连通图中一条边
    class edge {
        public int a;   // 开始顶点
        public int b;   //结束顶点
        public int value;   //权值

        edge(int a, int b, int value) {
            this.a = a;
            this.b = b;
            this.value = value;
        }
    }

    //使用合并排序，把数组A按照其value值进行从小到大排序
    public void edgeSort(edge[] A) {
        if (A.length > 1) {
            edge[] leftA = getHalfEdge(A, 0);
            edge[] rightA = getHalfEdge(A, 1);
            edgeSort(leftA);
            edgeSort(rightA);
            mergeEdgeArray(A, leftA, rightA);
        }
    }

    //judge = 0返回数组A的左半边元素，否则返回右半边元素
    public edge[] getHalfEdge(edge[] A, int judge) {
        edge[] half;
        if (judge == 0) {
            half = new edge[A.length / 2];
            for (int i = 0; i < A.length / 2; i++)
                half[i] = A[i];
        } else {
            half = new edge[A.length - A.length / 2];
            for (int i = 0; i < A.length - A.length / 2; i++)
                half[i] = A[A.length / 2 + i];
        }
        return half;
    }

    //合并leftA和rightA，并按照从小到大顺序排列
    public void mergeEdgeArray(edge[] A, edge[] leftA, edge[] rightA) {
        int i = 0;
        int j = 0;
        int len = 0;
        while (i < leftA.length && j < rightA.length) {
            if (leftA[i].value < rightA[j].value) {
                A[len++] = leftA[i++];
            } else {
                A[len++] = rightA[j++];
            }
        }
        while (i < leftA.length) A[len++] = leftA[i++];
        while (j < rightA.length) A[len++] = rightA[j++];
    }

    //获取节点a的根节点编号（带路径压缩）
    public int find(int[] id, int a) {
        int i, root, k;
        root = a;
        while (id[root] >= 0) root = id[root];  //此处，若id[root] >= 0，说明此时的a不是根节点，因为唯有根节点的值小于0
        // 路径压缩：将沿途所有节点直接挂在 root 下
        k = a;
        while (k != root) {  //将a节点所在树的所有节点，都变成root的直接子节点
            i = id[k];
            id[k] = root;
            k = i;
        }
        return root;
    }

    //判断顶点a和顶点b的根节点大小，根节点值越小，代表其对应树的节点越多，将节点少的树的节点添加到节点多的树上
    public void union(int[] id, int a, int b) {
        int ida = find(id, a);   //得到顶点a的根节点
        int idb = find(id, b);   //得到顶点b的根节点
        int num = id[ida] + id[idb];  //由于根节点值必定小于0，此处num值必定小于零
        // 按大小合并：小树合并到大树上
        if (id[ida] < id[idb]) {
            id[idb] = ida;    //将顶点b作为顶点a根节点的直接子节点
            id[ida] = num;   //更新根节点的id值
        } else {
            id[ida] = idb;    //将顶点a作为顶点b根节点的直接子节点
            id[idb] = num;    //更新根节点的id值
        }
    }

    //获取图A的最小生成树
    public ArrayList<edge> getMinSpanTree(int n, edge[] A) {
        ArrayList<edge> list = new ArrayList<edge>();
        int[] id = new int[n];
        // 初始化并查集，每个顶点自成一个集合
        for (int i = 0; i < n; i++)
            id[i] = -1;        //初始化id(x)，令所有顶点的id值为-1，即表示为根节点
        edgeSort(A);   //使用合并排序，对于图中所有边权值进行从小到大排序
        int count = 0;
        // 按边权从小到大遍历每条边
        for (int i = 0; i < A.length; i++) {
            int a = A[i].a;
            int b = A[i].b;
            int ida = find(id, a - 1);
            int idb = find(id, b - 1);
            // 两端点不在同一连通分量，加入该边不会成环
            if (ida != idb) {
                list.add(A[i]);
                count++;
                union(id, a - 1, b - 1);
            }
            //输出每一次添加完一条边后的所有顶点id值
            for (int j = 0; j < id.length; j++)
                System.out.print(id[j] + " ");
            System.out.println();

            if (count >= n - 1)
                break;
        }
        return list;
    }

    public static void main(String[] args) {
        KruskalMinimumSpanningTree test = new KruskalMinimumSpanningTree();
        Scanner in = new Scanner(System.in);
        System.out.println("请输入顶点数a和具体边数p：");
        int n = in.nextInt();
        int p = in.nextInt();
        edge[] A = new edge[p];
        System.out.println("请依次输入具体边对于的顶点和权值：");
        // 读入所有边
        for (int i = 0; i < p; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            int value = in.nextInt();
            A[i] = test.new edge(a, b, value);
        }
        // 执行 Kruskal 算法
        ArrayList<edge> list = test.getMinSpanTree(n, A);
        System.out.println("使用Kruskal算法得到的最小生成树具体边和权值分别为：");
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).a + "——>" + list.get(i).b + ", " + list.get(i).value);
        }
    }
}
