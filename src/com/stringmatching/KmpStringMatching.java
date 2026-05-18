package com.stringmatching;

import java.util.Arrays;
import java.util.Scanner;

/**
 * KMP 字符串匹配算法
 *
 * ═══════════════════════════════════════════════════════════
 * 人为思考过程：
 * ═══════════════════════════════════════════════════════════
 * 面对"在文本串中查找模式串"这个问题，暴力匹配每次失败都要回退文本指针和模式指针，复杂度 O(n*m) 在长文本面前显得低效。
 *
 * 1. 核心观察：暴力匹配中丢失了"已匹配部分"的信息——明明已经努力比较了一长段，失败后却不加利用地从头再来。
 * 2. 思考：如果模式串中有一部分是重复的（比如"ababa"中前缀"aba"和后缀"aba"是相同的），那么匹配失败时能不能利用这个重叠信息？
 * 3. 具体来说，当 text[i] 与 pattern[j] 不匹配时，模式串不需要回到开头，而是跳到一个"可能继续匹配"的位置。
 * 4. 这个跳转位置由什么决定？由已经成功匹配的前 j 个字符中最长相等前后缀的长度决定——这就是 next 数组的含义。
 * 5. next[i] 表示 pattern[0..i] 这个子串的最长相等前后缀长度，也是匹配失败时 pattern 指针 j 应该回退到的位置。
 * 6. 构建 next 数组的过程本身就是"模式串自己匹配自己"的过程，思想和主匹配过程如出一辙。
 * 7. 有了 next 数组后，主匹配过程就可以做到文本指针 i 从不回退，只调整 pattern 指针 j，将总复杂度降至 O(n+m)。
 *
 * ═══════════════════════════════════════════════════════════
 * 具体措施（算法步骤）：
 * ═══════════════════════════════════════════════════════════
 * 1. 构建 next 数组（前缀函数）：
 *    a) 初始化 next[0] = 0，j = 0。
 *    b) i 从 1 到 m-1 遍历 pattern：
 *       - 若 pattern[i] != pattern[j]，回退 j = next[j-1]，直到匹配或 j=0。
 *       - 若 pattern[i] == pattern[j]，j++。
 *       - next[i] = j。
 * 2. 主匹配过程：
 *    a) j = 0，i 从 0 到 n-1 遍历 text：
 *       - 若 text[i] != pattern[j]，回退 j = next[j-1]，直到匹配或 j=0。
 *       - 若 text[i] == pattern[j]，j++。
 *       - 若 j == m（完整匹配），返回 i - m + 1。
 * 3. 遍历结束未匹配，返回 -1。
 *
 * ═══════════════════════════════════════════════════════════
 * 复杂度分析：
 * ═══════════════════════════════════════════════════════════
 * 时间复杂度：O(n + m)
 *   - n 为文本串长度，m 为模式串长度。
 *   - 构建 next 数组 O(m)，主匹配 O(n)，每次指针变化均摊 O(1)。
 * 空间复杂度：O(m)
 *   - 仅需要长度为 m 的 next 数组。
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

    /**
     * KMP 匹配算法——在文本串 text 中查找模式串 pattern 首次出现的位置
     *
     * @return 首次匹配的起始索引，未找到返回 -1
     */
    public static int indexOf(String text, String pattern) {
        if (pattern.length() == 0) {
            return 0;
        }

        // 构建前缀函数（next 数组）
        int[] next = buildNext(pattern);

        int j = 0; // pattern 指针
        for (int i = 0; i < text.length(); i++) {
            // 不匹配时，利用 next 数组回退 pattern 指针
            while (j > 0 && text.charAt(i) != pattern.charAt(j)) {
                j = next[j - 1];
            }
            // 当前字符匹配，pattern 指针前进
            if (text.charAt(i) == pattern.charAt(j)) {
                j++;
            }
            // 完整匹配模式串
            if (j == pattern.length()) {
                return i - pattern.length() + 1;
            }
        }
        return -1;
    }

    /**
     * 构建 next 数组（前缀函数），next[i] = pattern[0..i] 的最长相等前后缀长度
     */
    public static int[] buildNext(String pattern) {
        int[] next = new int[pattern.length()];
        int j = 0; // 当前已匹配的前缀长度

        // i 从 1 开始，因为 next[0] 恒为 0
        for (int i = 1; i < pattern.length(); i++) {
            // 不匹配时回退——模式串自己匹配自己
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
