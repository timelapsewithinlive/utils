package emoji;

import com.vdurmont.emoji.EmojiParser;

public class TestEmoji {

    public static void main(String[] args){
        String str="\uD83D\uDE09";
        System.out.println(EmojiParser.parseToAliases(str));
        System.out.println(EmojiParser.parseToAliases(str, EmojiParser.FitzpatrickAction.PARSE));
        System.out.println(EmojiParser.parseToAliases(str, EmojiParser.FitzpatrickAction.REMOVE));
        System.out.println(EmojiParser.parseToAliases(str, EmojiParser.FitzpatrickAction.IGNORE));

    }

}
