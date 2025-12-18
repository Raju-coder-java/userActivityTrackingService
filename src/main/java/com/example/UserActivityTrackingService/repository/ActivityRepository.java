package com.example.UserActivityTrackingService.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.UserActivityTrackingService.entity.Activity;
import com.example.UserActivityTrackingService.model.ActivityStatus;

public interface ActivityRepository extends JpaRepository<Activity, Long> {



	List<Activity> findByUserId(String userId);
    List<Activity> findByStatus(ActivityStatus status);


}
