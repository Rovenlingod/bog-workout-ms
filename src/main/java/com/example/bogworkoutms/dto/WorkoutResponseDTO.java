package com.example.bogworkoutms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutResponseDTO {
    private String workoutId;
    private String userId;
    private String title;
    private List<RoundDTO> roundDTOs;
}
