package distribute.queue;

/**
 * Created by xinghonglin on 2017/11/29.
 */
public interface QueueStore<T> {
    public boolean offer(T t);
    public T peek();
    public T peek(int index);
    public boolean remove(T t);
    public int size();
}
