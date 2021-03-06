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

    }

    public static void main(String[] args) {
        String ssss= "1610461054800";
        System.out.println(Integer.valueOf(ssss));
    }
}
