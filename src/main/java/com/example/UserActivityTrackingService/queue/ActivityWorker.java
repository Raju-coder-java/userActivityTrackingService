package com.example.UserActivityTrackingService.queue;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.example.UserActivityTrackingService.entity.Activity;
import com.example.UserActivityTrackingService.model.ActivityStatus;
import com.example.UserActivityTrackingService.repository.ActivityRepository;

import jakarta.annotation.PostConstruct;

@Component
public class ActivityWorker {

    private  ActivityQueue queue;
    private  ActivityRepository repo;

    @PostConstruct
    public void start() {
        new Thread(this::process).start();
    }

    private void process() {
        while (true) {
            try {
                Long id = queue.dequeue();
                Activity activity = repo.findById(id).orElse(null);
                if (activity == null || activity.getStatus() != ActivityStatus.RECEIVED)
                    continue;

                activity.setStatus(ActivityStatus.PROCESSING);
                repo.save(activity);

                // Simulate transformation
                Thread.sleep(200);

                activity.setStatus(ActivityStatus.PROCESSED);
                activity.setProcessedAt(LocalDateTime.now());
                repo.save(activity);

            } catch (Exception e) {
                // log and continue
            }
        }
    }
}

