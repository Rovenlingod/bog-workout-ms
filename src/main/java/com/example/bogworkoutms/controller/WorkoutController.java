package com.example.bogworkoutms.controller;

import com.example.bogworkoutms.dto.WorkoutRequestDTO;
import com.example.bogworkoutms.dto.WorkoutResponseDTO;
import com.example.bogworkoutms.service.WorkoutService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/workout")
public class WorkoutController {
    private WorkoutService workoutService;

    public WorkoutController(WorkoutService workoutService) {
        this.workoutService = workoutService;
    }

    @PostMapping
    public ResponseEntity<Object> createWorkout(@RequestBody WorkoutRequestDTO workoutRequestDTO) {
        UUID id = workoutService.createWorkout(workoutRequestDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id.toString()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkoutResponseDTO> getWorkoutById(@PathVariable("id") String id) {
        return ResponseEntity.ok().body(workoutService.findByWorkoutId(id));
    }

    @PostMapping("/subscribe/{id}")
    public ResponseEntity<?> subscribeToWorkout(@PathVariable String id) {
        workoutService.subscribeToWorkout(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/unsubscribe/{id}")
    public ResponseEntity<?> unsubscribeFromWorkout(@PathVariable String id) {
        workoutService.unsubscribeFromWorkout(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
