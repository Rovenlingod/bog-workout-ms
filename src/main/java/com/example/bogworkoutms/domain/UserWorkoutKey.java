package com.example.bogworkoutms.domain;

import java.io.Serializable;
import java.util.UUID;

public class UserWorkoutKey implements Serializable {
    private UUID workout;
    private UUID userId;

    public UUID getWorkout() {
        return workout;
    }

    public void setWorkout(UUID workout) {
        this.workout = workout;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }
}
