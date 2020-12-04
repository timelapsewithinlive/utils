package function;

import domain.DspIdea;
import org.apache.flink.api.common.functions.RichFilterFunction;
import org.apache.flink.runtime.state.FunctionInitializationContext;
import org.apache.flink.runtime.state.FunctionSnapshotContext;
import org.apache.flink.streaming.api.checkpoint.CheckpointedFunction;

public class DspIdeaFilterFunction extends RichFilterFunction<DspIdea> {

    @Override
    public boolean filter(DspIdea dspIdea) throws Exception {
        //根据配置过滤物料
        //System.out.println("filter: "+Thread.currentThread().getId());
        return true;
    }
}
