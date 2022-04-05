package com.example.bogworkoutms.service;

import com.example.bogworkoutms.dto.WorkoutHistoryDTO;
import com.example.bogworkoutms.dto.WorkoutResponseDTO;

import java.util.List;

public interface WorkoutHistoryService {
    WorkoutHistoryDTO saveWorkoutHistory(String workoutId);
    List<WorkoutHistoryDTO> getLatestWorkoutHistory(int pageNo, int numberOfElements);
}
