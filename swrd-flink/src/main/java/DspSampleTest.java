import com.alibaba.fastjson.JSON;
import org.apache.flink.api.common.functions.AggregateFunction;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.RichFilterFunction;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.datastream.WindowedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.triggers.Trigger;
import org.apache.flink.streaming.api.windowing.triggers.TriggerResult;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author xinghonglin
 * @date 2020/12/01
 */
public class DspSampleTest {
    public static void main(String[] args) throws Exception {

        //1.获取运行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<String> text = env.addSource(new SourceFunction<String>() {
            private volatile boolean isRunning = true;
            private final Random random = new Random();

            @Override
            public void run(SourceContext<String> sourceContext) throws Exception {
                while (isRunning) {
                    TimeUnit.SECONDS.sleep(2);
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

        //3.定义窗口和触发器
        WindowedStream<DspIdea, Long, TimeWindow> windowedStream = windowCount.keyBy(DspIdea::getDspId)
                //指定计算数据的窗口大小和滑动窗口大小
                .timeWindow(Time.seconds(10))
                .trigger(new Trigger<DspIdea, TimeWindow>() {

                    @Override
                    public TriggerResult onElement(DspIdea dspIdeaDspTuple2, long l, TimeWindow timeWindow, TriggerContext triggerContext) throws Exception {
                        return TriggerResult.CONTINUE;
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
                });

        //4.增量计算
        SingleOutputStreamOperator<Dsp> aggregate = windowedStream.aggregate(
                new AggregateFunction<DspIdea, Dsp, Dsp>() {
                    @Override
                    public Dsp createAccumulator() {
                        return new Dsp();
                    }

                    @Override
                    public Dsp add(DspIdea value, Dsp accumulator) {
                        accumulator.dspId = value.dspId;
                        if (accumulator.entityIds == null) {
                            accumulator.entityIds = new ArrayList<>();
                        }
                        accumulator.entityIds.add(value.entityId);
                        //System.out.println("accumulator:" + accumulator.toString());
                        return accumulator;
                    }

                    @Override
                    public Dsp getResult(Dsp accumulator) {
                        return accumulator;
                    }

                    @Override
                    public Dsp merge(Dsp a, Dsp b) {
                        a.entityIds.addAll(b.entityIds);
                        return a;
                    }
                }
        );
        //5. 结果输出
        aggregate.addSink(
                 new RichSinkFunction<Dsp>() {

                     //连接资源
                     @Override
                     public void open(Configuration parameters) throws Exception {
                     }

                     @Override
                     public void invoke(Dsp value, Context context) throws Exception {
                         System.out.println("sink-------"+value);
                     }

                     //关闭资源、释放资源
                     @Override
                     public void close() throws Exception {
                        // System.out.println("closesssss");
                     }

                 })
                .setParallelism(1);
        //注意：因为flink是懒加载的，所以必须调用execute方法，上面的代码才会执行
        env.execute("streaming dsp sample");
    }


    public static class DspIdea {
        public Long dspId;
        public Long entityId;

        public DspIdea() {
        }

        public DspIdea(Long dspId, Long entityId) {
            this.dspId = dspId;
            this.entityId = entityId;
        }

        public Long getDspId() {
            return dspId;
        }

        public void setDspId(Long dspId) {
            this.dspId = dspId;
        }

        public Long getEntityId() {
            return entityId;
        }

        public void setEntityId(Long entityId) {
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

        @Override
        public String toString() {
            return "DspIdea{" +
                    "dspId='" + dspId + '\'' +
                    ", entityIds=" + JSON.toJSONString(entityIds) +
                    '}';
        }
    }
}
