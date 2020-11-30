import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.Partitioner;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.java.tuple.Tuple1;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSink;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.SocketClientSink;

/**
 * @author xinghonglin
 * @date 2020/11/29
 */
public class StreamPartitionTest {

    public static void main(String[] args) throws Exception {
        //定义socket的端口号
        int port;
        try {
            ParameterTool parameterTool = ParameterTool.fromArgs(args);
            port = parameterTool.getInt("port");
        } catch (Exception e) {
            System.err.println("没有指定port参数，使用默认值9000");
            port = 9000;
        }
        //获取运行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<String> text = env.socketTextStream("127.0.0.1", port, "\n");
        //连接socket获取输入的数据
        DataStream<Tuple1<Long>> tupData = text.map(new MapFunction<String, Tuple1<Long>>() {
            @Override
            public Tuple1<Long> map(String aLong) throws Exception {
                return new Tuple1<Long>(Long.valueOf(aLong));
            }
        });
        //分区之后的数据
        DataStream<Tuple1<Long>> partData = tupData.partitionCustom(new MyPartition(), 0);
        SingleOutputStreamOperator<Long> maps = partData.map(new MapFunction<Tuple1<Long>, Long>() {
            @Override
            public Long map(Tuple1<Long> value) throws Exception {
                //System.out.println("获取当前线程id" + Thread.currentThread().getId() + ",value" + value);
                return value.getField(0);
            }
        });
        maps.print().setParallelism(1);

        DataStreamSink dataStreamSink = maps.addSink(new SocketClientSink("127.0.0.1", 9000, new SimpleStringSchema()));
        env.execute("own definite partiotn");

    }

    static class MyPartition implements Partitioner<Long> {

        @Override
        public int partition(Long key, int numPartitions) {
            //System.out.println("分区总数" + numPartitions);
            if (key % 2 == 0) {
                return 0;
            } else {
                return 1;
            }
        }
    }
}
