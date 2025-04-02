package com.todo.voice.service;

import com.todo.voice.model.Task;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
public interface TaskService {
    Task createTask(Task task);
    Task getTask(Long id);
    List<Task> getAllTasks();
    Task updateTask(Long id, Task task);
    Boolean deleteTask(Long id);
}
