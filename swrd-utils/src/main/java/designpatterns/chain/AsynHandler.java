package designpatterns.chain;

public interface AsynHandler extends Handler {

    default Response asynHandle(Request request){
        return null;
    }

}
