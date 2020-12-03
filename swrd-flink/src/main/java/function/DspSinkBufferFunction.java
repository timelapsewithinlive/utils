package function;

import domain.Dsp;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;

/**
 * @author xinghonglin
 * @date 2020/12/03
 */
public class DspSinkBufferFunction extends RichSinkFunction<Dsp> {

    //连接资源
    @Override
    public void open(Configuration parameters) throws Exception {
    }

    @Override
    public void invoke(Dsp value, Context context) throws Exception {
        //value每次都是新集合
        System.out.println("sink-------" + value+"-------------- context.currentProcessingTime(): "+ context.currentProcessingTime());
    }

    //关闭资源、释放资源
    @Override
    public void close() throws Exception {
        // System.out.println("closesssss");
    }
}
