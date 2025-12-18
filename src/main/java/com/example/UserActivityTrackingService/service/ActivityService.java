package com.example.UserActivityTrackingService.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.example.UserActivityTrackingService.entity.Activity;
import com.example.UserActivityTrackingService.model.ActivityRequest;
import com.example.UserActivityTrackingService.model.ActivityStatus;
import com.example.UserActivityTrackingService.queue.ActivityQueue;
import com.example.UserActivityTrackingService.repository.ActivityRepository;

@Service
public class ActivityService {

	  private final ActivityRepository repo;
	    private final ActivityQueue queue;

	    public ActivityService(ActivityRepository repo, ActivityQueue queue) {
	        this.repo = repo;
	        this.queue = queue;
	    }
    public void submitActivity(ActivityRequest req, String userId) {

        Activity activity = new Activity();
        activity.setUserId(userId);
        activity.setType(req.getType());
        activity.setPayload(req.getPayload());
        activity.setStatus(ActivityStatus.RECEIVED);
        activity.setCreatedAt(LocalDateTime.now());

        repo.save(activity);
        queue.enqueue(activity.getId());
    }

    public List<Activity> viewActivities(Authentication auth) {

        boolean isAdmin = auth.getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (isAdmin) {
            return repo.findAll();
        }
        return repo.findByUserId(auth.getName());
    }
}


