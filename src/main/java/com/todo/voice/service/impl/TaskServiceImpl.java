package com.todo.voice.service.impl;

import com.todo.voice.analyzers.MyAnalyzer;
import com.todo.voice.model.Task;
import com.todo.voice.repository.TaskRepository;
import com.todo.voice.service.TaskService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskServiceImpl implements TaskService {
    final TaskRepository taskRepository;
    final MyAnalyzer myAnalyzer;
    @Override
    public Task createTask(String operation,String task,String urgency,String dateTime) {
        String newTask=myAnalyzer.stem(task);
        return taskRepository.save( Task.builder()
                .operation(operation)
                .urgency(urgency)
                .dateTime(dateTime)
                .task(newTask)
                .build());
    }

    @Override
    public Task getTask(Long id) {
        return taskRepository.findById(id).orElse(null);
    }

    @Override
    public List<Task> getTaskByUserId(Long id){
        if(taskRepository.existsById(id)){
            Optional<List<Task>> tasks=taskRepository.findAllById(id);
            if (tasks.isPresent()) {
                return tasks.get();
            }
        }
        return null;
    }

    @Override
    public Task updateTask(Long id, String operation,String task,String urgency,String dateTime) {
        Task existingTask=taskRepository.findById(id).orElse(null);
        if(existingTask!=null){
            String newTask=myAnalyzer.stem(task);
            existingTask.setTask(newTask);
            existingTask.setOperation(operation);
            existingTask.setUpdatedAt(urgency);
            existingTask.setDateTime(dateTime);

            return taskRepository.save(existingTask);
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
