package com.example.bogworkoutms.service;

import com.example.bogworkoutms.domain.Workout;
import com.example.bogworkoutms.domain.WorkoutRound;
import com.example.bogworkoutms.dto.RoundDTO;
import com.example.bogworkoutms.dto.WorkoutRequestDTO;
import com.example.bogworkoutms.dto.WorkoutResponseDTO;
import com.example.bogworkoutms.feign.RoundServiceFeign;
import com.example.bogworkoutms.feign.feignDtos.RoundCreationRequest;
import com.example.bogworkoutms.mapper.WorkoutMapper;
import com.example.bogworkoutms.repository.UserWorkoutRepository;
import com.example.bogworkoutms.repository.WorkoutHistoryRepository;
import com.example.bogworkoutms.repository.WorkoutRepository;
import com.example.bogworkoutms.repository.WorkoutRoundRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class WorkoutServiceImpl implements WorkoutService {
    private WorkoutRoundRepository workoutRoundRepository;
    private WorkoutRepository workoutRepository;
    private UserWorkoutRepository userWorkoutRepository;
    private WorkoutHistoryRepository workoutHistoryRepository;
    private WorkoutMapper workoutMapper;
    private RoundServiceFeign roundServiceFeign;

    public WorkoutServiceImpl(WorkoutRoundRepository workoutRoundRepository, WorkoutRepository workoutRepository, UserWorkoutRepository userWorkoutRepository, WorkoutHistoryRepository workoutHistoryRepository, WorkoutMapper workoutMapper, RoundServiceFeign roundServiceFeign) {
        this.workoutRoundRepository = workoutRoundRepository;
        this.workoutRepository = workoutRepository;
        this.userWorkoutRepository = userWorkoutRepository;
        this.workoutHistoryRepository = workoutHistoryRepository;
        this.workoutMapper = workoutMapper;
        this.roundServiceFeign = roundServiceFeign;
    }

    @Override
    public WorkoutResponseDTO findByWorkoutId(String id) {

        return workoutMapper.toDTO(workoutRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is no workout with id: " + id)));
    }

    @Override
    public UUID createWorkout(WorkoutRequestDTO workoutRequestDTO) {
        Workout newWorkout = workoutMapper.toEntity(workoutRequestDTO);
        List<WorkoutRound> workoutRounds = new ArrayList<>();
        List<String> roundIdList = roundServiceFeign.saveRoundList(workoutRequestDTO.getRoundCreationDTOs());
        for (String stringId : roundIdList) {
            WorkoutRound newWorkoutRound = new WorkoutRound();
            newWorkoutRound.setRoundId(UUID.fromString(stringId));
            newWorkoutRound.setWorkout(newWorkout);
            workoutRounds.add(newWorkoutRound);
        }
        newWorkout.setWorkoutRounds(workoutRounds);
        return workoutRepository.save(newWorkout).getWorkoutId();
    }

    @Override
    public WorkoutResponseDTO updateWorkout(WorkoutRequestDTO workoutRequestDTO) {
        return null;
    }
}
