package utils;

import com.google.common.collect.ImmutableSet;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

import static utils.TextUtils.convertToSimple;
import static utils.TextUtils.removeChars;
import static utils.TextUtils.sbc2dbc;

/**
 * 文本格式化<br/>
 * <ol>
 * <li>全角转半角</li>
 * <li>移除特殊字符</li>
 * <li>繁体转简体</li>
 * <li>大写转小写</li>
 * <li>
 * <ul>
 * <li>包含分割字符</li>
 * <li>不包含分割字符</li>
 * </ul>
 * </li>
 * </ol>
 *
 */
public class TextFormatter implements Function<String[], String[]> {

    /**
     * 特殊字符
     */
    public static Set<Character> RS_SET = ImmutableSet.copyOf(
        new Character[] {' ', '`', '#', '@', '$', '^', '*', '(', ')', '[', ']', '=', ';', '\'', ',',
            ':', '\"', '?', '【', '】', '。', '‘', '、', '《', '》', '…', '「', '」', '—', '“', '”', 'ˇ', '〔', '〕', '～', '″',
            '〃', '′'});

    /**
     * 分隔字符,一般是规则词表中包含的字符
     */
    public static Set<Character> RS_NEED_SPLIT_SET = ImmutableSet.copyOf(
        new Character[] {'\\', '|', '!', '-', '.', '&', '%', '/', '+', '_', '~', '>', '<'});

    @Override
    public String[] apply(String[] texts) {
        return format(texts);
    }

    /**
     * 对文本进行格式化,将生成不确定数量的文本列表
     *
     * @param texts
     * @return
     */
    public static String[] format(String... texts) {
        Set<String> stringSet = new HashSet<>();
        for (String string : texts) {
            format(string, stringSet);
        }
        return stringSet.toArray(new String[0]);
    }

    /**
     * 对文本自定义格式化,将生成不确定数量的文本列表
     *
     * @param texts
     * @return
     */
    public static String[] format(String[] texts, Function<String, Set<String>> function) {
        Set<String> stringSet = new HashSet<>();
        for (String string : texts) {
            stringSet.addAll(function.apply(string));
        }
        return stringSet.toArray(new String[0]);
    }

    /**
     * 对单个文本格式化
     *
     * @param text
     * @param formatted
     * @return
     */
    private static void format(String text, Set<String> formatted) {
        String replaced = convertToSimple(removeChars(sbc2dbc(text), RS_SET)).toLowerCase();
        formatted.add(replaced);
        formatted.add(removeChars(replaced, RS_NEED_SPLIT_SET));
    }

}
