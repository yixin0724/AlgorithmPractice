package com.shortestpath;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * Dijkstra 最短路径算法
 *
 * ═══════════════════════════════════════════════════════════
 * 人为思考过程：
 * ═══════════════════════════════════════════════════════════
 * 面对"求非负权图中源点到所有其他顶点的最短距离"这个问题，BFS 解决不了带权图，暴力枚举所有路径又是指数级的。
 *
 * 1. 贪心直觉：从源点出发，当前距离最短的那个顶点，它的最短路径应该已经确定了——因为通过其他点再绕回来只会更远（边权非负保证）。
 * 2. 确定一个顶点后，用它的最短距离去"松弛"它的邻居：如果从源点经过该顶点到达邻居的路径比当前记录的更短，就更新。
 * 3. 不断重复"找最短未确定顶点 -> 松弛其邻居"的过程，直到所有顶点都被确定。
 * 4. 这个贪心策略的正确性依赖于边权非负：一个顶点的最短路径不会因为后续加入其他顶点的松弛而被推翻。
 * 5. 每次找最短距离的顶点如果用扫描 O(V) 太慢——可以用优先队列（最小堆）来维护，每次取出堆顶 O(log V)。
 * 6. 同一个顶点可能多次入队（因为距离被多次更新），需要用"惰性删除"策略：取出顶点时检查距离是否与记录一致，不一致则跳过。
 *
 * ═══════════════════════════════════════════════════════════
 * 具体措施（算法步骤）：
 * ═══════════════════════════════════════════════════════════
 * 1. 初始化距离数组：源点距离为 0，其他顶点为无穷大。
 * 2. 将源点 (vertex=source, distance=0) 加入优先队列。
 * 3. 循环处理优先队列，直到队列为空：
 *    a) 取出堆顶元素（当前距离最小的顶点）。
 *    b) 惰性删除：若取出的距离与 distances[vertex] 不一致，跳过。
 *    c) 遍历该顶点的所有邻边，尝试松弛：
 *       - 若 distances[vertex] + edge.weight < distances[edge.to]，则更新距离并加入优先队列。
 * 4. 返回距离数组。
 *
 * ═══════════════════════════════════════════════════════════
 * 复杂度分析：
 * ═══════════════════════════════════════════════════════════
 * 时间复杂度：O((V + E) * log V)
 *   - V 为顶点数，E 为边数。
 *   - 每个顶点最多入队 E 次（每次松弛），每次堆操作 O(log V)。
 * 空间复杂度：O(V + E)
 *   - 距离数组 O(V)，优先队列 O(V)，邻接表 O(E)。
 */
public class DijkstraShortestPath {

    /**
     * 邻接表的边节点，使用链式前向星存储
     */
    static class Edge {
        int to;       // 目标顶点
        int weight;   // 边权
        Edge next;    // 下一条边

        Edge(int to, int weight, Edge next) {
            this.to = to;
            this.weight = weight;
            this.next = next;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入顶点数、边数和源点编号：");
        int vertexCount = scanner.nextInt();
        int edgeCount = scanner.nextInt();
        int source = scanner.nextInt();

        // 邻接表，每个顶点维护一条链表
        Edge[] graph = new Edge[vertexCount];
        System.out.println("请输入每条有向边的起点、终点和权值：");
        for (int i = 0; i < edgeCount; i++) {
            int from = scanner.nextInt();
            int to = scanner.nextInt();
            int weight = scanner.nextInt();
            // 链式前向星插入：头插法
            graph[from] = new Edge(to, weight, graph[from]);
        }

        System.out.println("源点到各顶点的最短距离为：" + Arrays.toString(dijkstra(graph, source)));
    }

    /**
     * Dijkstra 算法计算源点到所有顶点的最短距离
     *
     * @param graph  邻接表表示的图
     * @param source 源点编号
     * @return 源点到每个顶点的最短距离数组
     */
    public static int[] dijkstra(Edge[] graph, int source) {
        // 距离数组初始化为无穷大
        int[] distance = new int[graph.length];
        Arrays.fill(distance, Integer.MAX_VALUE);
        distance[source] = 0;

        // 最小堆：按距离升序排列，元素为 {顶点编号, 当前距离}
        PriorityQueue<int[]> queue = new PriorityQueue<int[]>((a, b) -> a[1] - b[1]);
        queue.offer(new int[]{source, 0});

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int vertex = current[0];
            int currentDistance = current[1];

            // 惰性删除：如果取出的距离与记录不一致，说明是旧数据，跳过
            if (currentDistance != distance[vertex]) {
                continue;
            }

            // 遍历该顶点的所有邻边，尝试松弛每个邻居
            for (Edge edge = graph[vertex]; edge != null; edge = edge.next) {
                if (distance[vertex] != Integer.MAX_VALUE
                        && distance[edge.to] > distance[vertex] + edge.weight) {
                    // 松弛成功，更新距离并入队
                    distance[edge.to] = distance[vertex] + edge.weight;
                    queue.offer(new int[]{edge.to, distance[edge.to]});
                }
            }
        }

        return distance;
    }
}
