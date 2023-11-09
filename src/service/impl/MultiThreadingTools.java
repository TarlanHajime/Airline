package service.impl;

import service.MultiThreadingToolsService;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MultiThreadingTools implements MultiThreadingToolsService {
    private static final ReentrantLock reentrantLock = new ReentrantLock();
    private static final Condition condition = reentrantLock.newCondition();

    @Override
    public void awaitThread(Condition condition) throws InterruptedException {
        condition.await();
    }

    @Override
    public void notifyAllThread(Condition condition) {
        condition.signalAll();
    }

    @Override
    public void awaitThread() throws InterruptedException {
        condition.await();
    }

    @Override
    public void notifyAllThread() {
        condition.signalAll();
    }

    @Override
    public void lock() {
        reentrantLock.lock();
    }

    @Override
    public void unlock() {
        reentrantLock.unlock();
    }

    @Override
    public void lock(ReentrantLock reentrantLock) {
        reentrantLock.lock();
    }

    @Override
    public void unlock(ReentrantLock reentrantLock) {
        reentrantLock.unlock();
    }
}
