package io.todos;

import java.io.IOException;
import java.util.List;

import todo.Todo;
import todo.TodoClient;
import todo.TodoNotFoundException;
import todo.ReadFromJson.ReadFromJson;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException, TodoNotFoundException {
        // TodoClient client = new TodoClient();
        ReadFromJson read = new ReadFromJson();
        System.out.println("Find todo by ID: " + read.getTodoById(200));
        System.out.println("Total number of todos: " + read.getAllTodos().size());
        read.delete(read.getTodoById(200)); // delete todo by id
        System.out.println("Total number of todos: " + read.getAllTodos().size());
        System.out.println("Find todo by ID: " + read.getTodoById(200));
        System.out.println("See Tests!");
    }
}