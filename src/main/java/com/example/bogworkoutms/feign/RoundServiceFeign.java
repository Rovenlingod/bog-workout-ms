package com.example.bogworkoutms.feign;

import com.example.bogworkoutms.dto.RoundDTO;
import com.example.bogworkoutms.feign.feignDtos.RoundCreationRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(name = "round-service")
public interface RoundServiceFeign {

    @PostMapping("/api/round")
    String saveRound(RoundCreationRequest roundCreationRequest);

    @GetMapping(value = "/api/round/{ids}")
    List<RoundDTO> getRoundByIds(@PathVariable(name = "ids") List<String> ids);
}
