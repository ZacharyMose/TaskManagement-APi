package com.mose.taskmanagement.repository;

import com.mose.taskmanagement.entity.Task;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.sql.JDBCType;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
public class TaskRepositoryImpl implements TaskRepository {

     private final JdbcClient jdbcClient;

     public TaskRepositoryImpl(JdbcClient jdbcClient) {
          this.jdbcClient = jdbcClient;
     }
     @Override
     public void save(Task task) {
          jdbcClient.sql("INSERT INTO tasks(id, title,description,start_time, due_date, task_status, completed) " +
               "VALUES (:id, :title, :description,:startTime, :dueDate, CAST(:currentStatus AS task_status), :completed)")
               .param("id",task.id())
               .param("title",task.title())
               .param("description",task.description())
               .param("startTime", Timestamp.from(task.startTime()))
               .param("dueDate",Timestamp.from(task.dueDate()))
               .param("currentStatus",task.currentStatus().name())
               .param("completed",task.completed())
               .update();

     }

     @Override
     public void completeTask(Task task) {
          String sql = """
               UPDATE tasks SET title = :title,  description = :description, start_time = :startTime, due_date = :dueDate,
               task_status = CAST(:currentStatus AS task_status), completed = :completed WHERE id = :id;
               """;
          jdbcClient.sql(sql)
               .param("id",task.id())
               .param("title",task.title())
               .param("description",task.description())
               .param("startTime", Timestamp.from(task.startTime()))
               .param("dueDate",Timestamp.from(task.dueDate()))
               .param("currentStatus",task.currentStatus().name())
               .param("completed",task.completed())
               .update();
     }

     @Override
     public List<Task> findAll() {
          String sql = "SELECT id,title, description, start_time, due_date, task_status AS current_status, completed FROM tasks ORDER BY start_time DESC";

          return jdbcClient.sql(sql)
               .query(Task.class)
               .list();
     }

     @Override
     public Optional<Task> findById(UUID id) {
          String sql = "SELECT id, title, description, start_time, due_date, task_status AS current_status, completed FROM tasks WHERE id = :id";

          return jdbcClient.sql(sql)
               .param("id",id) // named parameters prevent SQL injection
               .query(Task.class)
               .optional();
     }

     @Override
     public int deleteById(UUID id) {
          String sql = "DELETE FROM tasks WHERE id = :id";
          return jdbcClient.sql(sql)
               .param("id",id)
               .update();
     }
}
