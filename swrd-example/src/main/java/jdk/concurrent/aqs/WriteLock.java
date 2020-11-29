package jdk.concurrent.aqs;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class WriteLock implements Lock {

    private static Sync sync=new Sync();

    private static class Sync extends AbstractQueuedSynchronizer {

        @Override
        protected boolean tryAcquire(int acquires) {

            if (compareAndSetState(0, acquires)) {
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        @Override
        protected boolean tryRelease(int arg)  {
            if (getState()==0)
                throw  new IllegalMonitorStateException();
            setExclusiveOwnerThread(null);
            setState(0);
            return true;

        }

        public Condition newCondition() {
            return new ConditionObject();
        }

        /*
           是否处于独占状态
         */
        @Override
        protected boolean isHeldExclusively() {
            return  getState()==1;//同acquire传入的值一样
        }
    }
    @Override
    public void lock() {
        sync.acquire(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        return  sync.tryAcquire(1);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1, unit.toNanos(time));
    }

    @Override
    public void unlock() {
        sync.release(1);
    }

    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }


}
