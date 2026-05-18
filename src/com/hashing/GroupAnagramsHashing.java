package com.hashing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * LeetCode Hot 100 - 49. Group Anagrams（字母异位词分组）
 *
 * ═══════════════════════════════════════════════════════════
 * 人为思考过程：
 * ═══════════════════════════════════════════════════════════
 * 1. 暴力解法的核心问题在于需要两两比较字符串，
 *    非常耗费时间。需要一种方法，让每个字符串能自动找到它属于哪一组。
 * 2. 字母异位词有一个本质特征：它们包含的字母种类和数量完全相同。
 *    例如 "eat" 和 "tea" 都包含 1 个 e、1 个 a、1 个 t。
 * 3. 如果把每个字符串的"字母计数结果"当作一个唯一标识（key），
 *    那么互为异位词的字符串就会产生相同的 key，
 *    自然就能被分到同一组。
 * 4. 具体怎么做这个 key？用一个长度为 26 的数组 count[]，
 *    count[0] 记录 'a' 的出现次数，count[1] 记录 'b' 的次数...
 *    然后把 count[] 拼接成字符串，比如 "eat" 变成 "#1#0#0...#1#0...#1..."。
 * 5. 用 HashMap<String, List<String>>：key 是上面生成的标识字符串，
 *    value 是所有拥有相同标识的字符串列表。
 * 6. 遍历每个字符串，生成它的 key，然后在 HashMap 中找到对应的 list 并加入。
 *    最后把所有 list 收集起来就是答案。
 * 7. 注意拼接 key 时要用分隔符（如 #），防止不同计数组合产生相同的字符串。
 *    例如 [1, 11] 和 [11, 1] 如果直接拼接就无法区分。
 *
 * ═══════════════════════════════════════════════════════════
 * 具体措施（算法步骤）：
 * ═══════════════════════════════════════════════════════════
 * 1. 创建 HashMap<String, List<String>>。
 * 2. 遍历每个字符串 s：
 *    a. 调用 buildCountKey(s) 生成该字符串的 key。
 *    b. 若 map 中不存在该 key，创建新的 ArrayList 作为 value。
 *    c. 将 s 添加到 map.get(key) 对应的列表中。
 * 3. 返回 new ArrayList<>(map.values()) 作为最终分组结果。
 *
 * buildCountKey 方法：
 *    a. 创建长度为 26 的 int 数组 count。
 *    b. 遍历 s 的每个字符 c，count[c - 'a']++。
 *    c. 用 StringBuilder，在每个计数值前加 '#' 拼接成 key 字符串。
 *    d. 返回 key。
 *
 * ═══════════════════════════════════════════════════════════
 * 复杂度分析：
 * ═══════════════════════════════════════════════════════════
 * 时间复杂度：O(n * k)
 *   - 遍历 n 个字符串，每个字符串需要 O(k) 时间统计字符和拼接 key，
 *     其中 k 是字符串的平均长度。
 * 空间复杂度：O(n * k)
 *   - HashMap 中存储了所有字符串的分组结果，
 *     每个 key 长度为常数 26+分隔符。
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
        // key 是字符计数生成的字符串，value 是拥有相同 key 的单词列表
        Map<String, List<String>> map = new HashMap<>();

        for (String s : strs) {
            // 字母异位词会得到相同的 key，因此可以被放到同一个分组
            String key = buildCountKey(s);
            if (!map.containsKey(key)) {
                // 如果 map 中不存在该分组，则创建新组后添加到 map 中
                map.put(key, new ArrayList<>());
            }
            map.get(key).add(s);
        }

        // Map 的所有 value 正好就是最终分组结果
        return new ArrayList<>(map.values());
    }

    /**
     * 根据字符串中各字母的出现次数生成唯一标识 key。
     * 例如 count[0] 表示 'a' 出现次数，count[1] 表示 'b'，以此类推。
     * 使用 '#' 分隔符避免不同计数组合产生歧义。
     */
    public static String buildCountKey(String s) {
        // count[0] 表示字母 a 出现次数，count[1] 表示 b，依次类推
        int[] count = new int[26];
        for (char c : s.toCharArray()) {
            count[c - 'a']++;
        }

        // 用分隔符拼接，避免不同计数组合产生相同字符串
        // 例如 [1, 11] 和 [11, 1] 如果直接拼接会混淆，使用 # 后可以区分
        StringBuilder key = new StringBuilder();
        for (int i = 0; i < count.length; i++) {
            key.append('#').append(count[i]);
        }
        return key.toString();
    }
}
