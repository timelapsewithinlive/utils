package designpatterns.tools;

public interface Pipeline {

    Pipeline fireReceiveRequest();

    Pipeline addLast(Handler handler);
}
