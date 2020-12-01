import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.apache.flink.api.common.accumulators.AverageAccumulator;
import org.apache.flink.api.common.functions.*;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
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
        DataStream<Tuple2<DspIdea,Dsp>> windowCount = text
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
                .flatMap(new FlatMapFunction<DspIdea, Tuple2<DspIdea,Dsp>>() {
                    @Override
                    public void flatMap(DspIdea dspIdea, Collector<Tuple2<DspIdea,Dsp>> out) throws Exception {
                        Tuple2<DspIdea, Dsp> dspIdeaDspTuple2 = new Tuple2<>();
                        dspIdeaDspTuple2.f0 = dspIdea;
                        dspIdeaDspTuple2.f1 = new Dsp(dspIdea.dspId, Lists.newArrayList(dspIdea.entityId));
                        out.collect(dspIdeaDspTuple2);
                    }
                }).filter(new RichFilterFunction<Tuple2<DspIdea,Dsp>>() {
                    @Override
                    public boolean filter(Tuple2<DspIdea,Dsp> dspIdea) throws Exception {
                        // 满足总数小于300，写入数据库数据库
                        //if(value.count <300){
                        //insert mysql
                        // }
                        return true;
                    }
                });
                //针对相同的word数据进行分组
                windowCount.keyBy(new KeySelector<Tuple2<DspIdea, Dsp>, Object>() {

                    @Override
                    public Object getKey(Tuple2<DspIdea, Dsp> dspIdeaDspTuple2) throws Exception {
                        return "dspId";
                    }
                })
                //指定计算数据的窗口大小和滑动窗口大小
                .timeWindow(Time.seconds(10))
                .trigger(new Trigger<Tuple2<DspIdea, Dsp>, TimeWindow>() {
                    @Override
                    public TriggerResult onElement(Tuple2<DspIdea, Dsp> dspIdeaDspTuple2, long l, TimeWindow timeWindow, TriggerContext triggerContext) throws Exception {
                        return TriggerResult.FIRE;
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
                .aggregate(new AggregateFunction<Tuple2<DspIdea, Dsp>, Dsp, Object>() {
                               @Override
                               public Dsp createAccumulator() {
                                   return new Dsp();
                               }

                               @Override
                               public Dsp add(Tuple2<DspIdea, Dsp> value, Dsp accumulator) {
                                   accumulator.dspId = value.f1.dspId;
                                   accumulator.entityIds = new ArrayList<>();
                                   accumulator.entityIds.addAll(value.f1.entityIds);
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
                   );
               /* .reduce(new ReduceFunction<Tuple2<DspIdea,Dsp>>() {
                    @Override
                    public Tuple2<DspIdea, Dsp> reduce(Tuple2<DspIdea, Dsp> dspIdeaDspTuple2, Tuple2<DspIdea, Dsp> t1) throws Exception {
                        //System.out.println(JSON.toJSONString(t1));
                        if(t1!=null){
                            t1.f1.entityIds.add(dspIdeaDspTuple2.f0.entityId);
                        }
                        return t1;
                    }
                })*/;
        //把数据打印到控制台,使用一个并行度
        windowCount.print().setParallelism(1);
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

        @Override
        public String toString() {
            return "DspIdea{" +
                    "dspId='" + dspId + '\'' +
                    ", entityIds=" + JSON.toJSONString(entityIds) +
                    '}';
        }
    }
}
