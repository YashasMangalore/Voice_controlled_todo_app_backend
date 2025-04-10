package com.todo.voice.service;

import com.todo.voice.model.Task;
import java.util.List;


public interface TaskService {
    Task createTask(Task task);
    Task getTask(Long id);
    List<Task> getAllTasks();
    Task updateTask(Long id, Task task);
    Boolean deleteTask(Long id);
}
