package com.rookie.java.tree.juc.test;

import com.rookie.java.tree.juc.lock.RookieOptiLock;

import java.util.Collection;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author rookie
 * @Date 2024-09-14 6:01
 * @Description
 **/
public class LockDemo {

    int i = 0;
    Lock lock = new  RookieOptiLock();

    ReentrantLock reentrantLock = new ReentrantLock(){
        @Override
        protected Collection<Thread> getQueuedThreads() {
            return super.getQueuedThreads();
        }
    };

    public static void main(String[] args) throws InterruptedException {
        LockDemo lockDemo = new LockDemo();

        for (int i = 0; i<4; i++){
            new Thread(() -> {
                for (int j = 0; j< 5000; j++){
                    lockDemo.add();
                }
            }).start();
        }
        TimeUnit.MILLISECONDS.sleep(2000L);

        System.out.println(lockDemo.i);
    }

    private void add()  {
        lock.lock();
        try {
            i++;
        } finally {
            lock.unlock();
        }
    }
}
