package com.example.bogworkoutms.service;

import com.example.bogworkoutms.dto.WorkoutRequestDTO;
import com.example.bogworkoutms.dto.WorkoutResponseDTO;

import java.util.List;
import java.util.UUID;

public interface WorkoutService {
    WorkoutResponseDTO findByWorkoutId(String id);
    UUID createWorkout(WorkoutRequestDTO workoutRequestDTO);
    WorkoutResponseDTO updateWorkout(WorkoutRequestDTO workoutRequestDTO);
    void subscribeToWorkout(String workoutId);
    void unsubscribeFromWorkout(String workoutId);
    List<WorkoutResponseDTO> getSubscribedWorkouts(int pageNo, int numberOfElements);
    List<WorkoutResponseDTO> getLatest(int pageNo, int numberOfElements);
}
