package function;


import domain.Dsp;
import domain.DspIdea;
import org.apache.commons.collections.CollectionUtils;
import org.apache.flink.api.common.functions.AggregateFunction;

import java.util.ArrayList;

/**
 * @author xinghonglin
 * @date 2020/12/03
 */
public class DspIdeaAggegateFunction implements AggregateFunction<DspIdea, Dsp, Dsp> {

    Dsp accumulator = null;

    public DspIdeaAggegateFunction() {
        System.out.println("构造新的聚合函数");
        accumulator = new Dsp();
    }

    @Override
    public Dsp createAccumulator() {
        if (CollectionUtils.isNotEmpty(accumulator.dspIdeas)) {
            accumulator.dspIdeas.clear();
        }
        return accumulator;
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
        a.entityIds.addAll(b.entityIds);
        return a;
    }
}
