package designpatterns.tools;

public class Response<T> {

    private int retCode;

    private String retMsg;

    private T data;

    public Response() {
    }

    public Response(int retCode, String retMsg, T data) {
        this.retCode = retCode;
        this.retMsg = retMsg;
        this.data = data;
    }
}
