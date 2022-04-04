package com.example.bogworkoutms.repository;

import com.example.bogworkoutms.domain.UserWorkout;
import com.example.bogworkoutms.domain.UserWorkoutKey;
import com.example.bogworkoutms.domain.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserWorkoutRepository extends JpaRepository<UserWorkout, UserWorkoutKey> {

    boolean existsByUserIdAndWorkout(UUID userId, Workout workout);
    void deleteByUserIdAndWorkout(UUID userId, Workout workout);
}
