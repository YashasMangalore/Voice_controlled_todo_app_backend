package com.todo.voice.service.impl;

import com.todo.voice.analyzers.MyAnalyzer;
import com.todo.voice.model.Task;
import com.todo.voice.repository.TaskRepository;
import com.todo.voice.service.TaskService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskServiceImpl implements TaskService {
    final TaskRepository taskRepository;
    final MyAnalyzer myAnalyzer;
    @Override
    public Task createTask(String operation,String task,String urgency,String dateTime, Long userId) {
        String newTask=myAnalyzer.stem(task);
        return taskRepository.save( Task.builder()
                .operation(operation)
                .id(userId)
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

    @Override
    public List<Task> getTasksByDateRange(Long userId, Integer days) {
        Optional<List<Task>> tasks=taskRepository.findAllById(userId);
        List<Task> tasks1=tasks.get();
        if(days==null || days<=0){
            return tasks1;
        }
        //dd/mm/yyyy
        LocalDateTime now=LocalDateTime.now();
        LocalDateTime endDate=now.plusDays(days);

//        List<Task> finalResponse=new ArrayList<>();
//        for(int i=0;i<tasks1.size();i++){ // .stream()
//            Task task=tasks1.get(i);
//            LocalDateTime taskData=parseDatetime(task.getDateTime());
//            if(taskData!=null &&!taskData.isBefore(now) &&!taskData.isAfter(endDate)) // .filter
//            {
//                finalResponse.add(task); //.collect(Collectors.toList());
//            }
//        }
//        return finalResponse;

        return tasks1.stream().filter(task -> {
            try{
                LocalDateTime taskData=parseDatetime(task.getDateTime());
                return taskData!=null &&
                        !taskData.isBefore(now) &&
                        !taskData.isAfter(endDate);
            } catch (Exception e) {
                return false;
            }
        }).collect(Collectors.toList());
    }
    private LocalDateTime parseDatetime(String dateTime){
        if(dateTime==null||dateTime.isEmpty()){
            return null;
        }
        try{
            return LocalDateTime.parse(dateTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        } catch (Exception e) {
            try{
                return LocalDateTime.parse(dateTime, DateTimeFormatter.ISO_DATE_TIME);
            } catch (Exception ex) {
                throw new IllegalArgumentException("Illegal Date Time format "+ex);
            }
        }
    }
}
