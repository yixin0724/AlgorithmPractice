package com.dp;

import java.util.Scanner;
/**
 * 问题：数字三角形最大路径和。
 * 方法：使用动态规划自底向上计算最大路径和，并用一维路径数组辅助输出路径。
 * 解题思路：先把最后一层作为初始状态，再从倒数第二层向上更新 dp[i][j]=arr[i][j]+max(dp[i+1][j],dp[i+1][j+1])。
 * 每处理一层时记录下一层最大值所在列，最后根据记录输出路径上的数字。
 * 时间复杂度：O(n^2)，n 为三角形层数。
 * 空间复杂度：O(n^2)，当前实现仍使用二维 dp 表和原始数组。
 */
public class NumberTriangleMaxPathOptimizedDp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int len = sc.nextInt();
        int[][] dp = new int[len][len];
        int[][] arr = new int[len][len];
        int[] path = new int[len];
        //自顶向下输入
        for(int i=0;i<len;i++) {
            for(int j=0;j<=i;j++) {
                arr[i][j] = sc.nextInt();
            }
        }
        //dp数组赋值
        for(int i=0;i<len;i++) {
            dp[len-1][i] = arr[len-1][i];
        }
        //求最大路径和and最大路径
        for(int i=len-2;i>=0;i--) {
            for(int j=0;j<=i;j++) {
                dp[i][j] = arr[i][j]+Math.max(dp[i+1][j],dp[i+1][j+1]);
            }
            //求当前层的路径
            int max = 0;
            for(int j=0;j<=i+1;j++) {	//0到len-1层 所以是i+1
                if(dp[i+1][j]>max) {	//当前层的最大值就是需要走的路径
                    max=dp[i+1][j];
                    path[i+1]=j;	//记录路径坐标
                }
            }
        }
        System.out.println("最大路径和："+dp[0][0]);
        /*for(int i=0;i<len;i++) {	//输出路径的坐标
        	System.out.println(i+" "+path[i]);
        }*/
        //输出路径上的值
        for(int i=0;i<len;i++) {
            System.out.println("第"+i+"层:"+arr[i][path[i]]);
        }
    }
}

