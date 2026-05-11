# 分类说明

将目前练习过的题目进行记录，按照解题方法对算法练习进行归类。

| 包名 | 适用题型 | 当前示例 |
| --- | --- | --- |
| `backtracking` | 回溯法，适用于组合、排列、子集、搜索决策树、0/1 选择等问题。 | `ZeroOneKnapsackBacktracking` |
| `bitmanipulation` | 位运算，适用于异或性质、状态压缩、位掩码枚举等问题。 | `SingleNumberBitManipulation` |
| `breadthfirstsearch` | 广度优先搜索，适用于层序遍历、无权图最短路径、按步数扩展状态。 | `MazeShortestPathBfs` |
| `bruteforce` | 暴力枚举，适用于数据规模较小或用于对照验证的直接枚举解法。 | `KthSmallestBruteForce`, `LinearSearchBruteForce` |
| `depthfirstsearch` | 深度优先搜索，适用于图或树遍历、连通性判断、路径搜索。 | `IslandCountDfs` |
| `differencearray` | 差分数组，适用于多次区间更新后统一还原结果。 | `RangeUpdateDifferenceArray` |
| `divideandconquer` | 分治法，适用于将问题拆成相同结构子问题再合并结果的题目。 | `InversionCountDivideAndConquer`, `MaximumSubarrayDivideAndConquer` |
| `dp` | 动态规划，适用于最优子结构、重叠子问题、状态转移类题目。 | `ZeroOneKnapsackDp`, `HouseRobberDp` 等 |
| `greedy` | 贪心算法，适用于每一步局部最优可推出整体最优的题目。 | `FractionalKnapsackGreedy`, `PrimMinimumSpanningTree`, `KruskalMinimumSpanningTree` |
| `hashing` | 哈希表或计数数组，适用于快速查找、频次统计、去重和映射关系。 | `UniqueCharacterHashing` |
| `heap` | 堆和优先队列，适用于 Top K、动态中位数、多路归并、优先级扩展。 | `KthLargestMinHeap` |
| `math` | 数学方法，适用于公式推导、组合计数、概率、几何、矩阵快速幂。 | `JosephusProblemMath` |
| `monotonicqueue` | 单调队列，适用于滑动窗口最大值、最小值和区间最优值维护。 | `SlidingWindowMaximumMonotonicQueue` |
| `monotonicstack` | 单调栈，适用于下一个更大/更小元素、柱状图面积等问题。 | `NextGreaterElementMonotonicStack` |
| `numbertheory` | 数论，适用于质数、最大公约数、模运算、快速幂、因子分解。 | `GreatestCommonDivisorNumberTheory` |
| `prefixsum` | 前缀和，适用于区间和、子数组和、二维区域和。 | `RangeSumQueryPrefixSum` |
| `recursion` | 递归，适用于可以直接拆成同类子问题的问题。 | `FactorialRecursion` |
| `search` | 查找算法，适用于二分查找、顺序查找等基础查找问题。 | `BinarySearch` |
| `shortestpath` | 最短路径，适用于 Dijkstra、Bellman-Ford、Floyd 等图论题。 | `DijkstraShortestPath` |
| `simulation` | 模拟，适用于按题意逐步执行、规则细节较多的问题。 | `SpiralMatrixSimulation` |
| `slidingwindow` | 滑动窗口，适用于连续子数组或子串的最长、最短、计数问题。 | `LongestSubstringWithoutRepeatingSlidingWindow` |
| `sorting` | 排序算法，适用于排序实现、排序思想应用和排序相关题目。 | `BubbleSort`, `SelectionSort`, `InsertionSort`, `ShellSort`, `MergeSort`, `QuickSort`, `HeapSort`, `CountingSort`, `BucketSort`, `RadixSort` |
| `stringmatching` | 字符串匹配，适用于 KMP、Rabin-Karp、字符串哈希、模式串匹配。 | `KmpStringMatching` |
| `topologicalsort` | 拓扑排序，适用于课程安排、任务调度、有向无环图依赖关系。 | `CourseScheduleTopologicalSort` |
| `trie` | 字典树，适用于前缀匹配、单词检索、字符串集合统计。 | `TriePrefixSearch` |
| `twopointers` | 双指针，适用于有序数组、链表、左右夹逼、快慢指针问题。 | `TwoSumSortedTwoPointers` |
| `unionfind` | 并查集，适用于连通性、集合合并、环检测和连通分量统计。 | `VillageRoadConnectivityUnionFind` |



