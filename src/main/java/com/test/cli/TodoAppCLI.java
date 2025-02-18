package com.test.cli;

import com.test.entity.Task;
import com.test.enums.Status;
import com.test.service.TodoAppService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.test.enums.Status.*;

public class TodoAppCLI {

    private final Scanner sc;

    private final TodoAppService todoAppService;

    private final TodoAppPrinter appPrinter;

    public TodoAppCLI(TodoAppService todoAppService) {
        this.sc = new Scanner(System.in);
        this.todoAppService = todoAppService;
        this.appPrinter = new TodoAppPrinter();
    }

    public void runApp() {
        printUsage();

        boolean running = true;

        while (running){
            System.out.print("task-cli ");

            List<String> input = splitString(sc.nextLine());

            try {
                switch (input.get(0).toLowerCase()) {
                    case "add":
                        if (input.size() < 2) {
                            System.out.println("Error: Missing task description.");
                            break;
                        }

                        add(input.get(1));

                        break;
                    case "update":
                        if (input.size() < 3) {
                            System.out.println("Error: Missing task ID or new description.");
                            break;
                        }

                        update(Long.parseLong(input.get(1)), input.get(2));

                        break;
                    case "delete":
                        if (input.size() < 2) {
                            System.out.println("Error: Missing task ID.");
                            break;
                        }

                        delete(Long.parseLong(input.get(1)));

                        break;
                    case "mark-todo":
                        if (input.size() < 2) {
                            System.out.println("Error: Missing task ID or status.");
                            break;
                        }

                        updateStatus(Long.parseLong(input.get(1)),TODO);

                        break;
                    case "mark-in-progress":
                        if (input.size() < 2) {
                            System.out.println("Error: Missing task ID or status.");
                            break;
                        }
                        updateStatus(Long.parseLong(input.get(1)), IN_PROGRESS);
                        break;
                    case "mark-done":
                        if (input.size() < 2) {
                            System.out.println("Error: Missing task ID or status.");
                            break;
                        }
                        updateStatus(Long.parseLong(input.get(1)), DONE);
                        break;
                    case "list":
                        if (input.size() < 2) {
                            getAll();
                            break;
                        }

                        getListTask(input.get(1));
                        break;
                    case "help":
                        printUsage();
                        break;
                    case "exist":
                        running = false;
                        break;
                    default:
                        System.out.println("Error: Invalid action.");
                        printUsage();
                }

                todoAppService.save();
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private List<String> splitString(String input) {
        List<String> result = new ArrayList<>();
        String regex = "\"[^\"]*\"|\\S+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            String part = matcher.group();

            if (part.startsWith("\"") && part.endsWith("\"")) {
                part = part.substring(1, part.length() - 1);
            }
            result.add(part);
        }

        return result;
    }

    private void add(String description) {
        Task task = todoAppService.add(description);

        System.out.println("Task added: ");
        appPrinter.printTask(task);
    }

    private void update(long id, String description) {
        Task task = todoAppService.update(id, description);

        System.out.println("Task updated: ");
        appPrinter.printTask(task);
    }

    private void delete(long id) {
        todoAppService.delete(id);

        System.out.println("Task deleted successfully!");
    }

    private void updateStatus(long id, Status status) {
        Task task = todoAppService.updateStatus(id, status);

        System.out.println("Task updated status" +
                status.toString()
                        .toLowerCase()
                        .replace("_","-") +
                " : ");

        appPrinter.printTask(task);
    }

    private void getListTask(String s) {
        switch (s.toLowerCase()) {
            case "all":
                getAll();
                break;
            case "in-progress":
                appPrinter.printTasks(todoAppService.getByStatus(IN_PROGRESS));
                break;
            case "done":
                appPrinter.printTasks(todoAppService.getByStatus(DONE));
                break;
            case "todo":
                appPrinter.printTasks(todoAppService.getByStatus(TODO));
                break;
            default:
                System.out.println("Error: Invalid action.");
                break;
        }
    }

    private void getAll() {
        appPrinter.printTasks(todoAppService.getAll());
    }

    private void printUsage() {
        System.out.println("Usage: java TodoAppCLI <action> [arguments]");
        System.out.println("Actions:");
        System.out.println("  add \"<description>\"       - Add a new task");
        System.out.println("  update <id> \"<description>\" - Update a task's description");
        System.out.println("  delete <id>            - Delete a task");
        System.out.println("  mark-in-progress <id>  - Update a task's status in_progress");
        System.out.println("  mark-done <id>  - Update a task's status done");
        System.out.println("  getAll [filter]          - List tasks (all, todo, in_progress, done)");
        System.out.println("  exist         - Exist program");
    }
}
