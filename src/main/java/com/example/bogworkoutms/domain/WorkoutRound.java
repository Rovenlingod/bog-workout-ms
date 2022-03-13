package com.example.bogworkoutms.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@IdClass(value = WorkoutRoundKey.class)
@Table(name = "workout_round")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkoutRound {
    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "workout_id")
    private Workout workout;

    @Id
    private UUID roundId;
}
