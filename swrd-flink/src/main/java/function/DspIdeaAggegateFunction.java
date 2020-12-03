package function;


import domain.Dsp;
import domain.DspIdea;
import org.apache.commons.collections.CollectionUtils;
import org.apache.flink.api.common.functions.AggregateFunction;
import org.apache.flink.api.common.state.ListState;
import org.apache.flink.api.common.state.ListStateDescriptor;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.runtime.state.FunctionInitializationContext;
import org.apache.flink.runtime.state.FunctionSnapshotContext;
import org.apache.flink.streaming.api.checkpoint.CheckpointedFunction;

import java.util.ArrayList;

/**
 * @author xinghonglin
 * @date 2020/12/03
 */
public class DspIdeaAggegateFunction implements AggregateFunction<DspIdea, Dsp, Dsp>, CheckpointedFunction {

    Dsp accumulator = null;
    private ListState<Dsp> checkpointedState;

    public DspIdeaAggegateFunction() {
        System.out.println("构造新的聚合函数");
        accumulator = new Dsp();
    }

    @Override
    public Dsp createAccumulator() {
        System.out.println("DspIdeaAggegateFunction  createAccumulator tread: "+Thread.currentThread());
        if (CollectionUtils.isNotEmpty(accumulator.dspIdeas)) {
            accumulator.dspIdeas.clear();
        }
        return accumulator;
    }

    @Override
    public Dsp add(DspIdea value, Dsp accumulator) {
        System.out.println("DspIdeaAggegateFunction add tread: "+Thread.currentThread());
        accumulator.dspId = value.dspId;
        if (accumulator.entityIds == null) {
            accumulator.entityIds = new ArrayList<>();
        }
        if (accumulator.dspIdeas == null) {
            accumulator.dspIdeas = new ArrayList<>();
        }
        accumulator.count += 1;
        //accumulator.entityIds.add(value.entityId);
        accumulator.dspIdeas.add(value);
        // System.out.println(System.currentTimeMillis()+"  accumulator:" + accumulator.toString());
        return accumulator;
    }

    @Override
    public Dsp getResult(Dsp accumulator) {
        return accumulator;
    }

    @Override
    public Dsp merge(Dsp a, Dsp b) {
        a.count+=b.count;
        a.entityIds.addAll(b.entityIds);
        return a;
    }

    @Override
    public void snapshotState(FunctionSnapshotContext functionSnapshotContext) throws Exception {
        System.out.println("DspIdeaAggegateFunction snapshotState tread: "+Thread.currentThread().getName());
    }

    @Override
    public void initializeState(FunctionInitializationContext context) throws Exception {
        System.out.println("DspIdeaAggegateFunction initializeState tread: "+Thread.currentThread().getName());
        ListStateDescriptor<Dsp> descriptor =new ListStateDescriptor<Dsp>("fsdafsd", TypeInformation.of(new TypeHint<Dsp>(){}));
        checkpointedState = context.getOperatorStateStore().getListState(descriptor);

        if(context.isRestored()){
            Iterable<Dsp> dsps = checkpointedState.get();
            accumulator= dsps.iterator().next();
        }
    }
}
