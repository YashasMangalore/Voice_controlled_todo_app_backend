package com.todo.voice.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

@Data
@Entity
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String operation;
    @Column(columnDefinition = "TEXT")
    String task;
    String urgency;
    String dateTime;

    @CreationTimestamp
    @Column(updatable = false)
    String createdAt;
    @CreationTimestamp
    String updatedAt;
}
