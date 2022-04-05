package com.example.bogworkoutms.service;

import com.example.bogworkoutms.domain.UserWorkout;
import com.example.bogworkoutms.domain.UserWorkoutKey;
import com.example.bogworkoutms.domain.Workout;
import com.example.bogworkoutms.domain.WorkoutRound;
import com.example.bogworkoutms.dto.RoundDTO;
import com.example.bogworkoutms.dto.WorkoutRequestDTO;
import com.example.bogworkoutms.dto.WorkoutResponseDTO;
import com.example.bogworkoutms.feign.RoundServiceFeign;
import com.example.bogworkoutms.feign.feignDtos.RoundCreationRequest;
import com.example.bogworkoutms.feign.feignDtos.UserDetailsDTO;
import com.example.bogworkoutms.mapper.WorkoutMapper;
import com.example.bogworkoutms.repository.UserWorkoutRepository;
import com.example.bogworkoutms.repository.WorkoutHistoryRepository;
import com.example.bogworkoutms.repository.WorkoutRepository;
import com.example.bogworkoutms.repository.WorkoutRoundRepository;
import com.example.bogworkoutms.utulities.CustomUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
        UserDetailsDTO currentUser = CustomUtils.getCurrentUser()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Couldn't find user in context. Contact support."));
        List<UserWorkout> subscribers = List.of(UserWorkout.builder()
                .workout(newWorkout)
                .userId(UUID.fromString(currentUser.getUuid()))
                .build());
        newWorkout.setUserWorkouts(subscribers);
        newWorkout.setWorkoutRounds(workoutRounds);
        return workoutRepository.save(newWorkout).getWorkoutId();
    }

    @Override
    public void subscribeToWorkout(String workoutId) {
        Workout requestedWorkout = workoutRepository.findById(UUID.fromString(workoutId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is no workout with id: " + workoutId));
        UserDetailsDTO currentUser = CustomUtils.getCurrentUser()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Couldn't find user in context. Contact support."));
        if (userWorkoutRepository.existsByUserIdAndWorkout(UUID.fromString(currentUser.getUuid()), requestedWorkout)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User is already subscribed to workout with id: " + workoutId);
        } else {
            UserWorkout newSubscriber = UserWorkout.builder()
                    .workout(requestedWorkout)
                    .userId(UUID.fromString(currentUser.getUuid()))
                    .build();
            userWorkoutRepository.save(newSubscriber);
        }
    }

    @Transactional
    @Override
    public void unsubscribeFromWorkout(String workoutId) {
        Workout requestedWorkout = workoutRepository.findById(UUID.fromString(workoutId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is no workout with id: " + workoutId));
        UserDetailsDTO currentUser = CustomUtils.getCurrentUser()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Couldn't find user in context. Contact support."));
        if (!userWorkoutRepository.existsByUserIdAndWorkout(UUID.fromString(currentUser.getUuid()), requestedWorkout)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User isn't subscribed to workout with id: " + workoutId);
        } else {
            userWorkoutRepository.deleteByUserIdAndWorkout(UUID.fromString(currentUser.getUuid()), requestedWorkout);
        }
    }

    @Override
    public List<WorkoutResponseDTO> getSubscribedWorkouts(int pageNo, int numberOfElements) {
        UserDetailsDTO currentUser = CustomUtils.getCurrentUser()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Couldn't find user in context. Contact support."));
        return userWorkoutRepository.findAllByUserId(UUID.fromString(currentUser.getUuid()), PageRequest.of(pageNo, numberOfElements))
                .stream()
                .map(e -> workoutMapper.toDTO(e.getWorkout()))
                .collect(Collectors.toList());
    }

    @Override
    public List<WorkoutResponseDTO> getLatest(int pageNo, int numberOfElements) {
        return workoutRepository.findAllByOrderByCreationDateDesc(PageRequest.of(pageNo, numberOfElements))
                .stream()
                .map(workoutMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public WorkoutResponseDTO updateWorkout(WorkoutRequestDTO workoutRequestDTO) {
        return null;
    }
}
