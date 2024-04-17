package com.example.TodoList.TodoList;

import com.example.TodoList.TodoList.model.Task;
import com.example.TodoList.TodoList.repository.TodoRepository;
import com.example.TodoList.TodoList.serviceImpl.TodoAppServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TodoRepositoryTest {

    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private TodoAppServiceImpl todoAppServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        List<Task> tasks = Arrays.asList(
                new Task(1, "Task 1", "Completed"),
                new Task(2, "Task 2", "Completed"));
        when(todoRepository.findAll()).thenReturn(tasks);

        List<Task> result = todoAppServiceImpl.getAllTodos();

        assertEquals(2, result.size());
        assertEquals("Task 1", result.get(0).getDescription());
        assertEquals("Task 2", result.get(1).getDescription());

        verify(todoRepository, times(1)).findAll();
    }

    @Test
    void testFindTodoById() {
        Task task = new Task(1, "Task 1", "Completed");
        when(todoRepository.findTodoById(1)).thenReturn(Optional.of(task));

        Optional<Task> result = todoAppServiceImpl.getTodoById(1);

        assertEquals(task, result.orElse(null));

        verify(todoRepository, times(1)).findTodoById(1);
    }
}
