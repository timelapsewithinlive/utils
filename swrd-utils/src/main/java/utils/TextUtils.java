package utils;

import com.google.common.collect.ImmutableSet;
import com.hankcs.hanlp.HanLP;
import org.apache.commons.lang3.StringUtils;

import java.util.Set;

/**
 * 文本处理工具类
 *
 */
public class TextUtils {

    static final char SBC_SPACE = 12288; // 全角空格 12288

    static final char DBC_SPACE = 32; //半角空格 32

    static final char ASCII_END = 126;

    static final char UNICODE_START = 65281;

    static final char UNICODE_END = 65374;

    static final char DBC_SBC_STEP = 65248; // 全角半角转换间隔

    /**
     * 忽略字符
     */
    public static Set<Character> IGNORE_CHARS = ImmutableSet.copyOf(
            new Character[]{'`', '!', '#', '@', '$', '^', '*', '(', ')', '[', ']', '\\', '|', '=', ';', '\'', ',', ':',
                    '\"', '?', '【', '】', '。', '‘', '、', '《', '》', '…', '「', '」', '“', '”', 'ˇ', '〔', '〕', '～', '″', '〃',
                    '′'});


    /**
     * 规范化字符串,包括<p/> 1.全角转半角,2.繁->简,3、大写转小写,4,空格去重
     *
     * @param src
     * @return
     */
    public static String normalize(String src) {
        return toLowerCase(convertToSimple(sbc2dbc(src))).replaceAll("(\\s)+", "$1");
    }

    /**
     * 格式化字符串,包括<p/> 1.全角转半角,2.繁->简,3、大写转小写,4,删除特殊符号
     *
     * @param src
     * @return
     */
    public static String format(String src, boolean keepSpace) {
        return removeIgnoreChars(toLowerCase(convertToSimple(sbc2dbc(src)))).replaceAll("(\\s)+", keepSpace ? "$1" : "");
    }

    /**
     * 繁体转简体
     *
     * @param src
     * @return
     */
    public static String convertToSimple(String src) {
        return TraditionalChineseDictionary.convertToSimplifiedChinese(src);
    }

    /**
     * 简体转繁体
     *
     * @param src
     * @return
     */
    public static String convertToTraditional(String src) {
        return HanLP.convertToTraditionalChinese(src);
    }

    /**
     * 大写转小写
     *
     * @param src
     * @return
     */
    public static String toLowerCase(String src) {
        return src.toLowerCase();
    }

    /**
     * 全角转半角
     *
     * @param src
     * @return
     */
    public static String sbc2dbc(String src) {
        if (src == null) {
            return null;
        }
        char[] c = src.toCharArray();
        for (int i = 0; i < c.length; i++) {
            c[i] = sbc2dbc(c[i]);
        }
        return new String(c);
    }

    /**
     * 半角转全角
     *
     * @param src
     * @return
     */
    public static String dbc2sbc(String src) {
        if (src == null) {
            return null;
        }
        char[] c = src.toCharArray();
        for (int i = 0; i < c.length; i++) {
            c[i] = dbc2sbc(c[i]);
        }
        return new String(c);
    }

    /**
     * 全角转半角
     *
     * @param src
     * @return
     */
    private static char sbc2dbc(char src) {
        if (src == SBC_SPACE) {
            return DBC_SPACE;
        }
        if (src >= UNICODE_START && src <= UNICODE_END) {
            return (char) (src - DBC_SBC_STEP);
        }
        return src;
    }

    /**
     * 半角转全角
     *
     * @param src
     * @return
     */
    private static char dbc2sbc(char src) {
        if (src == DBC_SPACE) {
            return SBC_SPACE;
        }
        if (src <= ASCII_END) {
            return (char) (src + DBC_SBC_STEP);
        }
        return src;
    }

    /**
     * 删除忽略字符
     *
     * @param src
     * @return
     */
    private static String removeIgnoreChars(String src) {
        return removeChars(src, IGNORE_CHARS);
    }

    /**
     * 移除指定字符
     *
     * @param src
     * @param specialChars
     * @return
     */
    public static String removeChars(String src, Set<Character> specialChars) {
        if (StringUtils.isEmpty(src) || CollectionUtils.isEmpty(specialChars)) {
            return src;
        }
        char[] wordChars = src.toCharArray();
        char[] rmChars = new char[wordChars.length];
        int i = 0;
        for (char aChar : wordChars) {
            if (specialChars.contains(aChar)) {
                continue;
            }
            rmChars[i] = aChar;
            i++;
        }
        return new String(rmChars, 0, i);
    }

}



