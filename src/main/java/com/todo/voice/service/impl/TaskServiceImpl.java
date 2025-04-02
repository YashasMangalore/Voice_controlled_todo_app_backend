package com.todo.voice.service.impl;

import com.todo.voice.model.Task;
import com.todo.voice.repository.TaskRepository;
import com.todo.voice.service.TaskService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskServiceImpl implements TaskService {
    final TaskRepository taskRepository;
    @Override
    public Task createTask(Task task) {
        taskRepository.save(task);
        return task;
    }

    @Override
    public Task getTask(Long id) {
        return taskRepository.findById(id).orElse(null);
    }

    @Override
    public List<Task> getAllTasks() {
        return (List<Task>) taskRepository.findAll();
    }

    @Override
    public Task updateTask(Long id, Task task) {
        Task existingTask=taskRepository.findById(id).orElse(null);
        if(existingTask!=null){
            taskRepository.save(task);
            return task;
        }
        return null;
    }

    @Override
    public Boolean deleteTask(Long id) {
        if(taskRepository.existsById(id)){
            taskRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
