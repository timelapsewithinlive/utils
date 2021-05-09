package utils;

import com.hankcs.hanlp.collection.AhoCorasick.AhoCorasickDoubleArrayTrie;
import com.hankcs.hanlp.corpus.dictionary.StringDictionary;
import com.hankcs.hanlp.corpus.io.ByteArray;
import com.hankcs.hanlp.corpus.io.IOUtil;
import com.hankcs.hanlp.dictionary.ts.BaseChineseDictionary;
import com.hankcs.hanlp.utility.Predefine;
import lombok.extern.slf4j.Slf4j;

import java.io.DataOutputStream;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * 繁简词典，提供简繁转换
 * <p>
 * {@link com.hankcs.hanlp.dictionary.ts.TraditionalChineseDictionary}
 *
 * @author hankcs
 */
@Slf4j
public class TraditionalChineseDictionary extends BaseChineseDictionary {

    private static String tcDictionaryRoot = "data/dictionary/tc/custom/";

    private static String T2S_FILE_NAME = System.getProperty("t2s.file.name", "t2s.txt");

    /**
     * 繁体=简体
     */
    private static AhoCorasickDoubleArrayTrie<String> trie = new AhoCorasickDoubleArrayTrie<String>();

    static {
        long start = System.currentTimeMillis();
        if (!load(tcDictionaryRoot + T2S_FILE_NAME, trie, false)) {
            throw new IllegalArgumentException("繁简词典" + tcDictionaryRoot + T2S_FILE_NAME + "加载失败");
        }
        log.info("繁简词典" + tcDictionaryRoot + T2S_FILE_NAME + "加载成功，耗时" + (System.currentTimeMillis() - start) + "ms");
    }

    public static String convertToSimplifiedChinese(String traditionalChineseString) {
        return segLongest(traditionalChineseString.toCharArray(), trie);
    }

    public static String convertToSimplifiedChinese(char[] traditionalChinese) {
        return segLongest(traditionalChinese, trie);
    }

    /**
     * 读取词典
     *
     * @param path
     * @param trie
     * @param reverse 是否将其翻转
     * @return
     */
    static boolean load(String path, AhoCorasickDoubleArrayTrie<String> trie, boolean reverse) {
        String datPath = path;
        if (reverse) {
            datPath += Predefine.REVERSE_EXT;
        }
        boolean notResource = !IOUtil.isResource(datPath);
        if (notResource && loadDat(datPath, trie)) { return true; }
        // 从文本中载入并且尝试生成dat
        TreeMap<String, String> map = new TreeMap<String, String>();
        if (!load(map, reverse, path)) { return false; }
        log.debug("正在构建AhoCorasickDoubleArrayTrie，来源：" + path);
        trie.build(map);
        log.debug("正在缓存双数组" + datPath);
        if (notResource) { saveDat(datPath, trie, map.entrySet()); }
        return true;
    }

    /**
     * 读取词典
     *
     * @param storage   储存空间
     * @param reverse   是否翻转键值对
     * @param pathArray 路径
     * @return 是否加载成功
     */
    static boolean load(Map<String, String> storage, boolean reverse, String... pathArray) {
        StringDictionary dictionary = new StringDictionary("=");
        for (String path : pathArray) {
            if (!dictionary.load(path)) { return false; }
        }
        if (reverse) { dictionary = dictionary.reverse(); }
        Set<Map.Entry<String, String>> entrySet = dictionary.entrySet();
        for (Map.Entry<String, String> entry : entrySet) {
            storage.put(entry.getKey(), entry.getValue());
        }

        return true;
    }

    static boolean loadDat(String path, AhoCorasickDoubleArrayTrie<String> trie) {
        if (IOUtil.isResource(path)) { return false; }
        ByteArray byteArray = ByteArray.createByteArray(path + Predefine.BIN_EXT);
        if (byteArray == null) { return false; }
        int size = byteArray.nextInt();
        String[] valueArray = new String[size];
        for (int i = 0; i < valueArray.length; ++i) {
            valueArray[i] = byteArray.nextString();
        }
        trie.load(byteArray, valueArray);
        return true;
    }

    static boolean saveDat(String path, AhoCorasickDoubleArrayTrie<String> trie,
        Set<Map.Entry<String, String>> entrySet) {
        path = path + Predefine.BIN_EXT;
        // 忽略掉
        if (IOUtil.isResource(path)) {
            return true;
        }
        if (trie.size() != entrySet.size()) {
            log.warn("键值对不匹配");
            return false;
        }
        try {
            DataOutputStream out = new DataOutputStream(IOUtil.newOutputStream(path));
            out.writeInt(entrySet.size());
            for (Map.Entry<String, String> entry : entrySet) {
                char[] charArray = entry.getValue().toCharArray();
                out.writeInt(charArray.length);
                for (char c : charArray) {
                    out.writeChar(c);
                }
            }
            trie.save(out);
            out.close();
        } catch (Exception e) {
            log.warn("缓存值dat" + path + "失败");
            return false;
        }
        return true;
    }

}
