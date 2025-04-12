package com.todo.voice.repository;

import com.todo.voice.model.Task;
import com.todo.voice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional<List<Task>> findAllById(Long id);//magic methods
    boolean existById(Long id);
}
