package db;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @Author: lhx249322
 * @Date: 2020/8/13
 */
public class Test {

    private final static String filePath = "/Applications/work-doc";

    public static void main(String[] args) throws Exception {
        // 4*32
        Map<String, List<String>> map = IntStream.range(0, 1024).boxed().collect(
            Collectors.groupingBy(i -> String.format("%04d", i % 8),
                Collectors.mapping(i -> String.format("%04d", i), Collectors.toList())));
        map.entrySet().forEach(entry -> {
            File file = new File(filePath + String.format("audit_%s.sql", entry.getKey()));
            System.out.println(String.format("==========audit_%s============", entry.getKey()));
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                entry.getValue().forEach(v -> {
                    String format = String.format(
                        "alter table cpc_winfo_%s drop index INDEX_userid_logic_review_state,drop index INDEX_userid_unitid_logic_state_review_state,drop index INDEX_review_state_logic_state_unitid;\n"+
                        "alter table cpc_idea_%s drop index INDEX_userid_logic_review_state,drop index INDEX_review_state_logic_state_unitid,drop index INDEX_userid_unitid_logic_state_review_state;\n", v, v);
                    System.out.println(format);
                    try {
                        fileOutputStream.write(format.getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                String ext = String.format(
                    "alter table cpc_idea_pro_%s drop index INDEX_review_state_logic_state_unitid,drop index INDEX_userid_logic_state_review_state,drop index INDEX_userid_unitid_logic_state_review_state;\n", entry.getKey());
                fileOutputStream.write(ext.getBytes());
                fileOutputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }
}
