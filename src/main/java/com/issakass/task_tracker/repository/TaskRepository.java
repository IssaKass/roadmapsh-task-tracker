package com.issakass.task_tracker.repository;

import com.issakass.task_tracker.model.Task;
import com.issakass.task_tracker.model.TaskStatus;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Author: abdallah-issakass
 */
public interface TaskRepository {

    List<Task> loadTasks() throws IOException;

    void saveTasks() throws IOException;

    Optional<Task> findById(int id);

    int addTask(String description);

    void updateTaskDescription(int id, String description);

    void deleteTask(int id);

    void markTaskStatus(int id, TaskStatus status);

    List<Task> findAll();

    List<Task> findByStatus(TaskStatus status);
}
