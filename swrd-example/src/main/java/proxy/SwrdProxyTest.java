package proxy;

import ciglib.Human;
import ciglib.SuperMan;
import designpatterns.proxy.SwrdInvocationHandlerImpl;

public class SwrdProxyTest {

    public static void main(String[] args) {

        Human instance = (Human) new SwrdInvocationHandlerImpl().getInstance(new SuperMan());
        instance.fly();
    }
}
