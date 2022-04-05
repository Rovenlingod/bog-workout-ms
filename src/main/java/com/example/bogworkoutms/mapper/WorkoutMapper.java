package com.example.bogworkoutms.mapper;


import com.example.bogworkoutms.domain.Workout;
import com.example.bogworkoutms.domain.WorkoutRound;
import com.example.bogworkoutms.dto.RoundDTO;
import com.example.bogworkoutms.dto.WorkoutRequestDTO;
import com.example.bogworkoutms.dto.WorkoutResponseDTO;
import com.example.bogworkoutms.feign.RoundServiceFeign;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel="spring")
public abstract class WorkoutMapper {

    @Autowired
    private RoundServiceFeign roundServiceFeign;

    @Mappings({
            @Mapping(target = "userId", expression = "java((java.util.UUID.fromString(((com.example.bogworkoutms.feign.feignDtos.UserDetailsDTO) org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUuid())))"),
            @Mapping(target = "creationDate", expression = "java(new java.util.Date())")
    })
    public abstract Workout toEntity(WorkoutRequestDTO workoutRequestDTO);

    @Mappings({
            @Mapping(target = "userId", expression = "java(workout.getUserId().toString())"),
            @Mapping(target = "workoutId", expression = "java(workout.getWorkoutId().toString())"),
            @Mapping(target = "roundDTOs", expression = "java(this.workoutRoundsToRoundDTOs(workout.getWorkoutRounds()))")
    })
    public abstract WorkoutResponseDTO toDTO(Workout workout);

    protected List<RoundDTO> workoutRoundsToRoundDTOs(List<WorkoutRound> workoutRounds) {
        List<String> roundIds = workoutRounds.stream().map(e -> e.getRoundId().toString()).collect(Collectors.toList());
        return roundServiceFeign.getRoundByIds(roundIds);
    }
}
