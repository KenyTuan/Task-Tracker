package com.test.cli;

import com.test.entity.Task;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class TodoAppPrinter {

    private static final String BORDER = "+-------+------------------------------------------+--------------+------------------+------------------+";
    private static final String HEADER = "| ID    | Description                              | Status       | Created Time     | Updated Time     |";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final String TASK_FORMAT = "| %-5s | %-40s | %-12s | %-16s | %-16s |%n";
    private static final int DESCRIPTION_MAX_LENGTH = 40;

    public void printTasks(List<Task> tasks) {
        System.out.println(BORDER);
        System.out.println(HEADER);
        System.out.println(BORDER);

        tasks.forEach(task -> {
            System.out.printf(TASK_FORMAT,
                    task.getId(),
                    truncate(task.getDescription()),
                    task.getStatus(),
                    task.getCreatedAt().format(DATE_FORMATTER),
                    task.getUpdatedAt().format(DATE_FORMATTER));
        });

        System.out.println(BORDER);
    }

    public void printTask(Task task) {
        System.out.println(BORDER);
        System.out.println(HEADER);
        System.out.println(BORDER);

        System.out.printf(TASK_FORMAT,
                task.getId(),
                truncate(task.getDescription()),
                task.getStatus(),
                task.getCreatedAt().format(DATE_FORMATTER),
                task.getUpdatedAt().format(DATE_FORMATTER));

        System.out.println(BORDER);
    }

    private String truncate(String str){
        return (str.length() <= DESCRIPTION_MAX_LENGTH) ? str :
                str.substring(0, DESCRIPTION_MAX_LENGTH - 3) + "...";
    }

}
