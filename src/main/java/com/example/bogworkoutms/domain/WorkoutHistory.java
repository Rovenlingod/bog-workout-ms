package com.example.bogworkoutms.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "history")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkoutHistory {
    @Id
    @Column(name = "history_id")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID historyId;

    @ManyToOne
    @JoinColumn(name = "workout_id")
    private Workout workout;

    @Column(name = "user_id")
    private UUID userId;

    @DateTimeFormat
    private LocalDateTime timestamp;
}
