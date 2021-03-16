package jdk8.stream.nashorn;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author xinghonglin
 * @date 2021/03/16
 */
public class Test {
    private final static String filePath = "/Applications/word/word.txt";

    public static void main(String[] args) throws IOException {
        File file = new File(filePath);
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        for(int i=400001;i<500000;i++){
            String word = String.format(
                    "李某某是二傻子%s\n", i, i);
            System.out.println(word);
            fileOutputStream.write(word.getBytes());
        }

        fileOutputStream.flush();
        fileOutputStream.close();
    }

}
