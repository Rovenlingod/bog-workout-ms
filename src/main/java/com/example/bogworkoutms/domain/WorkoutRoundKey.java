package com.example.bogworkoutms.domain;

import java.io.Serializable;
import java.util.UUID;

public class WorkoutRoundKey implements Serializable {
    private UUID workout;
    private UUID roundId;

    public UUID getRoundId() {
        return roundId;
    }

    public void setRoundId(UUID roundId) {
        this.roundId = roundId;
    }

    public UUID getWorkout() {
        return workout;
    }

    public void setWorkout(UUID workout) {
        this.workout = workout;
    }
}
