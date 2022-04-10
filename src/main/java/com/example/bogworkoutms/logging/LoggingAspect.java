package com.example.bogworkoutms.logging;

import com.example.bogworkoutms.dto.WorkoutHistoryDTO;
import com.example.bogworkoutms.dto.WorkoutResponseDTO;
import com.example.bogworkoutms.feign.feignDtos.UserDetailsDTO;
import com.example.bogworkoutms.utulities.CustomUtils;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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
        String username = getUsername();
        log.info("User with username \"" + username + "\" got access to the controller method " + joinPoint.getSignature());
    }

    @AfterReturning(value = "execution(* com.example.bogworkoutms.service.WorkoutService.createWorkout(..))", returning = "id")
    public void logWorkoutCreation(JoinPoint joinPoint, UUID id) {
        String username = getUsername();
        log.info("Workout with id \"" + id + "\" was successfully created by user \"" + username + "\".");
    }

    @AfterReturning(value = "execution(* com.example.bogworkoutms.service.WorkoutService.findByWorkoutId(..))", returning = "workout")
    public void logWorkoutGet(JoinPoint joinPoint, WorkoutResponseDTO workout) {
        log.info("Workout with id \"" + workout.getWorkoutId() + "\" was successfully returned.");
    }

    @After(value = "execution(* com.example.bogworkoutms.service.WorkoutService.subscribeToWorkout(..)) && args(workoutId,..)")
    public void logSubscribeToWorkout(JoinPoint joinPoint, String workoutId) {
        String username = getUsername();
        log.info("User with username \"" + username + "\" successfully subscribed to workout with id " + workoutId);
    }

    @After(value = "execution(* com.example.bogworkoutms.service.WorkoutService.unsubscribeFromWorkout(..)) && args(workoutId,..)")
    public void logUnsubscribeToWorkout(JoinPoint joinPoint, String workoutId) {
        String username = getUsername();
        log.info("User with username \"" + username + "\" successfully unsubscribed from workout with id " + workoutId);
    }

    @AfterReturning(value = "execution(* com.example.bogworkoutms.service.WorkoutService.getSubscribedWorkouts(..))", returning = "workoutResponseDTOS")
    public void logGetSubscribedWorkouts(JoinPoint joinPoint, List<WorkoutResponseDTO> workoutResponseDTOS) {
        String username = getUsername();
        String idList = workoutResponseDTOS.stream().map(WorkoutResponseDTO::getWorkoutId).collect(Collectors.toList()).toString();
        log.info("User with username \"" + username + "\" got list of his subscribed workouts with ids " + idList);
    }

    @AfterReturning(value = "execution(* com.example.bogworkoutms.service.WorkoutService.getLatest(..))", returning = "workoutResponseDTOS")
    public void logGetLatest(JoinPoint joinPoint, List<WorkoutResponseDTO> workoutResponseDTOS) {
        String username = getUsername();
        String idList = workoutResponseDTOS.stream().map(WorkoutResponseDTO::getWorkoutId).collect(Collectors.toList()).toString();
        log.info("User with username \"" + username + "\" got list of latest workouts with ids " + idList);
    }

    @AfterReturning(value = "execution(* com.example.bogworkoutms.service.WorkoutHistoryService.saveWorkoutHistory(..))", returning = "workoutHistoryDTO")
    public void logSaveWorkoutHistory(JoinPoint joinPoint, WorkoutHistoryDTO workoutHistoryDTO) {
        String username = getUsername();
        log.info("User with username \"" + username + "\" saved history with id " + workoutHistoryDTO.getHistoryId() + " of workout with id  " + workoutHistoryDTO.getWorkoutId());
    }

    @AfterReturning(value = "execution(* com.example.bogworkoutms.service.WorkoutHistoryService.getLatestWorkoutHistory(..))", returning = "workoutHistoryDTOS")
    public void logGetLatestWorkoutHistory(JoinPoint joinPoint, List<WorkoutHistoryDTO> workoutHistoryDTOS) {
        String username = getUsername();
        String idList = workoutHistoryDTOS.stream().map(WorkoutHistoryDTO::getHistoryId).collect(Collectors.toList()).toString();
        log.info("User with username \"" + username + "\" got workout histories with ids " + idList);
    }

    private String getUsername() {
        Optional<UserDetailsDTO> currentUser = CustomUtils.getCurrentUser();
        return currentUser.isPresent() ? currentUser.get().getUsername() : "Anonymous";
    }
}
