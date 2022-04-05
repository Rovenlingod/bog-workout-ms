package com.example.bogworkoutms.repository;

import com.example.bogworkoutms.domain.Workout;
import com.example.bogworkoutms.domain.WorkoutHistory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.UUID;

public interface WorkoutHistoryRepository extends PagingAndSortingRepository<WorkoutHistory, UUID> {
    List<WorkoutHistory> findAllByOrderByCompletionTimestampDesc(Pageable pageable);
}
