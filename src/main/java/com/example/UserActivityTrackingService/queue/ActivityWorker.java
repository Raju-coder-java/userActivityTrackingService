package com.example.UserActivityTrackingService.queue;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.example.UserActivityTrackingService.entity.Activity;
import com.example.UserActivityTrackingService.model.ActivityStatus;
import com.example.UserActivityTrackingService.repository.ActivityRepository;

import jakarta.annotation.PostConstruct;

@Component
public class ActivityWorker {

    private final ActivityQueue queue;
    private final ActivityRepository repo;

    // ✅ Constructor injection (REQUIRED)
    public ActivityWorker(ActivityQueue queue, ActivityRepository repo) {
        this.queue = queue;
        this.repo = repo;
    }

    @PostConstruct
    public void start() {
        System.out.println("✅ ActivityWorker started");
        new Thread(this::process).start();
    }

    private void process() {
        while (true) {
            try {
                Long id = queue.dequeue();
                System.out.println("➡️ Dequeued activity id = " + id);

                Activity activity = repo.findById(id).orElse(null);
                if (activity == null) continue;

                activity.setStatus(ActivityStatus.PROCESSING);
                repo.save(activity);

                Thread.sleep(200); // simulate work

                activity.setStatus(ActivityStatus.PROCESSED);
                activity.setProcessedAt(LocalDateTime.now());
                repo.save(activity);

                System.out.println("✅ Activity processed: " + id);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}



