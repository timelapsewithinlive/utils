import com.alibaba.fastjson.JSON;
import org.apache.flink.api.common.functions.AggregateFunction;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.RichFilterFunction;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
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

        //获取运行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        //连接socket获取输入的数据
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

        //计算数据
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
        //针对相同的word数据进行分组
        windowCount.keyBy(DspIdea::getDspId)
                //指定计算数据的窗口大小和滑动窗口大小
                .timeWindow(Time.seconds(10))
                .trigger(new Trigger<DspIdea, TimeWindow>() {

                    @Override
                    public TriggerResult onElement(DspIdea dspIdeaDspTuple2, long l, TimeWindow timeWindow, TriggerContext triggerContext) throws Exception {
                        return TriggerResult.CONTINUE;
                    }

                    @Override
                    public TriggerResult onProcessingTime(long l, TimeWindow timeWindow, TriggerContext triggerContext) throws Exception {
                        return TriggerResult.CONTINUE;
                    }

                    @Override
                    public TriggerResult onEventTime(long l, TimeWindow timeWindow, TriggerContext triggerContext) throws Exception {
                        return TriggerResult.CONTINUE;
                    }

                    @Override
                    public void clear(TimeWindow timeWindow, TriggerContext triggerContext) throws Exception {

                    }
                })
                .aggregate(new AggregateFunction<DspIdea, Dsp, Object>() {
                               @Override
                               public Dsp createAccumulator() {
                                   return new Dsp();
                               }

                               @Override
                               public Dsp add(DspIdea value, Dsp accumulator) {
                                   // System.out.println("DspIdea:" + value.toString());
                                   //System.out.println("accumulator:" + accumulator.toString());

                                   accumulator.dspId = value.dspId;
                                   if (accumulator.entityIds == null) {
                                       accumulator.entityIds = new ArrayList<>();
                                   }
                                   accumulator.entityIds.add(value.entityId);
                                   return accumulator;
                               }

                               @Override
                               public Object getResult(Dsp accumulator) {
                                   return accumulator;
                               }

                               @Override
                               public Dsp merge(Dsp a, Dsp b) {
                                   a.entityIds.addAll(b.entityIds);
                                   return a;
                               }
                           }
                ).addSink(
                new RichSinkFunction<Object>() {
                    @Override
                    public void open(Configuration parameters) throws Exception {
                        System.out.println(parameters);
                    }

                    @Override
                    public void invoke(Object value, Context context) throws Exception {
                        System.out.println(value);
                    }

                    @Override
                    public void close() throws Exception {

                    }

                }).name("34231423").setParallelism(1);
        //注意：因为flink是懒加载的，所以必须调用execute方法，上面的代码才会执行
        env.execute("streaming dsp sample");
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
