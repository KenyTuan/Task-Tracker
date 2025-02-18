package com.test;

import com.test.cli.TodoAppCLI;
import com.test.repo.TaskRepositoryImpl;
import com.test.service.TodoTodoAppServiceImpl;

import java.io.*;


public class Main {


    public static void main(String[] args) throws IOException {
        new TodoAppCLI(new TodoTodoAppServiceImpl(new TaskRepositoryImpl())).runApp();
    }

}