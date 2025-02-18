package com.test.repo;

import com.test.entity.Task;

import java.io.IOException;
import java.util.List;

public interface TaskRepository {

    List<Task> loading() throws IOException;

    void save(List<Task> tasks) throws IOException;

}
