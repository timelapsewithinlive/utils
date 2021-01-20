/*
import function.DspIdeaSourceFunction;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.StreamTableEnvironment;

public class FlinkSqlDemo {

    public static void main(String[] args) throws Exception {

        //1.获取运行环境
        //EnvironmentSettings fsSettings = EnvironmentSettings.newInstance().useOldPlanner().inStreamingMode().build();
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        //1.1设置执行环境的并发度
        env.setParallelism(1);

        StreamTableEnvironment fsTableEnv = StreamTableEnvironment.create(fsEnv, fsSettings);


        DataStreamSource<String> dataStreamSource = env.addSource(new DspIdeaSourceFunction());

        dataStreamSource
    }
}
*/
