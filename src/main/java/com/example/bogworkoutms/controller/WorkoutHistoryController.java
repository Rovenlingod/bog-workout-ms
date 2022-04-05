package com.example.bogworkoutms.controller;

import com.example.bogworkoutms.dto.WorkoutHistoryDTO;
import com.example.bogworkoutms.dto.WorkoutResponseDTO;
import com.example.bogworkoutms.service.WorkoutHistoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workoutHistory")
public class WorkoutHistoryController {

    private final WorkoutHistoryService workoutHistoryService;

    public WorkoutHistoryController(WorkoutHistoryService workoutHistoryService) {
        this.workoutHistoryService = workoutHistoryService;
    }

    @PostMapping("/{workoutId}")
    public ResponseEntity<WorkoutHistoryDTO> saveWorkoutHistory(@PathVariable String workoutId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(workoutHistoryService.saveWorkoutHistory(workoutId));
    }

    @GetMapping("/latest")
    public ResponseEntity<List<WorkoutHistoryDTO>> getLatest(@RequestParam(defaultValue = "0") int pageNo, @RequestParam(defaultValue = "10") int numberOfElements) {
        return ResponseEntity.ok().body(workoutHistoryService.getLatestWorkoutHistory(pageNo, numberOfElements));
    }
}
