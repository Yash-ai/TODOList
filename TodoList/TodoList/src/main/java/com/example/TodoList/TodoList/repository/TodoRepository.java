package com.example.TodoList.TodoList.repository;


import com.example.TodoList.TodoList.model.Task;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TodoRepository extends JpaRepository<Task,Integer> {

    @NonNull
    List<Task> findAll();

    Optional<Task> findTodoById(int id);


}
