package com.example.todo103.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class CustomSuccessResponse <T>{
    private T data;
    private int statusCode;
    private boolean success;
}
