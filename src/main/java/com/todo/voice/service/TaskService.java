package com.todo.voice.service;

import com.todo.voice.model.Task;

import java.util.List;


public interface TaskService {
    Task createTask(String operation,String task,String urgency,String dateTime, Long userId);
    Task getTask(Long id);
    List<Task> getTaskByUserId(Long id);
    Task updateTask(Long id, String operation,String task,String urgency,String dateTime);
    Boolean deleteTask(Long id);
    List<Task> getTasksByDateRange(Long userId, Integer days);
}
