package cli;

import core.Status;
import core.TaskService;

/**
 * Author: abdallah-issakass
 */
public class TaskCLI {
    private final TaskService taskService;

    public TaskCLI(TaskService taskService) {
        this.taskService = taskService;
    }

    public void run(String[] args) {
        if (args.length == 0) {
            printHelp();
            return;
        }

        String command = args[0];

        switch (command) {
            case "add" -> {
                if (args.length < 2) {
                    System.err.println("Usage: taskcli add <description>");
                    return;
                }

                taskService.addTask(args[1]);
                System.out.println("Task added successfully!");
            }
            case "update" -> {
                if (args.length < 3) {
                    System.err.println("Usage: taskcli update <id> <description>");
                    return;
                }

                taskService.updateDescription(Integer.parseInt(args[1]), args[2]);
                System.out.println("Task updated successfully!");
            }
            case "delete" -> {
                if (args.length < 2) {
                    System.err.println("Usage: taskcli delete <id>");
                    return;
                }

                taskService.deleteTask(Integer.parseInt(args[1]));
                System.out.println("Task deleted successfully!");
            }
            case "mark-in-progress" -> {
                if (args.length < 2) {
                    System.err.println("Usage: taskcli mark-in-progress <id>");
                    return;
                }

                taskService.updateStatus(Integer.parseInt(args[1]), Status.IN_PROGRESS);
                System.out.println("Task marked as in-progress");
            }
            case "mark-done" -> {
                if (args.length < 2) {
                    System.err.println("Usage: taskcli mark-done <id>");
                    return;
                }

                taskService.updateStatus(Integer.parseInt(args[1]), Status.DONE);
                System.out.println("Task marked as done");
            }
            case "list" -> {
                if (args.length < 2) {
                    taskService.listTasks();
                } else {
                    taskService.listTasks(args[1]);
                }
            }
            default -> System.out.println("Invalid command.");

        }
    }

    private void printHelp() {
        System.err.println("Usage: taskcli <command> [args]");
        System.err.println("[add|update|delete|mark-in-progress|mark-done|list...]");
    }
}
