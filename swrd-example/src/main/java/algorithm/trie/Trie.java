package algorithm.trie;

import com.hankcs.hanlp.collection.trie.DoubleArrayTrie;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author xinghonglin
 * @date 2020/11/10
 */
public class Trie {
    public static void main(String[] args) throws IOException
    {
        BufferedReader reader = new BufferedReader(new FileReader("/Applications/work-code/utils/swrd-example/src/main/java/small.dic"));
        String line;
        List<String> words = new ArrayList<String>();
        //码表集合
        Set<Character> charset = new HashSet<Character>();
        while ((line = reader.readLine()) != null)
        {
            words.add(line);
            // 制作一份码表debug
            for (char c : line.toCharArray())
            {
                charset.add(c);
            }
        }
        reader.close();
        // 这个字典如果要加入新词必须按字典序，参考下面的代码
//        Collections.sort(words);
//        BufferedWriter writer = new BufferedWriter(new FileWriter("./data/sorted.dic", false));
//        for (String w : words)
//        {
//            writer.write(w);
//            writer.newLine();
//        }
        System.out.println("字典词条：" + words.size());

        {
            String infoCharsetValue = "";
            String infoCharsetCode = "";
            for (Character c : charset)
            {
                infoCharsetValue += c.charValue() + "    ";
                infoCharsetCode += (int)c.charValue() + " ";
            }
            infoCharsetValue += '\n';
            infoCharsetCode += '\n';
            System.out.print(infoCharsetValue);
            System.out.print(infoCharsetCode);
        }

        MyDoubleArrayTrie dat = new MyDoubleArrayTrie();
        System.out.println("是否错误: " + dat.build(words,new ArrayList()));
        System.out.println(dat);
        List<Integer> integerList = dat.commonPrefixSearch("一举成名天下知");
        for (int index : integerList)
        {
            System.out.println(words.get(index));
        }
    }
}
