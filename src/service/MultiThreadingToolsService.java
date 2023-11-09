package service;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public interface MultiThreadingToolsService {
    void awaitThread() throws InterruptedException;
    void notifyAllThread();
    void awaitThread(Condition condition) throws InterruptedException;
    void notifyAllThread(Condition condition);
    void lock();
    void unlock();
    void lock(ReentrantLock reentrantLock);
    void unlock(ReentrantLock reentrantLock);
}
