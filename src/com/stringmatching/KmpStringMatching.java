package com.stringmatching;

import java.util.Arrays;
import java.util.Scanner;

/**
 * 问题：在文本串中查找模式串第一次出现的位置。
 * 方法：使用 KMP 字符串匹配算法。
 * 解题思路：先为模式串构建 next 数组，记录每个位置之前的最长相等前后缀长度。
 * 匹配失败时，模式串不回到开头，而是根据 next 数组跳转到可继续匹配的位置，从而避免重复比较。
 * 时间复杂度：O(n+m)，n 为文本串长度，m 为模式串长度。
 * 空间复杂度：O(m)，需要 next 数组。
 */
public class KmpStringMatching {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入文本串：");
        String text = scanner.nextLine();
        System.out.println("请输入模式串：");
        String pattern = scanner.nextLine();
        int[] next = buildNext(pattern);
        System.out.println("next 数组为：" + Arrays.toString(next));
        System.out.println("第一次匹配位置为：" + indexOf(text, pattern));
    }

    public static int indexOf(String text, String pattern) {
        if (pattern.length() == 0) {
            return 0;
        }
        int[] next = buildNext(pattern);
        int j = 0;
        for (int i = 0; i < text.length(); i++) {
            while (j > 0 && text.charAt(i) != pattern.charAt(j)) {
                j = next[j - 1];
            }
            if (text.charAt(i) == pattern.charAt(j)) {
                j++;
            }
            if (j == pattern.length()) {
                return i - pattern.length() + 1;
            }
        }
        return -1;
    }

    public static int[] buildNext(String pattern) {
        int[] next = new int[pattern.length()];
        int j = 0;
        for (int i = 1; i < pattern.length(); i++) {
            while (j > 0 && pattern.charAt(i) != pattern.charAt(j)) {
                j = next[j - 1];
            }
            if (pattern.charAt(i) == pattern.charAt(j)) {
                j++;
            }
            next[i] = j;
        }
        return next;
    }
}
