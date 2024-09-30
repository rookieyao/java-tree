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
public class RookieLock implements Lock {

    volatile AtomicReference<Thread> owner = new AtomicReference<>();
    volatile LinkedBlockingQueue<Thread> waiters = new LinkedBlockingQueue<>();

    @Override
    public void lock() {

        boolean addQ = true;
        while (!tryLock()){
            if(addQ){
                // 没有拿到锁，加入到等待集合
                waiters.offer(Thread.currentThread());
                addQ = false;
            }else {
                // 阻塞，挂起当前的线程，不要再继续往下跑了
                LockSupport.park();
            }
        }
        waiters.remove(Thread.currentThread());
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return owner.compareAndSet(null, Thread.currentThread());
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {


    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
