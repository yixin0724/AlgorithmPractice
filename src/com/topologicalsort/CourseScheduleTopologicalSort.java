package com.topologicalsort;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

/**
 * LeetCode Hot 100 - 207. Course Schedule (课程表)
 *
 * ═══════════════════════════════════════════════════════════
 * 人为思考过程：
 * ═══════════════════════════════════════════════════════════
 * 1. 问题本质：有 n 门课程，某些课程有先修要求（学 B 之前必须学 A），
 *    问是否存在一种学习顺序能完成所有课程。这实质是判断有向图是否存在
 *    环 —— 若存在循环依赖（A 依赖 B，B 依赖 A），则不可能完成。
 * 2. 把课程看作顶点，先修关系看作有向边（A → B 表示学 B 前要先学 A）。
 *    没有先修要求的课程（入度为 0）作为起始点，因为它们可以直接学。
 * 3. 拓扑排序的思路：每次找入度为 0 的顶点，将其移除（表示这门课已学），
 *    同时由于它被移除，所有以它为前置的课程的入度减 1。如果某门课入度
 *    变为 0，说明它的前置课程全部完成，可以排入学习计划。
 * 4. 重复上述过程，直到没有入度为 0 的顶点。如果所有顶点都被遍历到，
 *    说明图无环，可以完成所有课程；否则说明存在环，不可能完成。
 * 5. 这就是经典的 BFS 拓扑排序（Kahn 算法），利用队列管理当前入度为 0
 *    的顶点集合。
 *
 * ═══════════════════════════════════════════════════════════
 * 具体措施（算法步骤）：
 * ═══════════════════════════════════════════════════════════
 * 1. 建图：用邻接表 graph 表示有向图，数组 indegree 记录每门课入度。
 *    - 对于每对 (course, prerequisite)，执行：
 *      graph[prerequisite].add(course)，indegree[course]++。
 * 2. 初始化队列：将所有入度为 0 的课程加入队列。
 * 3. BFS 处理：
 *    a. 从队列取出课程 current，已完成课程数 learned++。
 *    b. 遍历 graph[current] 中的每门后继课程 next：
 *       - indegree[next]--。
 *       - 若 indegree[next] == 0，将其加入队列。
 * 4. 检查 learned 是否等于总课程数，相等则返回 true，否则返回 false。
 *
 * ═══════════════════════════════════════════════════════════
 * 复杂度分析：
 * ═══════════════════════════════════════════════════════════
 * 时间复杂度：O(V + E)
 *   - 建图阶段：遍历所有依赖关系 E 条边，O(E)。
 *   - 初始化入度为 0 的队列：遍历所有 V 个顶点，O(V)。
 *   - BFS 阶段：每个顶点入队一次、每条边被访问一次，O(V + E)。
 * 空间复杂度：O(V + E)
 *   - 邻接表 graph 存储所有边：O(V + E)。
 *   - 入度数组 indegree：O(V)。
 *   - 队列 queue 最多存储 O(V) 个顶点。
 */
public class CourseScheduleTopologicalSort {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入课程数和依赖关系数量：");
        int courseCount = scanner.nextInt();
        int relationCount = scanner.nextInt();
        // 建图：邻接表
        List<List<Integer>> graph = new ArrayList<List<Integer>>();
        for (int i = 0; i < courseCount; i++) {
            graph.add(new ArrayList<Integer>());
        }
        // 入度数组
        int[] indegree = new int[courseCount];
        System.out.println("请输入每个依赖关系：课程 先修课程");
        // 读入依赖关系并填充图
        for (int i = 0; i < relationCount; i++) {
            int course = scanner.nextInt();
            int prerequisite = scanner.nextInt();
            graph.get(prerequisite).add(course);
            indegree[course]++;
        }
        // Kahn 算法判断是否可以完成所有课程
        System.out.println("是否可以完成所有课程：" + canFinish(graph, indegree));
    }

    /**
     * 使用 Kahn's BFS 拓扑排序判断有向图中是否存在环
     */
    public static boolean canFinish(List<List<Integer>> graph, int[] indegree) {
        Queue<Integer> queue = new ArrayDeque<Integer>();
        // 初始化：所有入度为 0 的顶点入队
        for (int i = 0; i < indegree.length; i++) {
            if (indegree[i] == 0) {
                queue.offer(i);
            }
        }
        int learned = 0;
        // BFS 拓扑排序
        while (!queue.isEmpty()) {
            int current = queue.poll();
            learned++;
            // 将当前课程移除后，其后继课程的入度减 1
            for (int next : graph.get(current)) {
                indegree[next]--;
                if (indegree[next] == 0) {
                    queue.offer(next);
                }
            }
        }
        // 学习的课程数等于总课程数说明无环
        return learned == indegree.length;
    }
}
