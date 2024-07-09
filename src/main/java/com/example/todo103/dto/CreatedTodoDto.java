package com.example.todo103.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreatedTodoDto {
    @NotBlank
    @Size(min = 3,max = 160)
    private String text;
}
