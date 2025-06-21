package com.issakass.task_tracker.service;

import com.issakass.task_tracker.model.Task;
import com.issakass.task_tracker.model.TaskStatus;
import com.issakass.task_tracker.repository.TaskRepository;

import java.io.IOException;
import java.util.List;

/**
 * Author: abdallah-issakass
 */
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) throws IOException {
        this.taskRepository = taskRepository;
    }

    @Override
    public void saveTasks() throws IOException {
        taskRepository.saveTasks();
    }

    @Override
    public int addTask(String description) {
        return taskRepository.addTask(description);
    }

    @Override
    public void updateTask(int id, String description) {
        taskRepository.updateTaskDescription(id, description);
    }

    @Override
    public void deleteTask(int id) {
        taskRepository.deleteTask(id);
    }

    @Override
    public void markTaskAsTodo(int id) {
        taskRepository.markTaskStatus(id, TaskStatus.TODO);
    }

    @Override
    public void markTaskAsInProgress(int id) {
        taskRepository.markTaskStatus(id, TaskStatus.IN_PROGRESS);
    }

    @Override
    public void markTaskAsDone(int id) {
        taskRepository.markTaskStatus(id, TaskStatus.DONE);
    }

    @Override
    public List<Task> findAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public List<Task> findTodoTasks() {
        return taskRepository.findByStatus(TaskStatus.TODO);
    }

    @Override
    public List<Task> findInProgressTasks() {
        return taskRepository.findByStatus(TaskStatus.IN_PROGRESS);
    }

    @Override
    public List<Task> findDoneTasks() {
        return taskRepository.findByStatus(TaskStatus.DONE);
    }
}
