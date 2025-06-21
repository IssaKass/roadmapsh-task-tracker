package com.issakass.task_tracker.cli;

import com.issakass.task_tracker.exception.NoSuchTaskException;
import com.issakass.task_tracker.service.TaskService;

import java.io.IOException;
import java.util.Scanner;

/**
 * Author: abdallah-issakass
 */
public class TaskManagerCLI {
    private final Scanner scanner = new Scanner(System.in);
    private final TaskService taskService;

    public TaskManagerCLI(TaskService taskService) {
        this.taskService = taskService;
    }

    public void run() {
        printHelpMenu();

        String commandLine;
        while (true) {
            System.out.print("> ");
            commandLine = scanner.nextLine().trim();
            if (commandLine.equalsIgnoreCase("exit")) {
                break;
            }

            String[] parts = commandLine.split("\\s+", 2);
            String command = parts[0].toLowerCase();
            String args = parts.length > 1 ? parts[1] : "";
            args = args.trim();

            switch (command) {
                case "add" -> add(args);
                case "update" -> update(args);
                case "delete" -> delete(args);
                case "mark-todo" -> markTodo(args);
                case "mark-in-progress" -> markInProgress(args);
                case "mark-done" -> markDone(args);
                case "list" -> list();
                case "list-todo" -> listTodo();
                case "list-in-progress" -> listInProgress();
                case "list-done" -> listDone();
                case "help" -> printHelpMenu();
                default ->
                        System.out.println("Unknown command. Type 'help' to see the list of commands or 'exit' to exit.");
            }
        }

        exit();
    }

    private void add(String description) {
        if (description.isEmpty()) {
            System.err.println("Usage: add [description]");
            return;
        }

        int id = taskService.addTask(stripQuotes(description));
        System.out.printf("Task added successfully (ID: %d)\n", id);
    }

    private void update(String args) {
        try {
            String[] tokens = args.split("\\s+", 2);
            int id = Integer.parseInt(tokens[0]);
            String description = stripQuotes(tokens[1]);

            taskService.updateTask(id, description);
            System.out.printf("Task updated successfully (ID: %d)\n", id);
        } catch (NoSuchTaskException e) {
            System.err.println(e.getMessage());
        }
    }

    private void delete(String args) {
        try {
            int id = Integer.parseInt(args);
            taskService.deleteTask(id);
            System.out.printf("Task deleted successfully (ID: %d)\n", id);
        } catch (NoSuchTaskException e) {
            System.err.println(e.getMessage());
        }
    }

    private void markTodo(String args) {
        try {
            int id = Integer.parseInt(args);
            taskService.markTaskAsTodo(id);
            System.out.printf("Task marked as Todo (ID: %d)\n", id);
        } catch (NoSuchTaskException e) {
            System.err.println(e.getMessage());
        }
    }

    private void markInProgress(String args) {
        try {
            int id = Integer.parseInt(args);
            taskService.markTaskAsInProgress(id);
            System.out.printf("Task marked as In Progress (ID: %d)\n", id);
        } catch (NoSuchTaskException e) {
            System.err.println(e.getMessage());
        }
    }

    private void markDone(String args) {
        try {
            int id = Integer.parseInt(args);
            taskService.markTaskAsDone(id);
            System.out.printf("Task marked as Done (ID: %d)\n", id);
        } catch (NoSuchTaskException e) {
            System.err.println(e.getMessage());
        }
    }

    private void list() {
        taskService.findAllTasks().forEach(System.out::println);
    }

    private void listTodo() {
        taskService.findTodoTasks().forEach(System.out::println);
    }

    private void listInProgress() {
        taskService.findInProgressTasks().forEach(System.out::println);
    }

    private void listDone() {
        taskService.findDoneTasks().forEach(System.out::println);
    }

    private void printHelpMenu() {
        System.out.println("""
                - add [description]: Add a new task
                - update [id] [description]: Update a task
                - delete [id]: Delete a task
                - mark-todo [id]: Mark a task as Todo
                - mark-in-progress [id]: Mark a task as In-Progress
                - mark-done [id]: Mark a task as Done
                - list: List all tasks
                - list-todo: List all Todo tasks
                - list-in-progress: List all In-Progress tasks
                - list-done: List all Done tasks
                - help: Display the current help menu
                - exit: Exit the program
                """);
    }

    private void exit() {
        try {
            System.out.println("Saving the task...");
            taskService.saveTasks();
            System.out.println("Done! Goodbye!");
        } catch (IOException e) {
            System.err.println("Something went wrong and couldn't save the tasks!");
        }
    }

    private String stripQuotes(String input) {
        input = input.trim();
        if ((input.startsWith("\"") && input.endsWith("\"")) ||
            (input.startsWith("'") && input.endsWith("'"))) {
            return input.substring(1, input.length() - 1).trim();
        }
        return input;
    }

}
