package com.issakass.task_tracker.core.repository;

import com.issakass.task_tracker.core.model.Task;

import java.io.IOException;
import java.util.List;

/**
 * Author: abdallah-issakass
 */
public interface TaskRepository {

    List<Task> findTasks() throws IOException;

    void saveTasks(List<Task> tasks) throws IOException;
}
