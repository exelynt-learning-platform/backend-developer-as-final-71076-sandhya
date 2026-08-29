package com.example.resourcebooking.controller;

import java.math.BigDecimal;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.example.resourcebooking.dto.ReservationRequest;
import com.example.resourcebooking.dto.ReservationResponse;
import com.example.resourcebooking.entity.ReservationStatus;
import com.example.resourcebooking.service.ReservationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(
            ReservationService reservationService) {

        this.reservationService = reservationService;
    }

    // CREATE RESERVATION
    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(
            @Valid @RequestBody ReservationRequest request,
            Authentication authentication) {

        String username = authentication.getName();

        return ResponseEntity.ok(
                reservationService.createReservation(
                        request,
                        username
                )
        );
    }

    // GET RESERVATION BY ID
    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponse> getReservationById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                reservationService.getReservationById(id)
        );
    }

    // CANCEL RESERVATION
    @PutMapping("/{id}/cancel")
    public ResponseEntity<ReservationResponse> cancelReservation(
            @PathVariable Long id,
            Authentication authentication) {

        String username = authentication.getName();

        return ResponseEntity.ok(
                reservationService.cancelReservation(
                        id,
                        username
                )
        );
    }

    // GET RESERVATIONS WITH PAGINATION AND FILTERS
    @GetMapping
    public ResponseEntity<?> getReservations(
            Authentication authentication,

            @RequestParam(required = false)
            ReservationStatus status,

            @RequestParam(required = false)
            BigDecimal minPrice,

            @RequestParam(required = false)
            BigDecimal maxPrice,

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size) {

        String username = authentication.getName();

        return ResponseEntity.ok(
                reservationService.getReservations(
                        username,
                        authentication,
                        status,
                        minPrice,
                        maxPrice,
                        page,
                        size
                )
        );
    }
}