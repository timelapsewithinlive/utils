package designpatterns.tools;

import designpatterns.tools.Handler;
import designpatterns.tools.Request;

public interface SynHandler extends Handler {

    default Response synHandle(Request request){

        return null;
    }
}
