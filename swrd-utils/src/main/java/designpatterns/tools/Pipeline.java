package designpatterns.tools;

public interface Pipeline {

    Pipeline fireReceiveRequest();

    Response fireReturnResponse();

    Pipeline addLast(Handler handler);
}
