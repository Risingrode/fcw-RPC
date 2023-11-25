package com.wxy.rpc.core.other;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

// 线程测试
public class ThreadTest {

    public void test() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                2,          // 核心线程数
                10,     // 最大线程数
                0L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(20)   // 任务队列容量
        );
        // 执行 22 次任务
        for (int i = 0; i < 23; i++) {
            executor.execute(new DemoRunnable(i));
        }
    }

    public static class DemoRunnable implements Runnable {
        final int value;
        static long time = 1000L * 1000L;   // 休眠长一点
        public DemoRunnable(int value) {
            this.value = value;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " - value = " + value);
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new ThreadTest().test();
    }

}
