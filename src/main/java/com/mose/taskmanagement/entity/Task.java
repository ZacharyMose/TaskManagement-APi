package com.mose.taskmanagement.entity;

import java.time.Instant;
import java.util.UUID;

public record Task(
     UUID id,
     String title,
     String description,
     Instant startTime,
     Instant dueDate,
     TaskStatus currentStatus,
     boolean completed
) {
     public Task complete(){
          if(this.currentStatus == TaskStatus.COMPLETED){
               throw new IllegalArgumentException("Task is already done");
          }

          return new Task(this.id, this.title, this.description, this.startTime,
               this.dueDate, TaskStatus.COMPLETED, true);
     }
}
