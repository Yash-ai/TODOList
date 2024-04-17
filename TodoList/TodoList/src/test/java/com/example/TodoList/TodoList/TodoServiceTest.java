package com.example.TodoList.TodoList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.TodoList.TodoList.dto.TodoDto;
import com.example.TodoList.TodoList.model.Task;
import com.example.TodoList.TodoList.repository.TodoRepository;
import com.example.TodoList.TodoList.serviceImpl.TodoAppServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TodoServiceTest {

    private TodoAppServiceImpl todoAppService;
    private TodoRepository todoRepository;

    @BeforeEach
    void setUp() {
        todoRepository = mock(TodoRepository.class);
        todoAppService = new TodoAppServiceImpl(todoRepository);
    }

    @Test
    void getAllTodos_ReturnsListOfTasks() {
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task(1, "Task 1", "Completed"));
        tasks.add(new Task(2, "Task 2", "Completed"));
        when(todoRepository.findAll()).thenReturn(tasks);

        List<Task> result = todoAppService.getAllTodos();

        assertEquals(2, result.size());
        assertEquals("Task 1", result.get(0).getDescription());
        assertEquals("Task 2", result.get(1).getDescription());
    }

    @Test
    void getTodoById_ValidId_ReturnsTask() {
        Task task = new Task(3, "task 3", "Completed");
        when(todoRepository.findTodoById(3)).thenReturn(Optional.of(task));

        Optional<Task> result = todoAppService.getTodoById(3);

        assertTrue(result.isPresent());
        assertEquals("task 3", result.get().getDescription());
    }

    @Test
    void createTodo_ReturnCreatedTodoDto() {
        TodoDto requestDto = new TodoDto(4, "New Tasj 4", "InComplete");
        Task savedTask = new Task(5, "Task 5", "InComplete");
        when(todoRepository.save(any(Task.class))).thenReturn(savedTask);

        TodoDto result = todoAppService.createTodo(requestDto);

        assertEquals("Task 5", result.getDescription());
        assertEquals("InComplete",result.getCompletion_status());
    }

    @Test
    void updateTodo_ValidDto_ReturnsUpdatedTodoDto() {
        TodoDto requestDto = new TodoDto(6, "Existing Task", "InComplete");
        Task existingTask = new Task(6, "Updated Task", "Complete");
        when(todoRepository.findTodoById(6)).thenReturn(Optional.of(existingTask));
        when(todoRepository.save(any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));

        TodoDto result = todoAppService.updateTodo(requestDto);

        assertEquals("Updated Task", result.getDescription());
        assertEquals("Complete",result.getCompletion_status());
    }

    @Test
    void deleteTodo_ValidId_DeletesTask() {

        String result = todoAppService.deleteTodo(1);

        assertEquals("Task Successfully Deleted", result);
        verify(todoRepository).deleteById(1);
    }
}
