package com.example.bogworkoutms.repository;

import com.example.bogworkoutms.domain.UserWorkout;
import com.example.bogworkoutms.domain.UserWorkoutKey;
import com.example.bogworkoutms.domain.Workout;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.UUID;

public interface UserWorkoutRepository extends PagingAndSortingRepository<UserWorkout, UserWorkoutKey> {

    boolean existsByUserIdAndWorkout(UUID userId, Workout workout);
    void deleteByUserIdAndWorkout(UUID userId, Workout workout);
    List<UserWorkout> findAllByUserId(UUID userId, Pageable pageable);
}
