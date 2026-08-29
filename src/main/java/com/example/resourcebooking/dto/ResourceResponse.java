package com.example.resourcebooking.dto;


import java.math.BigDecimal;

public class ResourceResponse {

    private Long id;
    private String name;
    private String description;
    private boolean available;
    private BigDecimal price;

    public ResourceResponse() {
    }

    public ResourceResponse(Long id,
                            String name,
                            String description,
                            boolean available,
                            BigDecimal price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.available = available;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isAvailable() {
        return available;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
