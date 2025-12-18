package com.example.UserActivityTrackingService.entity;

import java.time.LocalDateTime;

import com.example.UserActivityTrackingService.model.ActivityStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "activity", schema = "useractivity")

public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
	private String userId;
    @Column
    private String type; // PAGE_VISIT, CLICK, LOGIN

    @Column(columnDefinition = "TEXT")
    private String payload;

    @Enumerated(EnumType.STRING)
    private ActivityStatus status;

    private LocalDateTime createdAt;
    private LocalDateTime processedAt;
    
    
    
    public Long getId() {
  		return id;
  	}
  	public void setId(Long id) {
  		this.id = id;
  	}
  	public String getUserId() {
  		return userId;
  	}
  	public void setUserId(String userId) {
  		this.userId = userId;
  	}
  	public String getType() {
  		return type;
  	}
  	public void setType(String type) {
  		this.type = type;
  	}
  	public String getPayload() {
  		return payload;
  	}
  	public void setPayload(String payload) {
  		this.payload = payload;
  	}
  	public ActivityStatus getStatus() {
  		return status;
  	}
  	public void setStatus(ActivityStatus status) {
  		this.status = status;
  	}
  	public LocalDateTime getCreatedAt() {
  		return createdAt;
  	}
  	public void setCreatedAt(LocalDateTime createdAt) {
  		this.createdAt = createdAt;
  	}
  	public LocalDateTime getProcessedAt() {
  		return processedAt;
  	}
  	public void setProcessedAt(LocalDateTime processedAt) {
  		this.processedAt = processedAt;
  	}
}

