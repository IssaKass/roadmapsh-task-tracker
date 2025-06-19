package core;

import mapper.TaskMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: abdallah-issakass
 */
public class TaskRepository {
    private final Path filePath;
    private final TaskMapper taskMapper;

    public TaskRepository(Path filePath, TaskMapper taskMapper) {
        this.filePath = filePath;
        this.taskMapper = taskMapper;
    }

    public List<Task> findAll() {
        if (!Files.exists(filePath)) {
            return new ArrayList<>();
        }

        try {
            String json = Files.readString(filePath);

            if (json.isEmpty() || json.equals("[]")) {
                return new ArrayList<>();
            }

            List<Task> savedTasks = new ArrayList<>();
            String[] jsonEntries = json
                    .substring(1, json.length() - 1)
                    .split("},\\s*");

            for (String jsonEntry : jsonEntries) {
                if (!jsonEntry.endsWith("}")) {
                    jsonEntry = jsonEntry.concat("}");
                }
                savedTasks.add(taskMapper.fromJSON(jsonEntry));
            }

            return savedTasks;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveAll(List<Task> tasks) {
        if (tasks.isEmpty()) {
            try {
                Files.writeString(filePath, "[]");
                return;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append("[\n");
        for (int i = 0; i < tasks.size(); i++) {
            sb.append(taskMapper.toJSON(tasks.get(i)));
            if (i < tasks.size() - 1) {
                sb.append(",\n");
            }
        }
        sb.append("\n]");

        try {
            Files.writeString(filePath, sb.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
