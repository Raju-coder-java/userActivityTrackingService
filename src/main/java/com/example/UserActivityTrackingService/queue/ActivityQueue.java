package com.example.UserActivityTrackingService.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.stereotype.Component;

@Component
public class ActivityQueue {

    private final BlockingQueue<Long> queue = new LinkedBlockingQueue<>();

    public void enqueue(Long activityId) {
        queue.offer(activityId);
    }

    public Long dequeue() throws InterruptedException {
        return queue.take();
    }
}


