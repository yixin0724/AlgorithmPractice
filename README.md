# 算法题分类说明

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
