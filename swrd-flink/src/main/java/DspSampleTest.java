import com.alibaba.fastjson.JSON;
import domain.Dsp;
import domain.DspIdea;
import function.DspIdeaAggegateFunction;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.RichFilterFunction;
import org.apache.flink.api.common.state.MapStateDescriptor;
import org.apache.flink.api.common.typeinfo.BasicTypeInfo;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.datastream.WindowedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.flink.streaming.api.windowing.evictors.Evictor;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.triggers.Trigger;
import org.apache.flink.streaming.api.windowing.triggers.TriggerResult;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.streaming.runtime.operators.windowing.TimestampedValue;
import org.apache.flink.util.Collector;

import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.TimeUnit;

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
        env.setParallelism(4);

        final MapStateDescriptor<String, String> CONFIG_DESCRIPTOR = new MapStateDescriptor<>(
                "dspConfig",
                BasicTypeInfo.STRING_TYPE_INFO,
                BasicTypeInfo.STRING_TYPE_INFO);
        //1.2广播dsp配置
        // BroadcastStream<DspConfig> broadcastStream = env.addSource(new DspConfigSourceFunction()).broadcast(CONFIG_DESCRIPTOR);

        DataStreamSource<String> text = env.addSource(new SourceFunction<String>() {
            private volatile boolean isRunning = true;
            private final Random random = new Random();

            @Override
            public void run(SourceContext<String> sourceContext) throws Exception {
                while (isRunning) {
                    TimeUnit.MILLISECONDS.sleep(2000);
                    DspIdea dspIdea = new DspIdea(1L, random.nextLong());
                    sourceContext.collect(JSON.toJSONString(dspIdea));
                }
            }

            @Override
            public void cancel() {
                isRunning = false;
            }
        });

        //2.过滤数据
        DataStream<DspIdea> windowCount = text
                .flatMap(new FlatMapFunction<String, DspIdea>() {
                    @Override
                    public void flatMap(String value, Collector<DspIdea> out) throws Exception {
                        out.collect(JSON.parseObject(value, DspIdea.class));
                    }
                })
                .filter(new RichFilterFunction<DspIdea>() {
                    @Override
                    public boolean filter(DspIdea dspIdea) throws Exception {
                        //根据配置过滤物料
                        return true;
                    }
                })
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
                .timeWindow(Time.seconds(1000))
                .trigger(new Trigger<DspIdea, TimeWindow>() {
                    //TriggerResult.FIRE_AND_PURGE 会重新执行聚合函数的createAccumulator
                    //TriggerResult.FIRE ：复用第一次会重新执行聚合函数的createAccumulator的结果
                    @Override
                    public TriggerResult onElement(DspIdea dspIdeaDspTuple2, long l, TimeWindow timeWindow, TriggerContext triggerContext) throws Exception {
                        return TriggerResult.FIRE;
                    }

                    @Override
                    public TriggerResult onProcessingTime(long l, TimeWindow timeWindow, TriggerContext triggerContext) throws Exception {
                        return TriggerResult.FIRE_AND_PURGE;
                    }

                    @Override
                    public TriggerResult onEventTime(long l, TimeWindow timeWindow, TriggerContext triggerContext) throws Exception {
                        return TriggerResult.CONTINUE;
                    }

                    @Override
                    public void clear(TimeWindow timeWindow, TriggerContext triggerContext) throws Exception {

                    }
                })
                //evictor 会重新执行聚合函数的createAccumulator
                .evictor(new Evictor<DspIdea, TimeWindow>() {
                    @Override
                    public void evictBefore(Iterable<TimestampedValue<DspIdea>> iterable, int size, TimeWindow timeWindow, EvictorContext evictorContext) {
                        //do nothing
                    }

                    @Override
                    public void evictAfter(Iterable<TimestampedValue<DspIdea>> iterable, int i, TimeWindow timeWindow, EvictorContext evictorContext) {
                        Iterator<TimestampedValue<DspIdea>> iterator = iterable.iterator();
                        while (iterator.hasNext()) {
                            TimestampedValue<DspIdea> next = iterator.next();
                            iterator.remove();
                        }
                    }
                });
        //4.增量计算
        SingleOutputStreamOperator<Dsp> aggregate = windowedStream.aggregate(
                new DspIdeaAggegateFunction());
        //5. 结果输出
        aggregate.addSink(
                new RichSinkFunction<Dsp>() {

                    //连接资源
                    @Override
                    public void open(Configuration parameters) throws Exception {
                    }

                    @Override
                    public void invoke(Dsp value, Context context) throws Exception {
                        System.out.println("sink-------" + value);
                        //  value.dspIdeas.clear();
                    }

                    //关闭资源、释放资源
                    @Override
                    public void close() throws Exception {
                        // System.out.println("closesssss");
                    }

                });
        //注意：因为flink是懒加载的，所以必须调用execute方法，上面的代码才会执行
        env.execute("streaming dsp sample");
    }
}
