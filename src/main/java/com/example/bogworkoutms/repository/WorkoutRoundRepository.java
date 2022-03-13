package com.example.bogworkoutms.repository;

import com.example.bogworkoutms.domain.WorkoutRound;
import com.example.bogworkoutms.domain.WorkoutRoundKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutRoundRepository extends JpaRepository<WorkoutRound, WorkoutRoundKey> {
}
