package com.issakass.task_tracker.mapper;

import com.issakass.task_tracker.model.Task;
import com.issakass.task_tracker.model.TaskStatus;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: abdallah-issakass
 */
public class TaskMapper {

    public static String taskToJson(Task task) {
        return JSONParser.toJson(taskToMap(task));
    }

    public static Task jsonToTask(String json) {
        return mapToTask(JSONParser.parseJsonObject(json));
    }

    public static String taskListToJson(List<Task> tasks) {
        List<Map<String, Object>> maps = tasks.stream()
                .map(TaskMapper::taskToMap)
                .toList();

        return JSONParser.toJson(maps);
    }

    public static List<Task> jsonToTaskList(String json) {
        return JSONParser.parseJsonArray(json)
                .stream()
                .map(TaskMapper::mapToTask)
                .toList();
    }

    private static Task mapToTask(Map<String, Object> map) {
        return new Task(
                Integer.parseInt(map.get("id").toString()),
                map.get("description").toString(),
                TaskStatus.valueOf(map.get("status").toString()),
                LocalDateTime.parse(map.get("createdAt").toString()),
                LocalDateTime.parse(map.get("updatedAt").toString())
        );
    }

    private static Map<String, Object> taskToMap(Task task) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("id", task.getId());
        map.put("description", task.getDescription());
        map.put("status", task.getStatus());
        map.put("createdAt", task.getCreatedAt());
        map.put("updatedAt", task.getUpdatedAt());
        return map;
    }
}
