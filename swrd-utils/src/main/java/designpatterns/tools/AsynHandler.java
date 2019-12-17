package designpatterns.tools;

import java.util.concurrent.Future;

public interface AsynHandler extends Handler {

    default Future asynHandle(Request request){
        return null;
    }

}
