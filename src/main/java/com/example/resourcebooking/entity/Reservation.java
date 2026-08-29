package com.example.resourcebooking.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "reservations")
public class Reservation {
	
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @ManyToOne(fetch = FetchType.LAZY, optional = false)
	    @JoinColumn(name = "user_id", nullable = false)
	    private User user;

	    @ManyToOne(fetch = FetchType.LAZY, optional = false)
	    @JoinColumn(name = "resource_id", nullable = false)
	    private Resource resource;

	    @Column(nullable = false, precision = 10, scale = 2)
	    private BigDecimal price;

	    @Enumerated(EnumType.STRING)
	    @Column(nullable = false)
	    private ReservationStatus status;

	    @Column(nullable = false)
	    private LocalDateTime createdAt;

	    public Reservation() {
	    }

	    public Reservation(User user,
	                        Resource resource,
	                        BigDecimal price,
	                        ReservationStatus status,
	                        LocalDateTime createdAt) {

	        this.user = user;
	        this.resource = resource;
	        this.price = price;
	        this.status = status;
	        this.createdAt = createdAt;
	    }

	    public Long getId() {
	        return id;
	    }

	    public User getUser() {
	        return user;
	    }

	    public void setUser(User user) {
	        this.user = user;
	    }

	    public Resource getResource() {
	        return resource;
	    }

	    public void setResource(Resource resource) {
	        this.resource = resource;
	    }

	    public BigDecimal getPrice() {
	        return price;
	    }

	    public void setPrice(BigDecimal price) {
	        this.price = price;
	    }

	    public ReservationStatus getStatus() {
	        return status;
	    }

	    public void setStatus(ReservationStatus status) {
	        this.status = status;
	    }

	    public LocalDateTime getCreatedAt() {
	        return createdAt;
	    }

	    public void setCreatedAt(LocalDateTime createdAt) {
	        this.createdAt = createdAt;
	    }

}
