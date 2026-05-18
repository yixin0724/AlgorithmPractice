package com.breadthfirstsearch;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Scanner;

/**
 * 迷宫最短路径
 *
 * ═══════════════════════════════════════════════════════════
 * 人为思考过程：
 * ═══════════════════════════════════════════════════════════
 * 面对"在迷宫中求最少步数"这一类问题，最自然的直觉是"逐层探索"：
 *
 * 1. 从起点出发，第一步能到达哪些格子？把这些格子全部走一遍。
 * 2. 第二步，在第一步到达的所有格子上，继续向四周走一步，得到新的可到达格子集合。
 * 3. 如此一层一层地向外扩展，就像水波纹扩散一样。
 * 4. 因为每一层代表步数加一，当某一层的某个格子恰好是终点时，那当前层数必然就是最少步数。
 * 5. 这种"逐层遍历"思想正是 BFS 的核心——它天然保证第一次遇到目标时路径最短。
 * 6. 思考过程中还要注意到两个细节：不能走出迷宫边界，不能穿墙（障碍物为 1），已经走过的格子不必再走（防重复）。
 * 7. 于是数据结构上需要队列来按顺序存储每一层的格子，还需要二维布尔数组标记已访问。
 * 8. 如果 BFS 结束后仍未到达终点，说明迷宫无解，返回 -1。
 *
 * ═══════════════════════════════════════════════════════════
 * 具体措施（算法步骤）：
 * ═══════════════════════════════════════════════════════════
 * 1. 检查起点或终点是否为障碍，若是则直接返回 -1。
 * 2. 将起点 (0,0) 加入队列，同时记录步数为 0，标记为已访问。
 * 3. 循环处理队列：
 *    a) 取出队首元素（当前行列坐标与步数）。
 *    b) 若该位置正好是终点，直接返回当前步数。
 *    c) 否则向上下左右四个方向扩展：
 *       - 计算下一位置的行列坐标。
 *       - 检查是否越界、是否为障碍、是否已访问。
 *       - 若合法，标记已访问，将新位置和步数+1加入队列。
 * 4. 循环结束后若未返回，说明不可达，返回 -1。
 *
 * ═══════════════════════════════════════════════════════════
 * 复杂度分析：
 * ═══════════════════════════════════════════════════════════
 * 时间复杂度：O(n * m)
 *   - n 为迷宫行数，m 为迷宫列数。
 *   - 每个格子至多入队一次、出队一次，每次扩展检查四个方向为 O(1)。
 * 空间复杂度：O(n * m)
 *   - visited 数组占用 O(n*m)，队列在最坏情况下也可能存储所有格子。
 */
public class MazeShortestPathBfs {

    /** 四个方向的偏移量：下、上、右、左 */
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

    /**
     * 使用 BFS 求迷宫从左上角到右下角的最少步数
     *
     * @param maze 0/1 矩阵，0 可走，1 障碍
     * @return 最少步数，不可达返回 -1
     */
    public static int shortestPath(int[][] maze) {
        int n = maze.length;
        int m = maze[0].length;

        // 起点或终点是障碍，直接不可达
        if (maze[0][0] == 1 || maze[n - 1][m - 1] == 1) {
            return -1;
        }

        // visited 标记已访问过的格子，避免重复入队
        boolean[][] visited = new boolean[n][m];
        // 队列存储 {行, 列, 当前步数}
        Queue<int[]> queue = new ArrayDeque<int[]>();
        queue.offer(new int[]{0, 0, 0});
        visited[0][0] = true;

        // BFS 主循环：按层遍历
        while (!queue.isEmpty()) {
            int[] current = queue.poll();

            // 到达终点，当前步数即为最短路径长度
            if (current[0] == n - 1 && current[1] == m - 1) {
                return current[2];
            }

            // 向上下左右四个方向扩展
            for (int[] direction : DIRECTIONS) {
                int nextRow = current[0] + direction[0];
                int nextCol = current[1] + direction[1];

                // 检查边界、障碍物、是否已访问
                if (nextRow >= 0 && nextRow < n && nextCol >= 0 && nextCol < m
                        && maze[nextRow][nextCol] == 0 && !visited[nextRow][nextCol]) {
                    visited[nextRow][nextCol] = true;
                    queue.offer(new int[]{nextRow, nextCol, current[2] + 1});
                }
            }
        }

        // BFS 结束未返回，说明终点不可达
        return -1;
    }
}
