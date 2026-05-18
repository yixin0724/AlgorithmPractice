package com.depthfirstsearch;

import java.util.Scanner;

/**
 * LeetCode Hot 100 - 200. Number of Islands（岛屿数量）
 *
 * ═══════════════════════════════════════════════════════════
 * 人为思考过程：
 * ═══════════════════════════════════════════════════════════
 * 面对"统计岛屿数量"这个问题，可以从人类的视觉直觉出发来推理：
 *
 * 1. 当我们看一张地图时，一块连在一起的陆地就是一个"岛屿"——这个概念非常直观。
 * 2. 要数清有多少个岛屿，可以逐个格子扫过去，碰到一块陆地就"圈出来"整个岛，然后继续扫。
 * 3. "圈出来"意味着把这块陆地以及与它上下左右相连的所有陆地都标记掉，防止后面重复计数。
 * 4. 这个过程本质上是对每个"1"格子执行一次深度优先搜索（DFS）或广度优先搜索（BFS），把连通区域全部染色。
 * 5. 染色操作可以直接在原地修改网格（将 1 改为 0），省去额外的 visited 数组。
 * 6. 每触发一次 DFS/BFS 就代表发现了一个新岛屿，计数器加一，最终遍历完所有格子后计数器的值就是答案。
 * 7. 选择 DFS 是因为递归写法简洁直观，正好契合"一路走到底再回溯"的思维模式。
 *
 * ═══════════════════════════════════════════════════════════
 * 具体措施（算法步骤）：
 * ═══════════════════════════════════════════════════════════
 * 1. 遍历网格的每一行每一列。
 * 2. 当 grid[i][j] == 1 时，岛屿计数加一，然后调用 DFS 淹没整个岛屿。
 * 3. DFS 的内部逻辑：
 *    a) 边界检查：越界或当前格子为 0 则直接返回。
 *    b) 将当前格子值置为 0（沉岛/已访问标记）。
 *    c) 递归向上下左右四个方向继续淹没。
 * 4. 遍历结束后返回岛屿计数。
 *
 * ═══════════════════════════════════════════════════════════
 * 复杂度分析：
 * ═══════════════════════════════════════════════════════════
 * 时间复杂度：O(n * m)
 *   - n 为网格行数，m 为网格列数。
 *   - 每个格子至多被访问一次（第一次 DFS 访问后被置为 0，不再进入 DFS 分支）。
 * 空间复杂度：O(n * m)
 *   - 原地修改网格，不需要额外 visited 数组，但递归调用栈在最坏情况下（网格全为 1 的蛇形路径）可能覆盖整个网格。
 */
public class IslandCountDfs {

    /** 四个方向的偏移量，用于 DFS 向上下左右扩展 */
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

    /**
     * 统计 0/1 网格中的岛屿数量
     *
     * @param grid 二维网格，1 表示陆地，0 表示水域
     * @return 岛屿的数量
     */
    public static int countIslands(int[][] grid) {
        int count = 0;
        // 遍历网格每个格子，发现未访问的陆地则计数并沉岛
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 1) {
                    count++;               // 新岛屿计数
                    dfs(grid, i, j);       // DFS 淹没整个岛屿
                }
            }
        }
        return count;
    }

    /**
     * 深度优先搜索淹没与 (row, col) 相连的所有陆地
     */
    private static void dfs(int[][] grid, int row, int col) {
        // 边界检查：越界或遇到水域则终止递归
        if (row < 0 || row >= grid.length || col < 0 || col >= grid[0].length || grid[row][col] == 0) {
            return;
        }

        // 沉岛：将当前陆地标记为水域，防止重复访问
        grid[row][col] = 0;

        // 向上下左右四个方向递归淹没相邻陆地
        for (int[] direction : DIRECTIONS) {
            dfs(grid, row + direction[0], col + direction[1]);
        }
    }
}
