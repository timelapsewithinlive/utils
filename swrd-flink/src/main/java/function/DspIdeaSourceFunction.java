package function;

import com.alibaba.fastjson.JSON;
import domain.Dsp;
import domain.DspIdea;
import org.apache.flink.api.common.state.MapStateDescriptor;
import org.apache.flink.api.common.typeinfo.BasicTypeInfo;
import org.apache.flink.runtime.state.FunctionInitializationContext;
import org.apache.flink.runtime.state.FunctionSnapshotContext;
import org.apache.flink.streaming.api.checkpoint.CheckpointedFunction;
import org.apache.flink.streaming.api.functions.source.SourceFunction;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author xinghonglin
 * @date 2020/12/03
 */
public class DspIdeaSourceFunction implements SourceFunction<String>, CheckpointedFunction {



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

    @Override
    public void snapshotState(FunctionSnapshotContext functionSnapshotContext) throws Exception {

    }

    @Override
    public void initializeState(FunctionInitializationContext context) throws Exception {

    }
}
