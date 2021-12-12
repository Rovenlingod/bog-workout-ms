package com.example.bogworkoutms.dto;

import com.example.bogworkoutms.enums.RoundType;
import com.example.bogworkoutms.feign.feignDtos.ExerciseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoundDTO {
    private String id;
    private RoundType roundType;
    private List<ExerciseDTO> exercises;
}
