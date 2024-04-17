package com.example.TodoList.TodoList.service;

import com.example.TodoList.TodoList.dto.TodoDto;
import com.example.TodoList.TodoList.model.Task;

import java.util.List;
import java.util.Optional;

public interface TodoAppService {
    public List<Task> getAllTodos();

    public Optional<Task> getTodoById(int id);

    public TodoDto createTodo(TodoDto requestDto);

    public TodoDto updateTodo(TodoDto requestDto);

    public String deleteTodo(int id);


}
