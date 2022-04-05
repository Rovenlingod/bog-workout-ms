package com.example.bogworkoutms.service;

import com.example.bogworkoutms.domain.Workout;
import com.example.bogworkoutms.domain.WorkoutHistory;
import com.example.bogworkoutms.dto.WorkoutHistoryDTO;
import com.example.bogworkoutms.feign.feignDtos.UserDetailsDTO;
import com.example.bogworkoutms.mapper.WorkoutHistoryMapper;
import com.example.bogworkoutms.repository.WorkoutHistoryRepository;
import com.example.bogworkoutms.repository.WorkoutRepository;
import com.example.bogworkoutms.utulities.CustomUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class WorkoutHistoryServiceImpl implements WorkoutHistoryService {

    private final WorkoutHistoryMapper workoutHistoryMapper;
    private final WorkoutHistoryRepository workoutHistoryRepository;
    private final WorkoutRepository workoutRepository;

    public WorkoutHistoryServiceImpl(WorkoutHistoryMapper workoutHistoryMapper,
                                     WorkoutHistoryRepository workoutHistoryRepository,
                                     WorkoutRepository workoutRepository) {
        this.workoutHistoryMapper = workoutHistoryMapper;
        this.workoutHistoryRepository = workoutHistoryRepository;
        this.workoutRepository = workoutRepository;
    }

    @Override
    public WorkoutHistoryDTO saveWorkoutHistory(String workoutId) {
        Workout requestedWorkout = workoutRepository.findById(UUID.fromString(workoutId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Workout with id: " + workoutId + " does not exist."));
        UserDetailsDTO currentUser = CustomUtils.getCurrentUser()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Couldn't find user in context. Contact support."));
        WorkoutHistory newWorkoutHistory = WorkoutHistory.builder()
                .workout(requestedWorkout)
                .completionTimestamp(new Date())
                .userId(UUID.fromString(currentUser.getUuid()))
                .build();
        WorkoutHistory createdWorkoutHistory = workoutHistoryRepository.save(newWorkoutHistory);
        return workoutHistoryMapper.toDTO(createdWorkoutHistory);
    }

    @Override
    public List<WorkoutHistoryDTO> getLatestWorkoutHistory(int pageNo, int numberOfElements) {
        return workoutHistoryRepository.findAllByOrderByCompletionTimestampDesc(PageRequest.of(pageNo, numberOfElements))
                .stream()
                .map(workoutHistoryMapper::toDTO)
                .collect(Collectors.toList());
    }
}
