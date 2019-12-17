package designpatterns.tools;

import java.util.concurrent.Future;

public interface AsynHandler extends Handler {

    default Response asynHandle(Request request){
        return null;
    }

}
