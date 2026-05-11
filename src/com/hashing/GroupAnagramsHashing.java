package com.hashing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * LeetCode Hot 100 - 49. Group Anagrams
 *
 * 思路：
 * 字母异位词的特点是：每个字母出现的次数完全相同，只是顺序不同。
 * 因此可以为每个字符串统计 26 个小写字母的出现次数，并把这个统计结果拼成一个 key。
 *
 * 例如：
 * eat 和 tea 的字符计数相同，所以它们生成的 key 也相同。
 * bat 的字符计数不同，所以会生成另一个 key。
 *
 * 使用 HashMap<String, List<String>> 进行分组：
 * key 表示某一种字符计数组合；
 * value 表示所有拥有相同字符计数的字符串，也就是同一组字母异位词。
 *
 * 时间复杂度：O(n * k)，n 是字符串数量，k 是字符串平均长度。
 * 空间复杂度：O(n * k)，主要用于保存分组结果和哈希表。
 */
public class GroupAnagramsHashing {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter string count:");
        int n = scanner.nextInt();

        String[] strs = new String[n];
        System.out.println("Please enter strings:");
        for (int i = 0; i < n; i++) {
            strs[i] = scanner.next();
        }

        List<List<String>> result = groupAnagrams(strs);
        if (result.isEmpty()) {
            System.out.println("No groups found");
        } else {
            for (List<String> group : result) {
                System.out.println(group);
            }
        }

        scanner.close();
    }

    public static List<List<String>> groupAnagrams(String[] strs) {
        // key 是字符计数生成的字符串，value 是拥有相同 key 的单词列表。
        Map<String, List<String>> map = new HashMap<>();

        for (String s : strs) {
            // 字母异位词会得到相同的 key，因此可以被放到同一个分组。
            String key = buildCountKey(s);
            if (!map.containsKey(key)) {
                // 如果map中不存在该分组，则创建新组后添加到map中
                map.put(key, new ArrayList<>());
            }
            map.get(key).add(s);
        }

        // LeetCode 要求返回 List<List<String>>，Map 的所有 value 正好就是最终分组结果。
        return new ArrayList<>(map.values());
    }

    public static String buildCountKey(String s) {
        // count[0] 表示字母 a 出现次数，count[1] 表示 b，依次类推。
        int[] count = new int[26];
        for (char c : s.toCharArray()) {
            count[c - 'a']++;
        }

        // 用分隔符拼接，避免不同计数组合产生相同字符串。
        // 例如 [1, 11] 和 [11, 1] 如果直接拼接会混淆，使用 # 后可以区分。
        StringBuilder key = new StringBuilder();
        for (int i = 0; i < count.length; i++) {
            key.append('#').append(count[i]);
        }
        return key.toString();
    }
}
