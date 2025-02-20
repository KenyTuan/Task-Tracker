package com.test.entity;

import com.test.enums.Status;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Task implements Serializable, Comparable<Task> {

    private static final long serialVersionUID = 1L;

    private long id;

    private String description;

    private Status status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public Task(long id, String description, Status status,
                LocalDateTime createdAt, LocalDateTime updatedAt) {
        checkId(id);
        checkDescription(description);

        this.id = id;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getDescription() {
        return description;
    }

    public long getId() {
        return id;
    }

    public Status getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public boolean isUpdated() {
        return updatedAt.isAfter(createdAt);
    }

    @Override
    public String toString() {
        return String.format("[Task: %d | Description: %s | Status: %s | Created: %s | Updated: %s]",
                this.id,
                this.description,
                this.status,
                this.createdAt.toString(),
                isUpdated() ? this.updatedAt.toString() : "N/A"
        );
    }

    private void checkId(long id) {
        if (id <= 0)
            throw new IllegalArgumentException("Task ID must be positive");
    }

    private void checkDescription(String description) {
        if (description == null || description.isBlank())
            throw new IllegalArgumentException("Description cannot be null or empty");
        if (description.length() > 255)
            throw new IllegalArgumentException("Description is too long");
    }


    @Override
    public int compareTo(Task task) {
        return Integer.compare((int) this.id, (int) task.id);
    }
}
