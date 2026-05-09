package com.slidingwindow;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * 问题：求字符串中不含重复字符的最长子串长度。
 * 方法：使用滑动窗口。
 * 解题思路：用左右指针维护一个无重复字符窗口，并用哈希表记录字符最近出现位置。
 * 当右指针遇到窗口内已经出现过的字符时，将左指针移动到上次出现位置的后一位。
 * 时间复杂度：O(n)，n 为字符串长度。
 * 空间复杂度：O(min(n,m))，m 为字符集大小。
 */
public class LongestSubstringWithoutRepeatingSlidingWindow {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入字符串：");
        String text = scanner.nextLine();
        System.out.println("最长无重复子串长度为：" + lengthOfLongestSubstring(text));
    }

    public static int lengthOfLongestSubstring(String text) {
        Map<Character, Integer> lastIndex = new HashMap<Character, Integer>();
        int left = 0;
        int answer = 0;
        for (int right = 0; right < text.length(); right++) {
            char current = text.charAt(right);
            if (lastIndex.containsKey(current) && lastIndex.get(current) >= left) {
                left = lastIndex.get(current) + 1;
            }
            lastIndex.put(current, right);
            answer = Math.max(answer, right - left + 1);
        }
        return answer;
    }
}
