package com.todo.voice.repository;

import com.todo.voice.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional<List<Task>> findAllById(Long id);//magic methods
    boolean existById(Long id);
}
