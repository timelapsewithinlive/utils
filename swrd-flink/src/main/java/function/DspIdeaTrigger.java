package function;

import domain.DspIdea;
import org.apache.flink.streaming.api.windowing.triggers.Trigger;
import org.apache.flink.streaming.api.windowing.triggers.TriggerResult;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;

/**
 * @author xinghonglin
 * @date 2020/12/03
 */
public class DspIdeaTrigger extends Trigger<DspIdea, TimeWindow> {

    //TriggerResult.FIRE_AND_PURGE 会重新执行聚合函数的createAccumulator
    //TriggerResult.FIRE ：复用第一次会重新执行聚合函数的createAccumulator的结果
    @Override
    public TriggerResult onElement(DspIdea dspIdeaDspTuple2, long l, TimeWindow timeWindow, TriggerContext triggerContext) throws Exception {
        //System.out.println("timeWindow.getStart(): "+timeWindow.getStart());
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
}
