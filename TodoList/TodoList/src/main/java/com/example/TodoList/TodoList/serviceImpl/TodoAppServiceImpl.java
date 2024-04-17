package com.example.TodoList.TodoList.serviceImpl;

import com.example.TodoList.TodoList.dto.TodoDto;
import com.example.TodoList.TodoList.model.Task;
import com.example.TodoList.TodoList.repository.TodoRepository;
import com.example.TodoList.TodoList.service.TodoAppService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TodoAppServiceImpl implements TodoAppService {

    private final TodoRepository todoRepository;

    public TodoAppServiceImpl(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    public List<Task> getAllTodos() {

        return todoRepository.findAll();
    }

    @Override
    public Optional<Task> getTodoById(int id) {
        if(id <= 0) {
            throw new IllegalArgumentException("Invalid task ID: " + id);
        }

        return Optional.ofNullable(todoRepository.findTodoById(id)
                .orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public TodoDto createTodo(TodoDto requestDto) {
        Task task = Task.builder()
                .id(requestDto.getId())
                .description(requestDto.getDescription())
                .completion_status(requestDto.getCompletion_status())
                .build();

        return mapToDto(todoRepository.save(task));

    }

    @Override
    public TodoDto updateTodo(TodoDto requestDto) {
        Optional<Task> task = todoRepository.findTodoById(requestDto.getId());
        if(task.isPresent()) {
            Task updatedTask = Task.builder()
                    .id(task.get().getId())
                    .description(task.get().getDescription())
                    .completion_status(task.get().getCompletion_status())
                    .build();

            return mapToDto(todoRepository.save(updatedTask));
        }
        else {
            throw new EntityNotFoundException("Task not found with ID: " + requestDto.getId());
        }
    }

    @Override
    public String deleteTodo(int id) {

        todoRepository.deleteById(id);
        return "Task Successfully Deleted";
    }

    private TodoDto mapToDto(Task task){

        return TodoDto.builder()
                .id(task.getId())
                .description(task.getDescription())
                .completion_status(task.getCompletion_status())
                .build();
    }
}
