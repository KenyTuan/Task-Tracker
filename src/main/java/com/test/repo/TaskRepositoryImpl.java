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
