package com.test.service;

import com.test.entity.Task;
import com.test.enums.Status;
import com.test.repo.TaskRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class TodoTodoAppServiceImpl implements TodoAppService {

    private TaskRepository repository;

    private List<Task> tasks;

    public TodoTodoAppServiceImpl(TaskRepository repository) throws IOException {
        this.repository = repository;
        this.tasks = repository.loading();
    }

    @Override
    public void save() throws IOException {
        repository.save(tasks);
    }

    @Override
    public Task add(String description) {
        final LocalDateTime now = LocalDateTime.now();
        final Task task = new Task(getNewId(),description, Status.TODO,now,now);

        tasks.add(task);

        return task;
    }

    @Override
    public Task update(long id, String description) {
        Task task = findTask(id);

        Task updatedTask = new Task(
                task.getId(),
                description,
                task.getStatus(),
                task.getCreatedAt(),
                LocalDateTime.now()
        );

        tasks.remove(task);
        tasks.add(updatedTask);

        return updatedTask;
    }

    @Override
    public void delete(long id){
        tasks.remove(findTask(id));
    }

    @Override
    public Task updateStatus(long id, Status status) {
        Task task = findTask(id);

        Task updatedTask = new Task(
                task.getId(),
                task.getDescription(),
                status,
                task.getCreatedAt(),
                LocalDateTime.now()
        );

        tasks.remove(task);
        tasks.add(updatedTask);

        return updatedTask;
    }

    @Override
    public List<Task> getAll() {
        return tasks
                .stream()
                .sorted()
                .toList();
    }

    @Override
    public List<Task> getByStatus(Status status) {
        return tasks.stream()
                .filter(t -> t.getStatus().equals(status))
                .sorted()
                .toList();
    }

    private long getNewId() {
        return tasks.stream()
                .mapToLong(Task::getId)
                .max()
                .orElse(0) + 1;
    }

    private Task findTask(long id) {
        return tasks.stream()
                .filter(t -> t.getId() == id)
                .findFirst()
                .orElseThrow(() -> new RuntimeException(String.format("Task with ID %d not found!", id)));
    }
}
