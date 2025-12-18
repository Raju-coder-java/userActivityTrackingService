package com.example.UserActivityTrackingService.controller;


import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.UserActivityTrackingService.entity.Activity;
import com.example.UserActivityTrackingService.model.ActivityRequest;
import com.example.UserActivityTrackingService.service.ActivityService;


import jakarta.validation.Valid;


@RestController
@RequestMapping("/activities")
public class ActivityController {

    private final ActivityService activityService;

    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @PostMapping("/receive")
    public ResponseEntity<?> submit(
            @Valid @RequestBody ActivityRequest request,
            Authentication authentication) {

        System.out.println("Authenticated user = " + authentication.getName());

        activityService.submitActivity(request, authentication.getName());
        return ResponseEntity.ok("Activity received");
    }

    @GetMapping("/view")
    public List<Activity> view(Authentication auth) {
        return activityService.viewActivities(auth);
    }
}




   

