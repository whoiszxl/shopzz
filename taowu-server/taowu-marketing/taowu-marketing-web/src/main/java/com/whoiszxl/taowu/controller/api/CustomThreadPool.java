package com.whoiszxl.taowu.controller.api;
import java.util.concurrent.*;

public class CustomThreadPool {

    public static void main(String[] args) throws InterruptedException {
        // 创建一个线程池
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
                0, // 初始线程数
                6, // 最大线程数
                11, // 线程空闲超时时间
                TimeUnit.SECONDS, // 时间单位
                new LinkedBlockingQueue<>() // 任务队列
        );

        // 提交任务到线程池
        for (int i = 0; i < 10; i++) {
            threadPool.execute(new Task(i));
        }
        Thread.sleep(10000);
        System.out.println(threadPool.getActiveCount());
        Thread.sleep(22000);
        System.out.println(threadPool.getActiveCount());
        threadPool.execute(new Task(1000));
        System.out.println(threadPool.getActiveCount());
    }

    static class Task implements Runnable {
        private final int taskId;

        public Task(int taskId) {
            this.taskId = taskId;
        }

        @Override
        public void run() {
            try {
                System.out.println("Task " + taskId + " started");
                // 模拟任务执行
                Thread.sleep(1000);
                System.out.println("Task " + taskId + " completed");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
