package console;

import core.Status;
import core.TaskService;

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
        while (true) {
            System.out.println("=== Task CLI ===");
            System.out.println("1. Add Task");
            System.out.println("2. Update Task");
            System.out.println("3. Delete Task");
            System.out.println("4. Mark task as in progress");
            System.out.println("5. Mark task as done");
            System.out.println("6. List all tasks");
            System.out.println("7. List tasks by status");
            System.out.println("8. Exit");

            System.out.print("\nEnter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter the description: ");
                    String description = scanner.nextLine();

                    taskService.addTask(description);
                    System.out.println("Task added successfully!");
                }
                case 2 -> {
                    System.out.print("Enter the id: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Enter the description: ");
                    String description = scanner.nextLine();

                    taskService.updateDescription(id, description);
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

                    taskService.updateStatus(id, Status.IN_PROGRESS);
                    System.out.println("Task marked as in-progress");
                }
                case 5 -> {
                    System.out.print("Enter the id: ");
                    int id = scanner.nextInt();

                    taskService.updateStatus(id, Status.DONE);
                    System.out.println("Task marked as done");
                }
                case 6 -> taskService.listTasks();
                case 7 -> {
                    System.out.print("Enter the status: ");
                    String status = scanner.nextLine();

                    taskService.listTasks(status);
                }
                case 8 -> {
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }


}
