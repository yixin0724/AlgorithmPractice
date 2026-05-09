package com.topologicalsort;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

/**
 * 问题：课程安排，给定课程依赖关系，判断是否可以完成所有课程。
 * 方法：使用拓扑排序。
 * 解题思路：把课程看作有向图顶点，先修关系看作有向边。
 * 统计每个课程入度，将入度为 0 的课程入队并逐个移除；如果最终移除课程数等于总课程数，则不存在环，可以完成。
 * 时间复杂度：O(V+E)，V 为课程数，E 为依赖关系数量。
 * 空间复杂度：O(V+E)，需要邻接表、入度数组和队列。
 */
public class CourseScheduleTopologicalSort {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入课程数和依赖关系数量：");
        int courseCount = scanner.nextInt();
        int relationCount = scanner.nextInt();
        List<List<Integer>> graph = new ArrayList<List<Integer>>();
        for (int i = 0; i < courseCount; i++) {
            graph.add(new ArrayList<Integer>());
        }
        int[] indegree = new int[courseCount];
        System.out.println("请输入每个依赖关系：课程 先修课程");
        for (int i = 0; i < relationCount; i++) {
            int course = scanner.nextInt();
            int prerequisite = scanner.nextInt();
            graph.get(prerequisite).add(course);
            indegree[course]++;
        }
        System.out.println("是否可以完成所有课程：" + canFinish(graph, indegree));
    }

    public static boolean canFinish(List<List<Integer>> graph, int[] indegree) {
        Queue<Integer> queue = new ArrayDeque<Integer>();
        for (int i = 0; i < indegree.length; i++) {
            if (indegree[i] == 0) {
                queue.offer(i);
            }
        }
        int learned = 0;
        while (!queue.isEmpty()) {
            int current = queue.poll();
            learned++;
            for (int next : graph.get(current)) {
                indegree[next]--;
                if (indegree[next] == 0) {
                    queue.offer(next);
                }
            }
        }
        return learned == indegree.length;
    }
}
