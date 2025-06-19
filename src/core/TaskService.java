package core;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Author: abdallah-issakass
 */
public class TaskService {
    private final TaskRepository taskRepository;
    private final List<Task> tasks;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
        this.tasks = taskRepository.findAll();
    }

    public void addTask(String description) {
        Task task = new Task(description);
        tasks.add(task);
        taskRepository.saveAll(tasks);
    }

    public void updateDescription(int id, String description) {
        Task task = findById(id);
        task.setDescription(description);
        taskRepository.saveAll(tasks);
    }

    public void updateStatus(int id, Status status) {
        Task task = findById(id);
        task.setStatus(status);
        taskRepository.saveAll(tasks);
    }

    public void deleteTask(int id) {
        Task task = findById(id);
        tasks.remove(task);
        taskRepository.saveAll(tasks);
    }

    public void listTasks() {
        tasks.forEach(System.out::println);
    }

    public void listTasks(String status) {
        tasks.stream()
                .filter(task -> task.getStatus().getValue().equalsIgnoreCase(status))
                .forEach(System.out::println);
    }

    public Task findById(int id) {
        return tasks
                .stream()
                .filter(task -> task.getId() == id)
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Task with id " + id + " not found."));
    }
}
