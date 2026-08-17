package com.mose.taskmanagement.service;

import com.mose.taskmanagement.dto.CreateTaskRequest;
import com.mose.taskmanagement.entity.Task;
import com.mose.taskmanagement.entity.TaskStatus;
import com.mose.taskmanagement.exception.TaskNotFoundException;
import com.mose.taskmanagement.repository.TaskRepository;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService {
     private final TaskRepository taskRepository;

     public TaskServiceImpl(TaskRepository taskRepository) {
          this.taskRepository = taskRepository;
     }

     @Override
     @Transactional(readOnly = true)
     public Task getTaskById(UUID id) {
          return taskRepository.findById(id)
               .orElseThrow(() -> new TaskNotFoundException("Task not found"));
     }

     @Override
     @Transactional
     public Task createTask(CreateTaskRequest request) {
          // Validate business rules
          if (request.title() == null || request.title().isBlank()) {
               throw new IllegalArgumentException("Title is required");
          }

          Task newtask = new Task(
               UUID.randomUUID(),
               request.title().trim(),
               request.description(),
               request.startTime(),
               request.dueDate(),
               TaskStatus.TO_DO,
               false
          );

          taskRepository.save(newtask);
          return newtask;
     }

     @Override
     @Transactional
     public Task completeTask(UUID id) {
          // fetch the data
          Task existingTask = getTaskById(id);

          // Delegate the business state to the domain object
          Task completedTask = existingTask.complete();

          // 3. save the new state
          taskRepository.completeTask(completedTask);
          return completedTask;
     }

     @Override
     @Transactional
     public void deleteTask(UUID id) {
          int rowsAffected = taskRepository.deleteById(id);
          if (rowsAffected == 0){
               throw new TaskNotFoundException("Task not found");
          }

     }

     @Override
     @Transactional(readOnly = true)
     public List<Task> getAllTasks() {
          return taskRepository.findAll();
     }
}
