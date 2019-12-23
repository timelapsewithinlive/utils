package jdk.string;

import org.springframework.util.StringUtils;

public class TestTrim {


    public static void main(String[] args){
        String fsa="邢宏  林";
        String s = StringUtils.trimAllWhitespace(fsa.trim());
        System.out.println(s);
    }
}
