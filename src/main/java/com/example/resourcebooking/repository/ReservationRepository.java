package com.example.resourcebooking.repository;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.resourcebooking.entity.Reservation;
import com.example.resourcebooking.entity.ReservationStatus;

public interface ReservationRepository
        extends JpaRepository<Reservation, Long> {

    // User's reservations
    Page<Reservation> findByUserUsername(
            String username,
            Pageable pageable
    );

    // Filter by status
    Page<Reservation> findByStatus(
            ReservationStatus status,
            Pageable pageable
    );

    // Minimum price
    Page<Reservation> findByPriceGreaterThanEqual(
            BigDecimal minPrice,
            Pageable pageable
    );

    // Maximum price
    Page<Reservation> findByPriceLessThanEqual(
            BigDecimal maxPrice,
            Pageable pageable
    );

    // Status + minimum price
    Page<Reservation> findByStatusAndPriceGreaterThanEqual(
            ReservationStatus status,
            BigDecimal minPrice,
            Pageable pageable
    );

    // Status + maximum price
    Page<Reservation> findByStatusAndPriceLessThanEqual(
            ReservationStatus status,
            BigDecimal maxPrice,
            Pageable pageable
    );

    // Price range
    Page<Reservation> findByPriceGreaterThanEqualAndPriceLessThanEqual(
            BigDecimal minPrice,
            BigDecimal maxPrice,
            Pageable pageable
    );

    // Status + price range
    Page<Reservation> findByStatusAndPriceGreaterThanEqualAndPriceLessThanEqual(
            ReservationStatus status,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            Pageable pageable
    );
}