package com.example.resourcebooking.controller;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.resourcebooking.dto.ResourceRequest;
import com.example.resourcebooking.dto.ResourceResponse;
import com.example.resourcebooking.service.ResourceService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/resources")
public class ResourceController {

    private final ResourceService resourceService;

    public ResourceController(
            ResourceService resourceService) {

        this.resourceService = resourceService;
    }

    @PostMapping
    public ResponseEntity<ResourceResponse> createResource(
            @Valid @RequestBody ResourceRequest request) {

        return ResponseEntity.ok(
                resourceService.createResource(request)
        );
    }

    @GetMapping
    public ResponseEntity<List<ResourceResponse>>
            getAllResources() {

        return ResponseEntity.ok(
                resourceService.getAllResources()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResourceResponse>
            getResourceById(
                    @PathVariable Long id) {

        return ResponseEntity.ok(
                resourceService.getResourceById(id)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResourceResponse>
            updateResource(
                    @PathVariable Long id,
                    @Valid @RequestBody ResourceRequest request) {

        return ResponseEntity.ok(
                resourceService.updateResource(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteResource(
            @PathVariable Long id) {

        resourceService.deleteResource(id);

        return ResponseEntity.ok(
                "Resource deleted successfully"
        );
    }
}