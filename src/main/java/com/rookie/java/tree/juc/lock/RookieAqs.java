package com.rookie.java.tree.juc.lock;

import java.util.Iterator;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.LockSupport;

/**
 * @Author rookie
 * @Date 2024-09-14 5:47
 * @Description 自己实现的一个抽象队列同步器
 * state， owner， waiters
 * acquire， acquireShared: 定义了资源争用的逻辑， 如果没有拿到，则等待
 * tryAcquire, tryAcquireShared: 实际执行占用资源的操作， 如何判定  由使用者具体去实现
 * release, releaseShared:  定义释放资源的逻辑，释放之后，通知后续节点进行争抢
 * tryRelease, tryReleaseShared:  实际执行资源释放的操作，由具体的AQS使用者去实现
 **/
public class RookieAqs {

    public volatile AtomicReference<Thread> owner = new AtomicReference<>();
    public volatile LinkedBlockingQueue<Thread> waiters = new LinkedBlockingQueue<>();
    public volatile AtomicInteger state = new AtomicInteger(0);
    public boolean tryAcquire(){
        // 交给使用者去实现，模板方法设计模式
        throw new UnsupportedOperationException();
    }

    public void acquire(){

        boolean addQ = true;
        while (!tryAcquire()){
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

    public boolean tryRelease(){
        // 交给使用者去实现，模板方法设计模式
        throw new UnsupportedOperationException();
    }

    public void release(){
        // 释放owner
        if(owner.compareAndSet(Thread.currentThread(), null)){ // 释放成功

            Iterator<Thread> iterator = waiters.iterator();
            while (iterator.hasNext()){
                Thread next = iterator.next();
                LockSupport.unpark(next);
            }
        }
    }
}
