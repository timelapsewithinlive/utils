package jdk8.stream.nashorn;

import com.google.common.base.Splitter;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author xinghonglin
 * @date 2021/03/16
 */
public class Test3 {

    private static final Splitter.MapSplitter MAP_SPLITTER = Splitter.on("\n").withKeyValueSeparator("|");
    private static final Splitter s = Splitter.on("\n");


    public static void main(String[] args) {

       String word = "北京糖果展|竞品词[京糖]\n";
        word += "中国国际渔业博览会";

        List<String> words = s.splitToList(word);
        System.out.println(words);

        List<String> reasonWords = words.stream().filter(w -> w.contains("|")).collect(Collectors.toList());

        Collection<String> noReasonWords = CollectionUtils.subtract(words, reasonWords);

        System.out.println(reasonWords);
        System.out.println(noReasonWords);

        //Map<String, String> split = MAP_SPLITTER.split(word);
        //System.out.println(split);
    }

}
