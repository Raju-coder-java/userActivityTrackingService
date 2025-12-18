package com.example.UserActivityTrackingService.queue;

import com.example.UserActivityTrackingService.entity.Activity;
import com.example.UserActivityTrackingService.model.ActivityStatus;
import com.example.UserActivityTrackingService.repository.ActivityRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ActivityScheduler {

    private final ActivityRepository repo;
    private final ActivityQueue queue;

    public ActivityScheduler(ActivityRepository repo, ActivityQueue queue) {
        this.repo = repo;
        this.queue = queue;
    }

    @Scheduled(fixedRate = 60000)
    public void requeueUnprocessed() {
        List<Activity> pending = repo.findByStatus(ActivityStatus.RECEIVED);

        for (Activity activity : pending) {
            queue.enqueue(activity.getId());
        }

        System.out.println("Requeued " + pending.size() + " activities");
    }
}
