import com.alibaba.fastjson.JSON;
import domain.Dsp;
import domain.DspIdea;
import function.DspIdeaAggegateFunction;
import function.DspIdeaEvitor;
import function.DspIdeaFilterFunction;
import function.DspIdeaSourceFunction;
import function.DspIdeaTrigger;
import function.DspRichReduceFunction;
import function.DspSinkBufferFunction;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.state.MapStateDescriptor;
import org.apache.flink.api.common.typeinfo.BasicTypeInfo;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.datastream.WindowedStream;
import org.apache.flink.streaming.api.environment.CheckpointConfig;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

/**
 * @author xinghonglin
 * @date 2020/12/01
 */
public class DspSampleTest {
    public DspSampleTest() {
    }

    public static void main(String[] args) throws Exception {

        //1.获取运行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        //1.1设置执行环境的并发度
        env.setParallelism(1);

        final MapStateDescriptor<String, String> CONFIG_DESCRIPTOR = new MapStateDescriptor<>(
                "dspConfig",
                BasicTypeInfo.STRING_TYPE_INFO,
                BasicTypeInfo.STRING_TYPE_INFO);
        //1.2广播dsp配置
        // BroadcastStream<DspConfig> broadcastStream = env.addSource(new DspConfigSourceFunction()).broadcast(CONFIG_DESCRIPTOR);

        DataStreamSource<String> text = env.addSource(new DspIdeaSourceFunction());
        // 开启 Checkpoint，每 1000毫秒进行一次 Checkpoint
        env.enableCheckpointing(1000);

// Checkpoint 语义设置为 EXACTLY_ONCE
        // 表示所有要消费的数据被恰好处理一次，即所有数据既不丢数据也不重复消费；ATLEASTONCE
        //表示要消费的数据至少处理一次，可能会重复消费。
        env.getCheckpointConfig().setCheckpointingMode(CheckpointingMode.EXACTLY_ONCE);

// CheckPoint 的超时时间
        // env.getCheckpointConfig().setCheckpointTimeout(60000);

// 同一时间，只允许 有 1 个 Checkpoint 在发生
        env.getCheckpointConfig().setMaxConcurrentCheckpoints(1);

// 两次 Checkpoint 之间的最小时间间隔为 500 毫秒
        // env.getCheckpointConfig().setMinPauseBetweenCheckpoints(500);

// 当 Flink 任务取消时，保留外部保存的 CheckPoint 信息
        env.getCheckpointConfig().enableExternalizedCheckpoints(CheckpointConfig.ExternalizedCheckpointCleanup.RETAIN_ON_CANCELLATION);

// 当有较新的 Savepoint 时，作业也会从 Checkpoint 处恢复
        //env.getCheckpointConfig().setPreferCheckpointForRecovery(true);

// 作业最多允许 Checkpoint 失败 1 次（flink 1.9 开始支持）
        // env.getCheckpointConfig().setTolerableCheckpointFailureNumber(1);

// Checkpoint 失败后，整个 Flink 任务也会失败（flink 1.9 之前）
        // env.getCheckpointConfig.setFailTasksOnCheckpointingErrors(true)

        //2.过滤数据
        DataStream<DspIdea> windowCount = text
                .flatMap(new FlatMapFunction<String, DspIdea>() {
                    @Override
                    public void flatMap(String value, Collector<DspIdea> out) throws Exception {
                        out.collect(JSON.parseObject(value, DspIdea.class));
                    }
                })
                .filter(new DspIdeaFilterFunction())
                .flatMap(new FlatMapFunction<DspIdea, DspIdea>() {
                    @Override
                    public void flatMap(DspIdea dspIdea, Collector<DspIdea> out) throws Exception {
                        out.collect(dspIdea);
                    }
                });
      /*  windowCount.connect(broadcastStream).process(new BroadcastProcessFunction() {
            @Override
            public void processElement(Object o, ReadOnlyContext readOnlyContext, Collector collector) throws Exception {
                System.out.println(o);
            }

            @Override
            public void processBroadcastElement(Object o, Context context, Collector collector) throws Exception {

            }
        });*/

        //3.定义窗口和触发器
        WindowedStream<DspIdea, Long, TimeWindow> windowedStream = windowCount.keyBy(DspIdea::getDspId)
                //指定计算数据的窗口大小和滑动窗口大小
                .timeWindow(Time.seconds(10000))
                //TriggerResult.FIRE_AND_PURGE 会重新执行聚合函数的createAccumulator
                //TriggerResult.FIRE ：复用第一次会重新执行聚合函数的createAccumulator的结果
                .trigger(new DspIdeaTrigger());
                //evictor 会重新执行聚合函数的createAccumulator
                //.evictor(new DspIdeaEvitor());
        //4.增量计算
        SingleOutputStreamOperator<Dsp> aggregate = windowedStream.aggregate(new DspIdeaAggegateFunction()).keyBy(Dsp::getDspId)
                .reduce(new DspRichReduceFunction());
        //5. 结果输出
        aggregate.addSink(new DspSinkBufferFunction());
        //注意：因为flink是懒加载的，所以必须调用execute方法，上面的代码才会执行
        env.execute("streaming dsp sample");
    }
}
