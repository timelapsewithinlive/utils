package designpatterns.chain;

public interface Listener<V> {

    public void listen(Response response);
}
