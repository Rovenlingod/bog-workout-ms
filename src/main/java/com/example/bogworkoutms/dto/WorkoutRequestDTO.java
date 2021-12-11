package com.example.bogworkoutms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutRequestDTO {

    @NotBlank(message = "Title is mandatory")
    private String title;
    @NotBlank(message = "UserID is mandatory")
    private String creatorId;
    private List<String> workoutHistories;

}
