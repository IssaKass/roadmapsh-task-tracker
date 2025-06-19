package mapper;

import core.Status;
import core.Task;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: abdallah-issakass
 */
public class TaskMapper implements JSONMapper<Task> {
    @Override
    public Task fromJSON(String json) {
        String raw = json.replace("{", "")
                .replace("}", "")
                .replace("\"", "")
                .trim();

        String[] entries = raw.split(",");
        Map<String, String> data = new HashMap<>();

        for (String entry : entries) {
            String[] keyValue = entry.split(":", 2);
            if (keyValue.length == 2) {
                data.put(keyValue[0].strip(), keyValue[1].strip());
            }
        }

        int id = Integer.parseInt(data.get("id"));
        String description = data.get("description");
        Status status = Status.fromValue(data.get("status"));
        LocalDateTime createdAt = LocalDateTime.parse(data.get("createdAt"), Task.FORMATTER);
        LocalDateTime updatedAt = LocalDateTime.parse(data.get("updatedAt"), Task.FORMATTER);

        Task task = new Task(description);
        task.setId(id);
        task.setDescription(description);
        task.setStatus(status);
        task.setCreatedAt(createdAt);
        task.setUpdatedAt(updatedAt);

        return task;
    }

    @Override
    public String toJSON(Task task) {
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
                task.getCreatedAt().format(Task.FORMATTER),
                task.getUpdatedAt().format(Task.FORMATTER)
        );
    }
}
