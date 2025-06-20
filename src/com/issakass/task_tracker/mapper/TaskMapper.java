package com.issakass.task_tracker.mapper;

import com.issakass.task_tracker.core.model.Task;
import com.issakass.task_tracker.core.model.TaskStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Author: abdallah-issakass
 */
public class TaskMapper {

    public static String tasksToJson(List<Task> tasks) {
        if (tasks.isEmpty()) {
            return "";
        }

        return tasks.stream()
                .map(TaskMapper::taskToJson)
                .collect(Collectors.joining(",", "[", "]"));
    }

    public static List<Task> jsonToTasks(String json) {
        if (json == null || json.isEmpty()) {
            return new ArrayList<>();
        }

        return Arrays.stream(json.substring(2, json.length() - 2).split("},\\{"))
                .map(TaskMapper::jsonToTask)
                .toList();
    }

    public static Task jsonToTask(String json) {
        String[] keyValuePairs = json.replaceAll("\"", "").split(",");

        int id = 0;
        String description = null;
        TaskStatus status = null;
        LocalDateTime createdAt = null, updatedAt = null;

        for (String keyValuePair : keyValuePairs) {
            String key = keyValuePair.substring(0, keyValuePair.indexOf(":")).trim();
            String value = keyValuePair.substring(keyValuePair.indexOf(":") + 1).trim();

            switch (key) {
                case "id" -> id = Integer.parseInt(value);
                case "description" -> description = value;
                case "status" -> status = TaskStatus.fromValue(value);
                case "createdAt" -> createdAt = LocalDateTime.parse(value);
                case "updatedAt" -> updatedAt = LocalDateTime.parse(value);
            }
        }

        return new Task(id, description, status, createdAt, updatedAt);
    }


    public static String taskToJson(Task task) {
        return """
                {
                    "id": %d,
                    "description": "%s",
                    "status": "%s",
                    "createdAt": "%s",
                    "updatedAt": "%s"
                }""".formatted(
                task.getId(),
                task.getDescription(),
                task.getStatus().getValue(),
                task.getCreatedAt(),
                task.getUpdatedAt()
        );
    }
}
