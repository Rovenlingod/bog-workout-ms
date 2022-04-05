package com.example.bogworkoutms.mapper;

import com.example.bogworkoutms.domain.Workout;
import com.example.bogworkoutms.domain.WorkoutHistory;
import com.example.bogworkoutms.dto.WorkoutHistoryDTO;
import com.example.bogworkoutms.dto.WorkoutResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel="spring")
public abstract class WorkoutHistoryMapper {

    @Mappings({
            @Mapping(target = "userId", expression = "java(workoutHistory.getUserId().toString())"),
            @Mapping(target = "workoutId", expression = "java(workoutHistory.getWorkout().getWorkoutId().toString())"),
            @Mapping(target = "historyId", expression = "java(workoutHistory.getHistoryId().toString())")
    })
    public abstract WorkoutHistoryDTO toDTO(WorkoutHistory workoutHistory);
}
