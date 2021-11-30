package com.example.bogworkoutms.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@IdClass(value = UserWorkoutKey.class)
@Table(name = "user_workout")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserWorkout {
    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "workout_id")
    private Workout workout;

    @Id
    private UUID userId;
}
