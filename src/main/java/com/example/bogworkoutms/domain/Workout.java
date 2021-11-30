package com.example.bogworkoutms.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "workout")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Workout {
    @Id
    @Column(name = "workout_id")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID workoutId;

    @Column(name = "title")
    private String title;

    @Column(name = "user_id")
    private UUID userId;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "workout", cascade = CascadeType.ALL)
    private List<WorkoutHistory> workoutHistories;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "workout", cascade = CascadeType.ALL)
    private List<WorkoutRound> workoutRounds;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "workout", cascade = CascadeType.ALL)
    private List<UserWorkout> userWorkouts;
}
