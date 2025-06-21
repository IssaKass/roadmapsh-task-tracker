package com.issakass.task_tracker;

import com.issakass.task_tracker.cli.TaskManagerCLI;
import com.issakass.task_tracker.repository.TaskRepository;
import com.issakass.task_tracker.repository.TaskRepositoryImpl;
import com.issakass.task_tracker.service.TaskService;
import com.issakass.task_tracker.service.TaskServiceImpl;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Author: abdallah-issakass
 */
public class Application {
    private static final String PATH = "tasks.json";

    public static void main(String[] args) throws IOException {
        TaskRepository repo = new TaskRepositoryImpl(Path.of(PATH));
        TaskService service = new TaskServiceImpl(repo);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                repo.saveTasks();
                System.out.println("Saving...");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }));

        new TaskManagerCLI(service).run();
    }
}
