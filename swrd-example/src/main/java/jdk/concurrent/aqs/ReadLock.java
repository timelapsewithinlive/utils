package jdk.concurrent.aqs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;

public class ReadLock extends AbstractQueuedSynchronizer {

    private    Sync sync;

    private static int count;

    public  ReadLock(int count){
        this.count=count;
        sync=new Sync(count);
    }
    private  static   class  Sync extends AbstractQueuedSynchronizer {

        public Sync(int count){
            setState(count);
        }
        @Override
        protected boolean tryAcquire(int acquires) {
            int c=getState();
            if (c==0)
                return false;
            if (compareAndSetState(c, c-acquires)) {
                return true;
            }
            return false;
        }

        @Override
        protected boolean tryRelease(int arg)  {
            int c=getState();
            if (c==count)
                throw  new IllegalMonitorStateException();
            if (compareAndSetState(c, c+arg)) {
                return true;
            }
            return false;

        }
        public Condition newCondition() {
            return new ConditionObject();
        }
    }
    public void lock() {
        sync.acquire(1);
    }

    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    public boolean tryLock() {
        return  sync.tryAcquire(1);
    }

    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1, unit.toNanos(time));
    }

    public void unlock() {
        sync.release(1);
    }

    public Condition newCondition() {
        return sync.newCondition();
    }

}
