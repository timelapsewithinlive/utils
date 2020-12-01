import com.alibaba.fastjson.JSON;
import org.apache.flink.api.common.functions.AggregateFunction;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.RichFilterFunction;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;

import java.util.List;

/**
 * @author xinghonglin
 * @date 2020/12/01
 */
public class DspSampleTest {
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
        //连接socket获取输入的数据
        DataStreamSource<String> text = env.socketTextStream("127.0.0.1", port, "\n");
        //计算数据
        DataStream<DspIdea> windowCount = text
                .filter(new RichFilterFunction<String>() {
                    @Override
                    public boolean filter(String value) throws Exception {
                        //根据配置过滤物料
                        return true;
                    }
                })
                .flatMap(new FlatMapFunction<String, DspIdea>() {
                    @Override
                    public void flatMap(String value, Collector<DspIdea> out) throws Exception {
                        out.collect(JSON.parseObject(value, DspIdea.class));
                    }
                }).filter(new RichFilterFunction<DspIdea>() {
                    @Override
                    public boolean filter(DspIdea dspIdea) throws Exception {
                        // 满足总数小于300，写入数据库数据库
                        //if(value.count <300){
                        //insert mysql
                        // }
                        return true;
                    }
                })
                //针对相同的word数据进行分组
                .keyBy("dspId")
                //指定计算数据的窗口大小和滑动窗口大小
                .timeWindow(Time.seconds(10))
                .trigger(null)
                .aggregate(new AggregateFunction<DspIdea, Object, DspIdea>() {
                    @Override
                    public Object createAccumulator() {
                        return null;
                    }

                    @Override
                    public Object add(DspIdea dspIdea, Object o) {
                        return null;
                    }

                    @Override
                    public DspIdea getResult(Object o) {
                        return null;
                    }

                    @Override
                    public Object merge(Object o, Object acc1) {
                        return null;
                    }
                });
        //把数据打印到控制台,使用一个并行度
        windowCount.print().setParallelism(1);
        windowCount.transform()
        //注意：因为flink是懒加载的，所以必须调用execute方法，上面的代码才会执行
        env.execute("streaming word count");
    }

    /**
     * 主要为了存储单词以及单词出现的次数
     */
    public static class DspIdea {
        public Long dspId;
        public Long entityId;

        public DspIdea() {
        }

        public DspIdea(Long dspId, Long entityId) {
            this.dspId = dspId;
            this.entityId = entityId;
        }

        @Override
        public String toString() {
            return "DspIdea{" +
                    "dspId='" + dspId + '\'' +
                    ", entityId=" + entityId +
                    '}';
        }
    }

    public static class Dsp {
        public Long dspId;
        public List<Long> entityIds;

        public Dsp() {
        }

        public Dsp(Long dspId, List<Long> entityIds) {
            this.dspId = dspId;
            this.entityIds = entityIds;
        }
    }
}
