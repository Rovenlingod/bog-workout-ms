package com.example.bogworkoutms.repository;

import com.example.bogworkoutms.domain.UserWorkout;
import com.example.bogworkoutms.domain.UserWorkoutKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserWorkoutRepository extends JpaRepository<UserWorkout, UserWorkoutKey> {
}
