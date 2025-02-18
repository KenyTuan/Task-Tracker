package com.test.mapper;

import com.test.entity.Task;
import com.test.enums.Status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;

public class JsonMapper {

    public static String taskToJson(List<Task> tasks) {
        if (tasks == null || tasks.isEmpty()) {
            return "";
        }
        return tasks.stream()
                .map(JsonMapper::convertTaskToJson)
                .collect(joining(",\n","[\n","\n]"));
    }

    public static List<Task> jsonToTasks(String json) {
        if (json == null || json.isEmpty()) {
            return new ArrayList<>();
        }

        String jsonContent = json.substring(1, json.length() - 1).trim();
        if (jsonContent.isEmpty()) {
            return new ArrayList<>();
        }

        String[] jsonObjects = jsonContent.split("},\\s*\\{");

        return Arrays.stream(jsonObjects)
                .map(obj -> {
                    if (!obj.startsWith("{")) {
                        obj = "{" + obj;
                    }
                    if (!obj.endsWith("}")) {
                        obj = obj + "}";
                    }
                    return convertJsonToTask(obj);
                })
                .collect(Collectors.toList());
    }

    private static String convertTaskToJson(Task task) {

        return "\t{\n" +
                "\t\t\"id\":" + task.getId() + ",\n" +
                "\t\t\"description\": \"" + task.getDescription() + "\",\n" +
                "\t\t\"status\": \"" + task.getStatus() + "\",\n" +
                "\t\t\"createdAt\": \"" + task.getCreatedAt() + "\",\n" +
                "\t\t\"updatedAt\": \"" + task.getUpdatedAt() + "\"\n" +
                "\t}";
    }

    private static Task convertJsonToTask(String json) {
        json = json.replaceAll("[{}\"]", "").trim();

        String[] keyValuePairs = json.split(",");

        long id = 0;
        String description = null;
        Status status = null;
        LocalDateTime createdAt = null;
        LocalDateTime updatedAt = null;

        for (String keyValuePair : keyValuePairs) {
            String[] keyValue = keyValuePair.split(":", 2);
            if (keyValue.length != 2) {
                continue;
            }

            String key = keyValue[0].trim();
            String value = keyValue[1].trim();

            switch (key) {
                case "id":
                    id = Long.parseLong(value);
                    break;
                case "description":
                    description = value;
                    break;
                case "status":
                    status = Status.valueOf(value);
                    break;
                case "createdAt":
                    createdAt = LocalDateTime.parse(value);
                    break;
                case "updatedAt":
                    updatedAt = LocalDateTime.parse(value);
                    break;
                default:
                    break;
            }
        }

        return new Task(id, description, status, createdAt, updatedAt);
    }
}
