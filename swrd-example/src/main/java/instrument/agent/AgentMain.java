package instrument.agent;

import instrument.TransClass;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

public class AgentMain {
    public static void agentmain(String agentArgs, Instrumentation inst) throws ClassNotFoundException, UnmodifiableClassException, InterruptedException, UnmodifiableClassException {
        System.out.println("Agent Main start");
        inst.addTransformer(new Transformer(),true);
        inst.retransformClasses(TransClass.class);
        System.out.println("Agent Main Done");
    }
}
