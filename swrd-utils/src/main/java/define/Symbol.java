package define;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;

import java.util.regex.Pattern;

/**
 * 符号常量
 */
public final class Symbol {

    String NEW_LINE = "\n";

    String UNDERLINE = "_";

    String VERTICAL = "|";

    String DOUBLE_VERTICAL = "||";

    String COMMA = ",";

    String SEMICOLON = ";";

    String COLON = ":";

    String TAB = "\t";

    String SLASH = "/";

    String CONCAT = "-";

    Pattern URL = Pattern.compile(",\\s*((?=http)|$)");

    Splitter LINE_SPLITTER = Splitter.on(NEW_LINE).omitEmptyStrings().trimResults();

    Splitter SPLITTER = Splitter.on(COMMA).omitEmptyStrings().trimResults();

    Splitter UNDERLINE_SPLITTER = Splitter.on(UNDERLINE).omitEmptyStrings().trimResults();

    Splitter VERTICAL_SPLITTER = Splitter.on(VERTICAL).omitEmptyStrings().trimResults();

    Splitter DOUBLE_VERTICAL_SPLITTER = Splitter.on(DOUBLE_VERTICAL).omitEmptyStrings().trimResults();

    Splitter SEMI_SPLITTER = Splitter.on(SEMICOLON).omitEmptyStrings().trimResults();

    Splitter COLON_SPLITTER = Splitter.on(COLON).omitEmptyStrings().trimResults();

    Splitter TAB_SPLITTER = Splitter.on(TAB).omitEmptyStrings().trimResults();

    Splitter SLASH_SPLITTER = Splitter.on(SLASH).omitEmptyStrings().trimResults();

    Splitter URL_SPLITTER = Splitter.on(URL).omitEmptyStrings().trimResults();

    Joiner LINE_JOINER = Joiner.on(NEW_LINE).skipNulls();

    Joiner JOINER = Joiner.on(COMMA).skipNulls();

    Joiner UNDERLINE_JOINER = Joiner.on(UNDERLINE).skipNulls();

    Joiner VERTICAL_JOINER = Joiner.on(VERTICAL).skipNulls();

    Joiner DOUBLE_VERTICAL_JOINER = Joiner.on(DOUBLE_VERTICAL).skipNulls();

    Joiner SEMI_JOINER = Joiner.on(SEMICOLON).skipNulls();

    Joiner COLON_JOINER = Joiner.on(COLON).skipNulls();

    Joiner TAB_JOINER = Joiner.on(TAB).skipNulls();

    Joiner SLASH_JOINER = Joiner.on(SLASH).skipNulls();

    Joiner CONCAT_JOINER = Joiner.on(CONCAT).skipNulls();

}
