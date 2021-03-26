package work;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author xinghonglin
 * @date 2021/01/14
 */
public class 分库分表索引生成器 {
    private final static String filePath = "/Applications/widea";

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
                    String winfo = String.format(
                            "create index idx_unitid_userid_logic_state on cpc_winfo_%s (unitid,userid,logic_state);\r\n", v, v);

//                   String idea = String.format(
//                            "create index idx_mod_tag_aid on audit_idea_%s (modify_day_tag,auditor_id);\r\n" +
//                                    "alter table audit_idea_%s drop index idx_mod_tag;\r\n", v, v);

                    String format = winfo;
                    //String format = winfo+idea;
                    System.out.println(format);
                    try {
                        fileOutputStream.write(format.getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                String create = String.format(
                        "create index idx_unitid_userid_logic_state on cpc_idea_pro_%s (unitid,userid,logic_state);\r\n", entry.getKey());

                //String delete = String.format("alter table audit_idea_pro_%s drop index idx_mod_tag;\n", entry.getKey());
                String ext = create;

                //String ext = create + delete;
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
