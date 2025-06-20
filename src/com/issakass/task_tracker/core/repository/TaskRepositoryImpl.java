package com.issakass.task_tracker.core.repository;

import com.issakass.task_tracker.core.model.Task;
import com.issakass.task_tracker.mapper.TaskMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: abdallah-issakass
 */
public class TaskRepositoryImpl implements TaskRepository {
    private final Path path;

    public TaskRepositoryImpl(Path path) {
        this.path = path;
    }

    @Override
    public List<Task> findTasks() throws IOException {
        if (!Files.exists(path)) {
            return new ArrayList<>();
        }

        return TaskMapper.jsonToTasks(Files.readString(path));
    }

    @Override
    public void saveTasks(List<Task> tasks) throws IOException {
        if (!Files.exists(path)) {
            Files.createFile(path);
        }

        Files.writeString(path, TaskMapper.tasksToJson(tasks));
    }
}
