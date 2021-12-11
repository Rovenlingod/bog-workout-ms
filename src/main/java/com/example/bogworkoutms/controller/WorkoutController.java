package com.example.bogworkoutms.controller;

import com.example.bogworkoutms.dto.WorkoutRequestDTO;
import com.example.bogworkoutms.service.WorkoutService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
public class WorkoutController {
    private WorkoutService workoutService;

    @PostMapping(path = "/user")
    public ResponseEntity<Object> createWorkout(@RequestBody WorkoutRequestDTO workoutRequestDTO) {
        UUID id = workoutService.createWorkout(workoutRequestDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id.toString()).toUri();
        return ResponseEntity.created(uri).build();
    }
}
