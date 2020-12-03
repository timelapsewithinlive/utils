package function;

import domain.Dsp;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.runtime.state.FunctionInitializationContext;
import org.apache.flink.runtime.state.FunctionSnapshotContext;
import org.apache.flink.streaming.api.checkpoint.CheckpointedFunction;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;

/**
 * @author xinghonglin
 * @date 2020/12/03
 */
public class DspSinkBufferFunction extends RichSinkFunction<Dsp> implements CheckpointedFunction {

    //连接资源
    @Override
    public void open(Configuration parameters) throws Exception {
    }

    @Override
    public void invoke(Dsp value, Context context) throws Exception {
        //value每次都是新集合
       // System.out.println("sink-------context.currentProcessingTime(): "+ context.currentProcessingTime());
        // System.out.println("sink-------" + value+"-------------- context.currentProcessingTime(): "+ context.currentProcessingTime());
    }

    //关闭资源、释放资源
    @Override
    public void close() throws Exception {
        // System.out.println("closesssss");
    }

    @Override
    public void snapshotState(FunctionSnapshotContext functionSnapshotContext) throws Exception {
        //System.out.println(functionSnapshotContext);
    }

    @Override
    public void initializeState(FunctionInitializationContext functionInitializationContext) throws Exception {
        //System.out.println(functionInitializationContext);
    }
}
