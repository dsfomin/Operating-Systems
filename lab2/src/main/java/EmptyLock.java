import java.util.concurrent.TimeUnit;

public class EmptyLock extends Lockable {
    EmptyLock(int maxCount) {
        super(maxCount);
    }

    @Override
    public void lock() {

    }

    @Override
    public void lockInterruptibly() {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long l, TimeUnit timeUnit) {
        return false;
    }

    @Override
    public void unlock() {

    }
}