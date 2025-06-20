package com.issakass.task_tracker.console;

import com.issakass.task_tracker.core.model.Task;
import com.issakass.task_tracker.core.service.TaskService;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * Author: abdallah-issakass
 */
public class TaskConsoleUI {
    private final TaskService taskService;
    private final Scanner scanner = new Scanner(System.in);

    public TaskConsoleUI(TaskService taskService) {
        this.taskService = taskService;
    }

    public void run() {
        int choice = 0;

        while (choice != 11) {
            System.out.println("=== Task CLI ===");
            System.out.println("1. Add Task");
            System.out.println("2. Update Task");
            System.out.println("3. Delete Task");
            System.out.println("4. Mark task as todo");
            System.out.println("5. Mark task as in-progress");
            System.out.println("6. Mark task as done");
            System.out.println("7. List all tasks");
            System.out.println("8. List todo tasks");
            System.out.println("9. List in-progress tasks");
            System.out.println("10. List done tasks");
            System.out.println("11. Exit");

            System.out.print("\nEnter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> add();
                case 2 -> {
                    System.out.print("Enter the id: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Enter the description: ");
                    String description = scanner.nextLine();

                    taskService.updateTask(id, description);
                    System.out.println("Task updated successfully!");
                }
                case 3 -> {
                    System.out.print("Enter the id: ");
                    int id = scanner.nextInt();

                    taskService.deleteTask(id);
                    System.out.println("Task deleted successfully!");
                }
                case 4 -> {
                    System.out.print("Enter the id: ");
                    int id = scanner.nextInt();

                    taskService.markTaskAsTodo(id);
                    System.out.println("Task marked as todo");
                }
                case 5 -> {
                    System.out.print("Enter the id: ");
                    int id = scanner.nextInt();

                    taskService.markTaskAsInProgress(id);
                    System.out.println("Task marked as in-progress");
                }
                case 6 -> {
                    System.out.print("Enter the id: ");
                    int id = scanner.nextInt();

                    taskService.markTaskAsDone(id);
                    System.out.println("Task marked as done");
                }
                case 7 -> listTasks(taskService.findAllTasks());
                case 8 -> listTasks(taskService.findTodoTasks());
                case 9 -> listTasks(taskService.findInProgressTasks());
                case 10 -> listTasks(taskService.findDoneTasks());
                default -> System.out.println("Invalid choice.");
            }
        }

        exit();
    }

    private void add() {
        System.out.print("Enter the description: ");
        String description = scanner.nextLine().trim();

        taskService.addTask(description);
        System.out.println("Task added successfully!");
    }

    private void exit() {
        try {
            System.out.println("Saving your tasks...");
            taskService.saveTasks();
            System.out.println("GoodBye!");
        } catch (IOException e) {
            System.err.println("Something went wrong while saving your tasks!");
        }
    }

    private void listTasks(List<Task> tasks) {
        tasks.forEach(System.out::println);
    }

}
