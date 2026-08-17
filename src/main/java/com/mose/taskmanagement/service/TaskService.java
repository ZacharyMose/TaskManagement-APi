package com.mose.taskmanagement.service;

import com.mose.taskmanagement.dto.CreateTaskRequest;
import com.mose.taskmanagement.entity.Task;

import java.util.List;
import java.util.UUID;

public interface TaskService {
     Task getTaskById(UUID id);
     Task createTask(CreateTaskRequest request);
     Task completeTask(UUID id);
     void deleteTask(UUID id);
     List<Task> getAllTasks();
}
