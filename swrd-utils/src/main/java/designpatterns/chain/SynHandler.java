package designpatterns.chain;

public interface SynHandler extends Handler {

    default Response synHandle(Request request){

        return null;
    }
}
