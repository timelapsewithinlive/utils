package function;

import domain.Dsp;
import org.apache.flink.api.common.functions.RichReduceFunction;
import org.apache.flink.api.common.state.ListState;
import org.apache.flink.api.common.state.ListStateDescriptor;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.runtime.state.FunctionInitializationContext;
import org.apache.flink.runtime.state.FunctionSnapshotContext;
import org.apache.flink.streaming.api.checkpoint.CheckpointedFunction;

import java.util.Map;

/**
 * @author xinghonglin
 * @date 2020/12/04
 */
public class DspRichReduceFunction extends RichReduceFunction<Dsp> implements CheckpointedFunction {

    Map<String, Integer> dspCount = null;
    private ListState<Map<String, Integer>> checkpointedState;

    @Override
    public Dsp reduce(Dsp newDsp, Dsp oldDsp) throws Exception {
        return oldDsp;
    }

    @Override
    public void snapshotState(FunctionSnapshotContext functionSnapshotContext) throws Exception {

    }

    @Override
    public void initializeState(FunctionInitializationContext context) throws Exception {
        ListStateDescriptor<Map<String, Integer>> descriptor = new ListStateDescriptor("fsdafsd", TypeInformation.of(new TypeHint<Dsp>() {
        }));
        checkpointedState = context.getOperatorStateStore().getListState(descriptor);

        if (context.isRestored()) {
            Iterable<Map<String, Integer>> dsps = checkpointedState.get();
            dspCount = dsps.iterator().next();
            //清除前一天的数量统计
        }
    }

}
