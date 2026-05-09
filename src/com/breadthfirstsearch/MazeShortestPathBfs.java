package com.breadthfirstsearch;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Scanner;

/**
 * 问题：在 0/1 迷宫中，从左上角走到右下角，求最少步数。
 * 方法：使用广度优先搜索。
 * 解题思路：把每个可走格子看作图中的节点，每次向上下左右四个方向扩展。
 * BFS 按层遍历，第一次到达终点时经过的步数就是最短路径长度。
 * 时间复杂度：O(nm)，n 和 m 分别为迷宫行数和列数。
 * 空间复杂度：O(nm)，需要访问标记数组和队列。
 */
public class MazeShortestPathBfs {
    private static final int[][] DIRECTIONS = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入迷宫行数和列数：");
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        int[][] maze = new int[n][m];
        System.out.println("请输入迷宫矩阵，0 表示可走，1 表示障碍：");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                maze[i][j] = scanner.nextInt();
            }
        }
        System.out.println("最少步数为：" + shortestPath(maze));
    }

    public static int shortestPath(int[][] maze) {
        int n = maze.length;
        int m = maze[0].length;
        if (maze[0][0] == 1 || maze[n - 1][m - 1] == 1) {
            return -1;
        }
        boolean[][] visited = new boolean[n][m];
        Queue<int[]> queue = new ArrayDeque<int[]>();
        queue.offer(new int[]{0, 0, 0});
        visited[0][0] = true;
        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            if (current[0] == n - 1 && current[1] == m - 1) {
                return current[2];
            }
            for (int[] direction : DIRECTIONS) {
                int nextRow = current[0] + direction[0];
                int nextCol = current[1] + direction[1];
                if (nextRow >= 0 && nextRow < n && nextCol >= 0 && nextCol < m
                        && maze[nextRow][nextCol] == 0 && !visited[nextRow][nextCol]) {
                    visited[nextRow][nextCol] = true;
                    queue.offer(new int[]{nextRow, nextCol, current[2] + 1});
                }
            }
        }
        return -1;
    }
}
