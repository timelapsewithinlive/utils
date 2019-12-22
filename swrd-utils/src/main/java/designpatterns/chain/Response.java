package designpatterns.chain;

public class Response<T> {

    private HandlerCurrentlyStatus flag;

    private Throwable cause;

    private T data;

    public Response() {
    }

    public Response(HandlerCurrentlyStatus flag, T data) {
        this.flag = flag;
        this.data = data;
    }

    public HandlerCurrentlyStatus getFlag() {
        return flag;
    }

    public void setFlag(HandlerCurrentlyStatus flag) {
        this.flag = flag;
    }

    public Throwable getCause() {
        return cause;
    }

    public void setCause(Throwable cause) {
        this.cause = cause;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Response{" +
                "flag=" + flag +
                ", cause=" + cause +
                ", data=" + data +
                ", 当前时间=" + System.currentTimeMillis() +
                '}';
    }
}
