package com.rookie.java.tree.juc.lock;

import java.util.Iterator;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;

/**
 * @Author rookie
 * @Date 2024-09-13 21:59
 * @Description
 **/
public class RookieOptiLock implements Lock {

    RookieAqs rookieAqs = new RookieAqs(){

        @Override
        public boolean tryAcquire() {
            return owner.compareAndSet(null, Thread.currentThread());
        }

        @Override
        public boolean tryRelease() {
            return owner.compareAndSet(Thread.currentThread(), null);
        }
    };


    @Override
    public void lock() {
        rookieAqs.acquire();
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return rookieAqs.tryAcquire();
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {
        rookieAqs.release();
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
