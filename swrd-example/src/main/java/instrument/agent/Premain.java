package instrument.agent;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

public class Premain {
    public static void premain(String agentArgs,Instrumentation inst)  throws ClassNotFoundException,UnmodifiableClassException

    {
        /*ClassDefinition def = new ClassDefinition(TransClass.class,Transformer.getBytesFromFile(Transformer.classNumberReturns2));
        inst.redefineClasses(new ClassDefinition[] { def });*/
        inst.addTransformer(new Transformer());
        System.out.println("success");
    }
}
