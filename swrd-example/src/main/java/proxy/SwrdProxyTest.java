package proxy;

import ciglib.SuperMan;
import designpatterns.proxy.SwrdInvocationHandlerImpl;

public class SwrdProxyTest {

    public static void main(String[] args) {

        SuperMan instance = (SuperMan) new SwrdInvocationHandlerImpl().getInstance(new SuperMan());

    }
}
