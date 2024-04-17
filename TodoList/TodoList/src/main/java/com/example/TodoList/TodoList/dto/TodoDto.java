package com.example.TodoList.TodoList.dto;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TodoDto {

    private Integer id;

    private String description;

    private String completion_status;

}
