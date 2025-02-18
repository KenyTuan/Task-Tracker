package com.test.service;

import com.test.entity.Task;
import com.test.enums.Status;

import java.io.IOException;
import java.util.List;

public interface TodoAppService {

    void save() throws IOException;

    Task add(String description);

    Task update(long id, String description);

    void delete(long id);

    Task updateStatus(long id, Status status);

    List<Task> getAll();

    List<Task> getByStatus(Status status);
}
