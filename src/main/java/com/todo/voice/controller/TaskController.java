package com.todo.voice.controller;

import com.todo.voice.analyzers.MyAnalyzer;
import com.todo.voice.model.Task;
import com.todo.voice.model.User;
import com.todo.voice.repository.UserRepository;
import com.todo.voice.service.TaskService;
import com.todo.voice.service.impl.NotificationServiceImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("/api/task")
public class TaskController {
    final TaskService taskService;
    final MyAnalyzer myAnalyzer;
    final UserRepository userRepository;
    final NotificationServiceImpl notificationService;

    @PostMapping("/add")
    public ResponseEntity<Task> createTask(@RequestBody Task task){
        if(task==null)
        {
            throw new RuntimeException();
        }
        Task createdTask=taskService.createTask(task.getOperation(),task.getTask(),task.getUrgency(),task.getDateTime(),task.getId());
        if(createdTask!=null && createdTask.getId()!=null){
            Optional<User> user=userRepository.findById(createdTask.getId());
            user.ifPresent(userData->notificationService.sendTaskNotification(createdTask,userData,false));
        }
        return ResponseEntity.ok(createdTask);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<List<Task>> getTask(@PathVariable Long userId){
        List<Task> tasks=taskService.getTaskByUserId(userId);
        return ResponseEntity.ok(tasks);
    }
    @GetMapping("/filter")
    public ResponseEntity<List<Task>> getTasksByDateRange(@RequestParam(required = true) Long userId, @RequestParam(required = false) Integer days){
        List<Task> tasks=taskService.getTasksByDateRange(userId,days);
        return ResponseEntity.ok(tasks);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task task){
        if(task==null)
        {
            throw new RuntimeException();
        }
        Task createdTask=taskService.updateTask(id,task.getOperation(),task.getTask(),task.getUrgency(),task.getDateTime());
        if(createdTask==null)
        {
            throw new RuntimeException();
        }
        return ResponseEntity.ok(createdTask);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteTask(Long id){
        Boolean createdTask=taskService.deleteTask(id);
        if(!createdTask)
        {
            throw new RuntimeException();
        }
        return new ResponseEntity<>("Successfully deleted task with id: "+id, HttpStatus.ACCEPTED);
    }
}


//    @GetMapping("/find/{id}")
//    public ResponseEntity<Task> getTaskB(@PathVariable Long id){
//
//        Task createdTask=taskService.getTask(id);
//        if(createdTask==null)
//        {
//            throw new RuntimeException();
//        }
//        return ResponseEntity.ok(createdTask);
//    }