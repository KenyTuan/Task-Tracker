package com.test.repo;

import com.test.entity.Task;
import com.test.mapper.JsonMapper;
import org.json.JSONArray;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TaskRepositoryImpl implements TaskRepository {

    private final static String FILE_NAME = "todo.json";

    private final Path path;

    public TaskRepositoryImpl() {
        this.path = Path.of(FILE_NAME);
    }

    public static List<Task> readToFile(String fileName)
            throws IOException {

        File file = new File(fileName);

        if (!file.exists()) {
            return new ArrayList<>();
        }

        StringBuilder jsonContent = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                jsonContent.append(line);
            }
        }

        JSONArray jsonArray = new JSONArray(jsonContent.toString());
        List<Task> tasks = new ArrayList<>();
        final DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

        return tasks;
    }

    public static void writeToFile(List<Task> tasks, String fileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(JsonMapper.taskToJson(tasks));
        }
    }


    @Override
    public List<Task> loading() throws IOException {

        if (!Files.exists(path)) {
            return new ArrayList<>();
        }

        return JsonMapper.jsonToTasks(Files.readString(path));
    }

    @Override
    public void save(List<Task> tasks) throws IOException {
        if (!Files.exists(path)) {
            Files.createFile(path);
        }
        Files.writeString(path, JsonMapper.taskToJson(tasks));
    }
}
