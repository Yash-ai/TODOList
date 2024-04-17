package com.example.TodoList.TodoList;

import com.example.TodoList.TodoList.controller.TodoController;
import com.example.TodoList.TodoList.dto.TodoDto;
import com.example.TodoList.TodoList.model.Task;
import com.example.TodoList.TodoList.service.TodoAppService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TodoController.class)
public class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    @MockBean
    private TodoAppService todoAppService;

//    @Mock
//    private TodoAppService todoRepository;

    @InjectMocks
    private TodoController todoController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllTasks() {

        Task task1 = new Task();
        task1.setId(1);
        task1.setDescription("Task 1");
        task1.setCompletion_status("Completed");

        Task task2 = new Task();
        task1.setDescription("Task 1");
        task1.setCompletion_status("inCompleted");

        List<Task> tasks = Arrays.asList(task1, task2);
        when(todoAppService.getAllTodos()).thenReturn(tasks);

        ResponseEntity<List<Task>> response = todoController.getAllTasks();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(tasks, response.getBody());
    }

    @Test
    public void testGetTaskById() throws Exception {
        int taskId = 1;
        Task task = Task.builder()
                .id(taskId)
                .description("Task 1")
                .completion_status("Completed")
                .build();

//        when(todoAppService.getTodoById(taskId)).thenReturn(Optional.of(task));
//
//        ResponseEntity<Optional<Task>> response = todoController.getTaskById(taskId);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(task, response.getBody().get());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/todo/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(task.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(task.getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.completion_status").value(task.getCompletion_status()));
    }

    @Test
    public void testCreateTask() {
        TodoDto requestDto = TodoDto.builder()
                .id(3)
                .description("Task 3")
                .completion_status("Completed")
                .build();

        TodoDto createdTask = new TodoDto();
            createdTask.setId(3);
            createdTask.setDescription("Task 3");
            createdTask.setCompletion_status("Completed");

        when(todoAppService.createTodo(requestDto)).thenReturn(createdTask);

        ResponseEntity<TodoDto> response = todoController.createTask(requestDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(requestDto.getId(), response.getBody().getId());
        assertEquals(requestDto.getDescription(), response.getBody().getDescription());
        assertEquals(requestDto.getCompletion_status(), response.getBody().getCompletion_status());

    }

    @Test
    public void testUpdateTodo() throws Exception {

        TodoDto requestDto = TodoDto.builder()
                .id(3)
                .description("Task 3")
                .completion_status("Completed")
                .build();

        TodoDto updatedTask = TodoDto.builder()
                .id(3)
                .description("Updated Task")
                .completion_status("InComplete")
                .build();

        when(todoAppService.updateTodo(requestDto)).thenReturn(updatedTask);

//        mockMvc.perform(MockMvcRequestBuilders.put("/api/todo/updateTask")
//                        .content("{ \"id\": 3, \"description\": \"Task 3\", \"completion_status\": \"Completed\" }")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(updatedTask.getId()))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(updatedTask.getDescription()))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.completion_status").value(updatedTask.getCompletion_status()));
    }


    @Test
    public void testDeleteTask() {
        int taskId = 3;
        String successMessage = "Task deleted successfully";

        when(todoAppService.deleteTodo(taskId)).thenReturn(successMessage);

        String response = todoController.deleteTask(taskId);

        assertEquals(successMessage, response);
    }
}
