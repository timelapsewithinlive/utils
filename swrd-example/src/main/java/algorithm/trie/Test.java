package algorithm.trie;

import com.google.common.collect.Maps;
import com.sm.audit.commons.utils.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * @author xinghonglin
 * @date 2020/11/11
 */
public class Test {

    //TODO 测试完成删除
    public static void main(String[] args) {
        /*AhoCorasickDoubleArrayTrie< List<String>> tries = new AhoCorasickDoubleArrayTrie<>();
        List<String> strings = Lists.newArrayList("汇川dsp", "品牌dsp");
        Map<String, List<String>> hashMap = Maps.newHashMap();
        hashMap.put("1",strings);
        List<AhoCorasickDoubleArrayTrie.Hit<List<String>>> hits = tries.parseText("汇川dsp和品牌dsp审核拒绝");
        if (CollectionUtils.isNotEmpty(hits)) {
            for (AhoCorasickDoubleArrayTrie.Hit<List<String>> hit : hits) {
                System.out.println(hit.value);
            }
        }*/

        AhoCorasickDoubleArrayTrie<String> trie = new AhoCorasickDoubleArrayTrie<>();
        Map<String, String> hashMap = Maps.newHashMap();
        hashMap.put("汇川dsp", "汇川dsp");
        hashMap.put("品牌dsp", "品牌dsp");
        trie.build(hashMap);

        List<AhoCorasickDoubleArrayTrie.Hit<String>> hits = trie.parseText("汇川dsp和品牌dsp审核拒绝");
        if (CollectionUtils.isNotEmpty(hits)) {
            for (AhoCorasickDoubleArrayTrie.Hit<String> hit : hits) {
                System.out.println(hit.value);
            }
        }
        String s = "胶";
        char[] chars = s.toCharArray();
        System.out.println(chars);
    }
}
