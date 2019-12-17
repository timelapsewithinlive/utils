package designpatterns.chain;

public interface Pipeline {

    Pipeline fireReceiveRequest();

    Response fireReturnResponse();

    Pipeline addLast(Handler handler);
}
