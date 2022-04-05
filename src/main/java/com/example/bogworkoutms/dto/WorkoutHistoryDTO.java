package com.example.bogworkoutms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkoutHistoryDTO {
    private String historyId;
    private String userId;
    private String workoutId;
    private Date completionTimestamp;
}
