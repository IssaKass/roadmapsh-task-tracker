package com.issakass.task_tracker.core.service;

import com.issakass.task_tracker.core.model.Task;
import com.issakass.task_tracker.core.model.TaskStatus;
import com.issakass.task_tracker.core.repository.TaskRepository;
import com.issakass.task_tracker.exception.NoSuchTaskException;

import java.io.IOException;
import java.util.List;

/**
 * Author: abdallah-issakass
 */
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final List<Task> tasks;

    public TaskServiceImpl(TaskRepository taskRepository) throws IOException {
        this.taskRepository = taskRepository;
        this.tasks = taskRepository.findTasks();
    }

    @Override
    public void saveTasks() throws IOException {
        taskRepository.saveTasks(tasks);
    }

    @Override
    public int addTask(String description) {
        Task task = new Task(description);
        tasks.add(task);
        return task.getId();
    }

    @Override
    public void updateTask(int id, String description) {
        Task task = findById(id);
        task.setDescription(description);
    }

    @Override
    public void deleteTask(int id) {
        Task task = findById(id);
        tasks.remove(task);
    }

    @Override
    public void markTaskAsTodo(int id) {
        markTaskStatus(id, TaskStatus.TODO);
    }

    @Override
    public void markTaskAsInProgress(int id) {
        markTaskStatus(id, TaskStatus.IN_PROGRESS);
    }

    @Override
    public void markTaskAsDone(int id) {
        markTaskStatus(id, TaskStatus.DONE);
    }

    @Override
    public List<Task> findAllTasks() {
        return tasks;
    }

    @Override
    public List<Task> findTodoTasks() {
        return getTasksByStatus(TaskStatus.TODO);
    }

    @Override
    public List<Task> findInProgressTasks() {
        return getTasksByStatus(TaskStatus.IN_PROGRESS);
    }

    @Override
    public List<Task> findDoneTasks() {
        return getTasksByStatus(TaskStatus.DONE);
    }

    private Task findById(int id) {
        return tasks
                .stream()
                .filter(task -> task.getId() == id)
                .findFirst()
                .orElseThrow(() -> new NoSuchTaskException("Task with id " + id + " not found."));
    }

    private void markTaskStatus(int id, TaskStatus newStatus) {
        Task originalTask = findById(id);
        originalTask.setStatus(newStatus);
    }

    private List<Task> getTasksByStatus(TaskStatus status) {
        return tasks
                .stream()
                .filter(task -> task.getStatus().equals(status))
                .toList();
    }
}
