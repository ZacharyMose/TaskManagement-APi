package com.mose.taskmanagement.dto;

import java.time.Instant;

public record CreateTaskRequest(
     String title,
     String description,
     Instant startTime,
     Instant dueDate
) {}
