package com.example.TodoList.TodoList.controller;


import com.example.TodoList.TodoList.dto.TodoDto;
import com.example.TodoList.TodoList.model.Task;
import com.example.TodoList.TodoList.service.TodoAppService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class TodoController {

    private final TodoAppService todoAppService;

    @GetMapping("/todo")
    public ResponseEntity<List<Task>> getAllTasks(){

        return ResponseEntity.ok(todoAppService.getAllTodos());
    }

    @GetMapping("/todo/{id}")
    public ResponseEntity<Optional<Task>> getTaskById(@PathVariable(name = "id") int id){
        return ResponseEntity.ok(todoAppService.getTodoById(id));
    }

    @PostMapping("/todo/createTask")
    public ResponseEntity<TodoDto> createTask(@RequestBody TodoDto requestDto){
        return ResponseEntity.ok(todoAppService.createTodo(requestDto));
    }

    @PutMapping("/todo/updateTask")
    public ResponseEntity<TodoDto> updateTask(@RequestBody TodoDto requestDto){
        return ResponseEntity.ok(todoAppService.updateTodo(requestDto));
    }

    @DeleteMapping("/todo/{id}")
    public String deleteTask(@PathVariable("id") int id){
        return todoAppService.deleteTodo(id);
    }



}
