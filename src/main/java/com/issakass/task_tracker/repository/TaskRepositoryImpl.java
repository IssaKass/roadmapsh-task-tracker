package com.issakass.task_tracker.repository;

import com.issakass.task_tracker.exception.NoSuchTaskException;
import com.issakass.task_tracker.mapper.TaskMapper;
import com.issakass.task_tracker.model.Task;
import com.issakass.task_tracker.model.TaskStatus;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Author: abdallah-issakass
 */
public class TaskRepositoryImpl implements TaskRepository {
    private final Path path;
    private final List<Task> tasks;

    public TaskRepositoryImpl(Path path) throws IOException {
        this.path = path;
        this.tasks = new ArrayList<>(loadTasks());
    }

    @Override
    public List<Task> loadTasks() throws IOException {
        if (!Files.exists(path)) {
            return new ArrayList<>();
        }

        return TaskMapper.jsonToTaskList(Files.readString(path));
    }

    @Override
    public void saveTasks() throws IOException {
        if (!Files.exists(path)) {
            Files.createFile(path);
        }

        Files.writeString(path, TaskMapper.taskListToJson(tasks));
    }

    @Override
    public Optional<Task> findById(int id) {
        return tasks.stream()
                .filter(task -> task.getId() == id)
                .findFirst();
    }

    @Override
    public int addTask(String description) {
        Task task = new Task(generateNextId(), description);
        tasks.add(task);
        return task.getId();
    }

    @Override
    public void updateTaskDescription(int id, String description) {
        Task task = findById(id)
                .orElseThrow(() -> new NoSuchTaskException("Task with ID " + id + "not found."));
        task.setDescription(description);
    }

    @Override
    public void deleteTask(int id) {
        Task task = findById(id)
                .orElseThrow(() -> new NoSuchTaskException("Task with ID " + id + "not found."));
        tasks.remove(task);
    }

    @Override
    public void markTaskStatus(int id, TaskStatus status) {
        Task task = findById(id)
                .orElseThrow(() -> new NoSuchTaskException("Task with ID " + id + "not found."));
        task.setStatus(status);
    }

    @Override
    public List<Task> findAll() {
        return Collections.unmodifiableList(tasks);
    }

    @Override
    public List<Task> findByStatus(TaskStatus status) {
        return tasks.stream()
                .filter(task -> task.getStatus() == status)
                .toList();
    }

    private int generateNextId() {
        return tasks.stream()
                       .mapToInt(Task::getId)
                       .max()
                       .orElse(0) + 1;
    }

}
