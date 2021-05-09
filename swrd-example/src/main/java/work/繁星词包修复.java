package work;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

/**
 * @author xinghonglin
 * @date 2021/03/26
 */
public class 繁星词包修复 {

    private final static String filePath = "/Applications/work-doc/word.txt";
    static String NEW_LINE = "\n";
    static Splitter LINE_SPLITTER = Splitter.on(NEW_LINE).omitEmptyStrings().trimResults();
    static Joiner LINE_Joiner = Joiner.on(NEW_LINE).skipNulls();


    public static void main(String[] args) throws IOException {

        File rejectInput = new File("/Applications/work-doc/人工干预 舒脉.txt");
        FileReader rejectFileReader = new FileReader(rejectInput);
        String rejectWords = IOUtils.toString(rejectFileReader);
        List<String> rejectWordReasons = LINE_SPLITTER.splitToList(rejectWords);
        List<String> rejectWordList = Lists.newArrayList();
        for (String reason : rejectWordReasons) {
            Pair<String, String> pair = splitToPair(reason, "|");
            rejectWordList.add(pair.getLeft());
        }

        File passInput = new File("/Users/xinghonglin/Downloads/a7919d14cd753957b75bb4b82d011b0d_f101635f.txt");
        FileReader passFileReader = new FileReader(passInput);
        String passtWords = IOUtils.toString(passFileReader);
        List<String> passWordList = LINE_SPLITTER.splitToList(passtWords);
        System.out.println(passWordList.size());


        Collection<String> resultList = CollectionUtils.subtract(passWordList, rejectWordList);
        System.out.println(resultList.size());
        String resultWords = LINE_Joiner.join(resultList);

        File result = new File("/Applications/work-doc/word.txt");
        FileOutputStream fileOutputStream = new FileOutputStream(result);

        fileOutputStream.write(resultWords.getBytes());
        fileOutputStream.flush();
        fileOutputStream.close();
    }

    private static Pair<String, String> splitToPair(String word, String regex) {
        return Pair.of(StringUtils.substringBeforeLast(word, regex), StringUtils.substringAfterLast(word, regex));
    }

}
