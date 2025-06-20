package com.issakass.task_tracker.core.service;

import com.issakass.task_tracker.core.model.Task;

import java.io.IOException;
import java.util.List;

/**
 * Author: abdallah-issakass
 */
public interface TaskService {

    void saveTasks() throws IOException;

    int addTask(String description);

    void updateTask(int id, String description);

    void deleteTask(int id);

    void markTaskAsTodo(int id);

    void markTaskAsInProgress(int id);

    void markTaskAsDone(int id);

    List<Task> findAllTasks();

    List<Task> findTodoTasks();

    List<Task> findInProgressTasks();

    List<Task> findDoneTasks();
}
