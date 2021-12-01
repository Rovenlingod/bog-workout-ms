package com.example.bogworkoutms.repository;

import com.example.bogworkoutms.domain.WorkoutHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WorkoutHistoryRepository extends JpaRepository<WorkoutHistory, UUID> {
}
