package com.example.bogworkoutms.logging;

import com.example.bogworkoutms.dto.WorkoutResponseDTO;
import com.example.bogworkoutms.feign.feignDtos.UserDetailsDTO;
import com.example.bogworkoutms.utulities.CustomUtils;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Component
@Aspect
@Log4j2
public class LoggingAspect {

    @Pointcut("within(com.example.bogworkoutms..*)")
    protected void loggingAllOperations() {
    }

    @Pointcut("execution(* com.example.bogworkoutms.controller.*.*(..))")
    protected void loggingControllerOperations() {
    }

    @AfterThrowing(pointcut = "loggingAllOperations()", throwing = "exception")
    public void logAllExceptions(JoinPoint joinPoint, Throwable exception) {
        log.error("Exception occurred while executing method \"" + joinPoint.getSignature() + "\"");
        log.error("Cause: " + exception.getCause() + ". Exception : " + exception.getClass().getName() + ". Message: " + exception.getMessage());
        log.error("Provided arguments: ");
        Object[] arguments = joinPoint.getArgs();
        for (Object a :
                arguments) {
            if (Objects.isNull(a)) {
                log.error("[null]");
            } else {
                log.error("Class name: " + a.getClass().getSimpleName() + ". Value: " + a);
            }
        }
    }

    @Before("loggingControllerOperations()")
    public void logEndpointAccess(JoinPoint joinPoint) {
        Optional<UserDetailsDTO> currentUser = CustomUtils.getCurrentUser();
        String username = currentUser.isPresent() ? currentUser.get().getUsername() : "Anonymous";
        log.info("User with username \"" + username + "\" got access to the controller method " + joinPoint.getSignature());
    }

    @AfterReturning(value = "execution(* com.example.bogworkoutms.service.WorkoutService.createWorkout(..))", returning = "id")
    public void logWorkoutCreation(JoinPoint joinPoint, UUID id) {
        Optional<UserDetailsDTO> currentUser = CustomUtils.getCurrentUser();
        String username = currentUser.isPresent() ? currentUser.get().getUsername() : "Anonymous";
        log.info("Workout with id \"" + id + "\" was successfully created by user \"" + username + "\".");
    }

    @AfterReturning(value = "execution(* com.example.bogworkoutms.service.WorkoutService.findByWorkoutId(..))", returning = "workout")
    public void logWorkoutGet(JoinPoint joinPoint, WorkoutResponseDTO workout) {
        log.info("Workout with id \"" + workout.getWorkoutId() + "\" was successfully returned.");
    }
}
