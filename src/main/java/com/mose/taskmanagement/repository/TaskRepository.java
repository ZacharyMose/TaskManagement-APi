package com.mose.taskmanagement.repository;

import com.mose.taskmanagement.entity.Task;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskRepository {
     void save(Task task);
     void completeTask(Task task);
     List<Task> findAll();
     Optional<Task> findById(UUID id);
     int deleteById(UUID id);
}
