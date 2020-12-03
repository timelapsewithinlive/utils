package function;

import domain.DspIdea;
import org.apache.flink.streaming.api.windowing.evictors.Evictor;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.streaming.runtime.operators.windowing.TimestampedValue;

import java.util.Iterator;

/**
 * @author xinghonglin
 * @date 2020/12/03
 */
public class DspIdeaEvitor implements Evictor<DspIdea, TimeWindow> {
    @Override
    public void evictBefore(Iterable<TimestampedValue<DspIdea>> iterable, int size, TimeWindow timeWindow, EvictorContext evictorContext) {
        //do nothing
    }

    @Override
    public void evictAfter(Iterable<TimestampedValue<DspIdea>> iterable, int i, TimeWindow timeWindow, EvictorContext evictorContext) {
        Iterator<TimestampedValue<DspIdea>> iterator = iterable.iterator();
        while (iterator.hasNext()) {
            TimestampedValue<DspIdea> next = iterator.next();
            iterator.remove();
        }
    }
}