# 目标

希望通过刷题，学习下面相关算法知识

| 类别         | 核心                        |
| ------------ | --------------------------- |
| 哈希表       | 快速查找、计数、去重        |
| 双指针       | 有序数组、链表、区间收缩    |
| 滑动窗口     | 子串、子数组、最长/最短窗口 |
| 栈           | 单调栈、括号匹配、表达式    |
| 链表         | 快慢指针、反转、合并、环    |
| 二叉树       | DFS、BFS、递归返回值设计    |
| 回溯         | 子集、排列、组合、搜索路径  |
| 动态规划     | 状态定义、状态转移、初始化  |
| 图           | BFS、DFS、拓扑排序、并查集  |
| 堆/贪心/二分 | TopK、区间选择、答案二分    |

每做一道题，都问一遍自己：

> 这题属于哪一类？
>  它和我之前做过的哪道题本质一样？
>  以后看到什么特征，我应该想到这种方法？

这一步我感觉比 AC 更重要。



# 做题建议

建议：每道题按“五步法”做，不要直接看答案

### 第 1 步：先判断题型

读完题后，先不要写代码，先判断：

- 是否要快速查找？可能是哈希表。
- 是否是连续子数组/子串？可能是滑动窗口、前缀和、动态规划。
- 是否是有序数组？可能是双指针、二分。
- 是否是树？先想 DFS/BFS。
- 是否要求所有方案？可能是回溯。
- 是否求最优值？可能是 DP、贪心、二分答案。

题型判断能力，是刷题最核心的能力。

------

### 第 2 步：先写暴力思路

哪怕暴力会超时，也要写出来或在脑子里讲出来。

例如“两数之和”：

暴力思路是两层循环找两个数，时间复杂度 O(n²)。

然后再问：

> 为什么慢？
>  慢在哪里？
>  能不能用额外空间换时间？

这样你才会自然想到哈希表，而不是死记答案。

------

### 第 3 步：看答案时只看思路，不要立刻看代码

很多人刷题效率低，是因为直接看了完整代码，感觉“我懂了”，但其实只是看懂了别人的代码。

正确做法：

1. 先看题解思路。
2. 关掉题解。
3. 自己重新写代码。
4. 卡住了再看一小段。
5. 看完后重新独立写一遍。

目标是：**最终代码必须是你自己写出来的，而不是抄出来的。**

------

### 第 4 步：题后复盘，必须写 5 行笔记

每道题做完后，用固定模板记录：

```
题目：最长无重复字符子串
类型：滑动窗口 + 哈希表
核心思路：用窗口维护当前无重复子串，右指针扩展，遇到重复时左指针收缩。
关键变量：left, right, set / map
易错点：left 不能随便回退；更新最大长度的位置要注意。
相似题：最小覆盖子串、找到字符串中所有字母异位词
```

不要写长篇笔记，太长你也不会看。
 真正有用的是：**题型、核心思路、易错点、相似题**。

------

### 第 5 步：必须复刷，否则一定会忘

“做完就忘”的根本原因是没有复刷机制。

可以考虑用这个节奏：

| 时间点   | 要做什么             |
| -------- | -------------------- |
| 第 1 天  | 第一次做，允许看题解 |
| 第 2 天  | 不看题解重写一遍     |
| 第 7 天  | 再写一遍，只看题目   |
| 第 15 天 | 快速复盘思路和代码   |
| 面试前   | 按题型批量复刷       |

一题真正掌握的标准不是“我看懂了”，而是：

> 过一周后，我还能不看题解写出来，并且能说清楚为什么这样做。
