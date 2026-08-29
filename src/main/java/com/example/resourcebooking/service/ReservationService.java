package com.example.resourcebooking.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.security.core.Authentication;
import com.example.resourcebooking.dto.ReservationRequest;
import com.example.resourcebooking.dto.ReservationResponse;
import com.example.resourcebooking.entity.Reservation;
import com.example.resourcebooking.entity.ReservationStatus;
import com.example.resourcebooking.entity.Resource;
import com.example.resourcebooking.entity.User;
import com.example.resourcebooking.exception.ReservationNotFoundException;
import com.example.resourcebooking.exception.ResourceNotFoundException;
import com.example.resourcebooking.repository.ReservationRepository;
import com.example.resourcebooking.repository.ResourceRepository;
import com.example.resourcebooking.repository.UserRepository;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ResourceRepository resourceRepository;
    private final UserRepository userRepository;

    public ReservationService(
            ReservationRepository reservationRepository,
            ResourceRepository resourceRepository,
            UserRepository userRepository) {

        this.reservationRepository = reservationRepository;
        this.resourceRepository = resourceRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public ReservationResponse createReservation(
            ReservationRequest request,
            String username) {

        User user =
                userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found"
                        )
                );

        Resource resource =
                resourceRepository.findById(
                        request.getResourceId()
                )
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Resource not found with id: "
                                + request.getResourceId()
                        )
                );

        if (!resource.isAvailable()) {
            throw new RuntimeException(
                    "Resource is not available"
            );
        }

        Reservation reservation = new Reservation();

        reservation.setUser(user);
        reservation.setResource(resource);

        // Store price at reservation time
        reservation.setPrice(resource.getPrice());

        reservation.setStatus(
                ReservationStatus.CONFIRMED
        );

        reservation.setCreatedAt(
                LocalDateTime.now()
        );

        Reservation saved =
                reservationRepository.save(reservation);

        return convertToResponse(saved);
    }

    @Transactional(readOnly = true)
    public ReservationResponse getReservationById(
            Long id) {

        Reservation reservation =
                reservationRepository.findById(id)
                .orElseThrow(() ->
                        new ReservationNotFoundException(
                                "Reservation not found with id: "
                                + id
                        )
                );

        return convertToResponse(reservation);
    }

    @Transactional
    public ReservationResponse cancelReservation(
            Long id,
            String username) {

        Reservation reservation =
                reservationRepository.findById(id)
                .orElseThrow(() ->
                        new ReservationNotFoundException(
                                "Reservation not found with id: "
                                + id
                        )
                );

        if (!reservation.getUser()
                .getUsername()
                .equals(username)) {

            throw new RuntimeException(
                    "You cannot cancel another user's reservation"
            );
        }

        reservation.setStatus(
                ReservationStatus.CANCELLED
        );

        Reservation updated =
                reservationRepository.save(reservation);

        return convertToResponse(updated);
    }

    private ReservationResponse convertToResponse(
            Reservation reservation) {

        return new ReservationResponse(
                reservation.getId(),
                reservation.getResource().getId(),
                reservation.getResource().getName(),
                reservation.getUser().getUsername(),
                reservation.getPrice(),
                reservation.getStatus(),
                reservation.getCreatedAt()
        );
    }
    
    @Transactional(readOnly = true)
    public Page<ReservationResponse> getReservations(
            String username,
            Authentication authentication,
            ReservationStatus status,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            int page,
            int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<Reservation> reservations;

        boolean isAdmin = authentication.getAuthorities()
                .stream()
                .anyMatch(a ->
                        a.getAuthority().equals("ROLE_ADMIN"));

        if (isAdmin) {

            if (status != null &&
                minPrice != null &&
                maxPrice != null) {

                reservations =
                        reservationRepository
                        .findByStatusAndPriceGreaterThanEqualAndPriceLessThanEqual(
                                status,
                                minPrice,
                                maxPrice,
                                pageable);

            } else if (status != null &&
                       minPrice != null) {

                reservations =
                        reservationRepository
                        .findByStatusAndPriceGreaterThanEqual(
                                status,
                                minPrice,
                                pageable);

            } else if (status != null &&
                       maxPrice != null) {

                reservations =
                        reservationRepository
                        .findByStatusAndPriceLessThanEqual(
                                status,
                                maxPrice,
                                pageable);

            } else if (minPrice != null &&
                       maxPrice != null) {

                reservations =
                        reservationRepository
                        .findByPriceGreaterThanEqualAndPriceLessThanEqual(
                                minPrice,
                                maxPrice,
                                pageable);

            } else if (status != null) {

                reservations =
                        reservationRepository
                        .findByStatus(
                                status,
                                pageable);

            } else if (minPrice != null) {

                reservations =
                        reservationRepository
                        .findByPriceGreaterThanEqual(
                                minPrice,
                                pageable);

            } else if (maxPrice != null) {

                reservations =
                        reservationRepository
                        .findByPriceLessThanEqual(
                                maxPrice,
                                pageable);

            } else {

                reservations =
                        reservationRepository
                        .findAll(pageable);
            }

        } else {

            reservations =
                    reservationRepository
                    .findByUserUsername(
                            username,
                            pageable);
        }

        return reservations.map(this::convertToResponse);
    }
    
    
}