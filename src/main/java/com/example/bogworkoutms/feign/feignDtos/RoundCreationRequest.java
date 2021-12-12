package com.example.bogworkoutms.feign.feignDtos;

import com.example.bogworkoutms.enums.RoundType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoundCreationRequest {
    private RoundType roundType;
    private List<String> exerciseIds;
}
