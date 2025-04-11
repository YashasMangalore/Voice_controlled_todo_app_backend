package com.todo.voice.controller;

import com.todo.voice.analyzers.MyAnalyzer;
import com.todo.voice.model.Task;
import com.todo.voice.service.TaskService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("/api/task")
public class TaskController {
    final TaskService taskService;
    final MyAnalyzer myAnalyzer;
    @PostMapping("/add")
    public ResponseEntity<Task> createTask(@RequestBody Task task){
        if(task==null)
        {
            throw new RuntimeException();
        }
        Task createdTask=taskService.createTask(task);
        return ResponseEntity.ok(createdTask);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Task> getTask(@PathVariable Long id){

        Task createdTask=taskService.getTask(id);
        if(createdTask==null)
        {
            throw new RuntimeException();
        }
        return ResponseEntity.ok(createdTask);
    }
    @GetMapping("/find/all")
    public ResponseEntity<List<Task>> getAllTask(){

        List<Task> createdTask=taskService.getAllTasks();
        if(createdTask==null)
        {
            throw new RuntimeException();
        }
        return ResponseEntity.ok(createdTask);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task task){

        Task createdTask=taskService.updateTask(id,task);
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
