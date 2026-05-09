package com.shortestpath;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * 问题：求非负权图中源点到其他所有顶点的最短距离。
 * 方法：使用 Dijkstra 算法和优先队列。
 * 解题思路：维护源点到每个顶点的当前最短距离，每次从优先队列中取出距离最小的顶点。
 * 用该顶点继续松弛它的所有邻边，若能得到更短距离，则更新距离并重新入队。
 * 时间复杂度：O((V+E)logV)，V 为顶点数，E 为边数。
 * 空间复杂度：O(V+E)，需要邻接表、距离数组和优先队列。
 */
public class DijkstraShortestPath {
    static class Edge {
        int to;
        int weight;
        Edge next;

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
        Edge[] graph = new Edge[vertexCount];
        System.out.println("请输入每条有向边的起点、终点和权值：");
        for (int i = 0; i < edgeCount; i++) {
            int from = scanner.nextInt();
            int to = scanner.nextInt();
            int weight = scanner.nextInt();
            graph[from] = new Edge(to, weight, graph[from]);
        }
        System.out.println("源点到各顶点的最短距离为：" + Arrays.toString(dijkstra(graph, source)));
    }

    public static int[] dijkstra(Edge[] graph, int source) {
        int[] distance = new int[graph.length];
        Arrays.fill(distance, Integer.MAX_VALUE);
        distance[source] = 0;
        PriorityQueue<int[]> queue = new PriorityQueue<int[]>((a, b) -> a[1] - b[1]);
        queue.offer(new int[]{source, 0});
        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int vertex = current[0];
            int currentDistance = current[1];
            if (currentDistance != distance[vertex]) {
                continue;
            }
            for (Edge edge = graph[vertex]; edge != null; edge = edge.next) {
                if (distance[vertex] != Integer.MAX_VALUE
                        && distance[edge.to] > distance[vertex] + edge.weight) {
                    distance[edge.to] = distance[vertex] + edge.weight;
                    queue.offer(new int[]{edge.to, distance[edge.to]});
                }
            }
        }
        return distance;
    }
}
