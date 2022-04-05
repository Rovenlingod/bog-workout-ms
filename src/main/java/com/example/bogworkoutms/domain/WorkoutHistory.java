package com.example.bogworkoutms.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "history")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WorkoutHistory {
    @Id
    @Column(name = "history_id")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID historyId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "workout_id")
    private Workout workout;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "completion_timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date completionTimestamp;
}
