package com.example.resourcebooking.dto;


import com.example.resourcebooking.entity.ReservationStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ReservationResponse {

    private Long id;
    private Long resourceId;
    private String resourceName;
    private String username;
    private BigDecimal price;
    private ReservationStatus status;
    private LocalDateTime createdAt;

    public ReservationResponse() {
    }

    public ReservationResponse(Long id,
                               Long resourceId,
                               String resourceName,
                               String username,
                               BigDecimal price,
                               ReservationStatus status,
                               LocalDateTime createdAt) {
        this.id = id;
        this.resourceId = resourceId;
        this.resourceName = resourceName;
        this.username = username;
        this.price = price;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public String getResourceName() {
        return resourceName;
    }

    public String getUsername() {
        return username;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}