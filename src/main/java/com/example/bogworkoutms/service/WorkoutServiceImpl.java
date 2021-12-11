package com.example.bogworkoutms.service;

import com.example.bogworkoutms.domain.Workout;
import com.example.bogworkoutms.dto.WorkoutRequestDTO;
import com.example.bogworkoutms.dto.WorkoutResponseDTO;
import com.example.bogworkoutms.repository.UserWorkoutRepository;
import com.example.bogworkoutms.repository.WorkoutHistoryRepository;
import com.example.bogworkoutms.repository.WorkoutRepository;
import com.example.bogworkoutms.repository.WorkoutRoundRepository;

import java.util.UUID;

public class WorkoutServiceImpl implements WorkoutService {
    private WorkoutRoundRepository workoutRoundRepository;
    private WorkoutRepository workoutRepository;
    private UserWorkoutRepository userWorkoutRepository;
    private WorkoutHistoryRepository workoutHistoryRepository;

    public WorkoutServiceImpl(WorkoutRoundRepository workoutRoundRepository,
                              WorkoutRepository workoutRepository,
                              UserWorkoutRepository userWorkoutRepository,
                              WorkoutHistoryRepository workoutHistoryRepository) {
        this.workoutRoundRepository = workoutRoundRepository;
        this.workoutRepository = workoutRepository;
        this.userWorkoutRepository = userWorkoutRepository;
        this.workoutHistoryRepository = workoutHistoryRepository;
    }

    @Override
    public WorkoutResponseDTO findByWorkoutId(String userId) {
        return null;
    }

    @Override
    public UUID createWorkout(WorkoutRequestDTO workoutRequestDTO) {
        Workout workout = ;
        return workoutRepository.save();
    }

    @Override
    public WorkoutResponseDTO updateWorkout(WorkoutRequestDTO workoutRequestDTO) {
        return null;
    }
}
