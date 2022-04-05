package com.example.bogworkoutms;

import com.example.bogworkoutms.domain.UserWorkout;
import com.example.bogworkoutms.domain.Workout;
import com.example.bogworkoutms.domain.WorkoutHistory;
import com.example.bogworkoutms.domain.WorkoutRound;
import com.example.bogworkoutms.repository.UserWorkoutRepository;
import com.example.bogworkoutms.repository.WorkoutHistoryRepository;
import com.example.bogworkoutms.repository.WorkoutRepository;
import com.example.bogworkoutms.repository.WorkoutRoundRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class EntityTest {
    @Autowired
    private WorkoutRepository workoutRepository;
    @Autowired
    private UserWorkoutRepository userWorkoutRepository;
    @Autowired
    private WorkoutHistoryRepository workoutHistoryRepository;
    @Autowired
    private WorkoutRoundRepository workoutRoundRepository;

    @Test
    public void testWorkoutEntity() {
        Workout workout = new Workout();
        UserWorkout userWorkout = new UserWorkout();
        WorkoutRound workoutRound = new WorkoutRound();
        WorkoutHistory workoutHistory = new WorkoutHistory();

        userWorkout.setWorkout(workout);
        userWorkout.setUserId(UUID.randomUUID());

        workoutHistory.setWorkout(workout);
        workoutHistory.setUserId(userWorkout.getUserId());

        workoutRound.setWorkout(workout);
        workoutRound.setRoundId(UUID.randomUUID());

        Workout resultWorkout = workoutRepository.save(workout);
        WorkoutHistory resultWorkoutHistory = workoutHistoryRepository.save(workoutHistory);
        userWorkoutRepository.save(userWorkout);
        workoutRoundRepository.save(workoutRound);
        //UserWorkout resultUserWorkout = userWorkoutRepository.findAll().get(0);
        WorkoutRound resultWorkoutRound = workoutRoundRepository.findAll().get(0);

        //Assert.assertEquals(resultUserWorkout.getWorkout().getWorkoutId(), resultWorkout.getWorkoutId());
        Assert.assertEquals(resultWorkoutRound.getWorkout().getWorkoutId(), resultWorkout.getWorkoutId());
        Assert.assertEquals(resultWorkoutHistory.getWorkout().getWorkoutId(), resultWorkout.getWorkoutId());
    }
}
