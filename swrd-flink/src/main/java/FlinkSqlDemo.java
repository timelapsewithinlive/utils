import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.java.StreamTableEnvironment;

public class FlinkSqlDemo {

    public static void main(String[] args) throws Exception {

        //1.获取运行环境
        EnvironmentSettings blinkSettings = EnvironmentSettings.newInstance().useBlinkPlanner().inStreamingMode().build();
        StreamExecutionEnvironment blinkEnv = StreamExecutionEnvironment.getExecutionEnvironment();

        //1.1设置执行环境的并发度
        blinkEnv.setParallelism(1);

        StreamTableEnvironment blinkTableEnv = StreamTableEnvironment.create(blinkEnv, blinkSettings);
        TableEnvironment tableEnvironment = TableEnvironment.create(blinkSettings);

        //tableEnvironment.connect(new DspCountSqlConnector("metaQ",1,false));

        Table table = tableEnvironment.sqlQuery("");

        //table.


    }
}
