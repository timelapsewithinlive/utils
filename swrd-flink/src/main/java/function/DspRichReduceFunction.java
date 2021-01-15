package function;

import domain.Dsp;
import org.apache.flink.api.common.functions.RichReduceFunction;
import org.apache.flink.api.common.state.ListState;
import org.apache.flink.api.common.state.ListStateDescriptor;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.runtime.state.FunctionInitializationContext;
import org.apache.flink.runtime.state.FunctionSnapshotContext;
import org.apache.flink.streaming.api.checkpoint.CheckpointedFunction;

import java.util.Map;

/**
 * @author xinghonglin
 * @date 2020/12/04
 */
public class DspRichReduceFunction extends RichReduceFunction<Dsp> implements CheckpointedFunction {

    //https://blog.csdn.net/qq_33689414/article/details/94447569  Flink DataStream Manager(托管) Keyed State的简单使用

    Map<String, Integer> dspCount = null;
    private ListState<Map<String, Integer>> checkpointedState;

    private ValueState<Tuple2<Dsp, Dsp>> state;

    @Override
    public void open(Configuration parameters) throws Exception{
        super.open(parameters);
        ValueStateDescriptor<Tuple2<Dsp, Dsp>> descriptor = new ValueStateDescriptor<Tuple2<Dsp, Dsp>>("", TypeInformation.of(new TypeHint<Tuple2<Dsp, Dsp>>() {
        }), Tuple2.of(null, null));

        state = getRuntimeContext().getState(descriptor);

        System.out.println(state);
    }

    @Override
    public Dsp reduce(Dsp newDsp, Dsp oldDsp) throws Exception {
        System.out.println(newDsp);
        Tuple2<Dsp, Dsp> of = Tuple2.of(newDsp, oldDsp);
        state.update(of);
        return oldDsp;
    }

    @Override
    public void snapshotState(FunctionSnapshotContext functionSnapshotContext) throws Exception {
        //System.out.println(functionSnapshotContext);
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
