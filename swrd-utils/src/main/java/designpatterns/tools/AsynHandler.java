package designpatterns.tools;

import java.util.concurrent.Future;

public interface AsynHandler extends Handler {

    default void asynHandle(Request request){
    }

}
