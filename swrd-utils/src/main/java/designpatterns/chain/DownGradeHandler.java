package designpatterns.chain;

public interface DownGradeHandler extends AsynHandler{

    @Override
    @UnNecessary
    default Response asynHandle(Request request){
        return null;
    }

}
