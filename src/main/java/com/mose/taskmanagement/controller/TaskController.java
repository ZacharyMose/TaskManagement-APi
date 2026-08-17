package com.mose.taskmanagement.controller;

import com.mose.taskmanagement.dto.CreateTaskRequest;
import com.mose.taskmanagement.entity.Task;
import com.mose.taskmanagement.exception.ApiErrorResponse;
import com.mose.taskmanagement.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/task")
public class TaskController {
     private TaskService  taskService;

     public TaskController(TaskService taskService) {
          this.taskService = taskService;
     }

     @PostMapping("/add")
     @ResponseStatus(HttpStatus.CREATED)
     public Task  addTask(@RequestBody CreateTaskRequest request) {
                    return taskService.createTask(request);
     }

     @GetMapping
     public List<Task> findAllTasks() {
          return taskService.getAllTasks();
     }
     @GetMapping("/{id}")
     public Task findTaskById(@PathVariable UUID id) {
          return taskService.getTaskById(id);
     }

     @PatchMapping("/{id}/complete")
     public Task completeTask(@PathVariable UUID id) {
          return taskService.completeTask(id);
     }

     @DeleteMapping("/{id}")
     public void deleteTask(@PathVariable UUID id) {
          taskService.deleteTask(id);
     }
}
