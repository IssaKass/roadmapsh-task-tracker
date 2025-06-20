package com.issakass.task_tracker.console;

import com.issakass.task_tracker.core.repository.TaskRepositoryImpl;
import com.issakass.task_tracker.core.service.TaskServiceImpl;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Author: abdallah-issakass
 */
public class MainConsole {
    private static final String PATH = "tasks.json";

    public static void main(String[] args) throws IOException {
        new TaskConsoleUI(new TaskServiceImpl(new TaskRepositoryImpl(Path.of(PATH)))).run();
    }
}
