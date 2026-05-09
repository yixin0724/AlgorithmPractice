package com.depthfirstsearch;

import java.util.Scanner;

/**
 * 问题：统计 0/1 网格中的岛屿数量，1 表示陆地，0 表示水域。
 * 方法：使用深度优先搜索。
 * 解题思路：遍历每个格子，遇到未访问的陆地时，说明发现一个新岛屿。
 * 从该格子开始 DFS，把上下左右相连的陆地全部标记为已访问，避免重复计数。
 * 时间复杂度：O(nm)，n 和 m 分别为网格行数和列数。
 * 空间复杂度：O(nm)，最坏情况下递归调用栈会覆盖整个网格。
 */
public class IslandCountDfs {
    private static final int[][] DIRECTIONS = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入网格行数和列数：");
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        int[][] grid = new int[n][m];
        System.out.println("请输入网格，1 表示陆地，0 表示水域：");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                grid[i][j] = scanner.nextInt();
            }
        }
        System.out.println("岛屿数量为：" + countIslands(grid));
    }

    public static int countIslands(int[][] grid) {
        int count = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 1) {
                    count++;
                    dfs(grid, i, j);
                }
            }
        }
        return count;
    }

    private static void dfs(int[][] grid, int row, int col) {
        if (row < 0 || row >= grid.length || col < 0 || col >= grid[0].length || grid[row][col] == 0) {
            return;
        }
        grid[row][col] = 0;
        for (int[] direction : DIRECTIONS) {
            dfs(grid, row + direction[0], col + direction[1]);
        }
    }
}
