package com.example.todo103.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BaseSuccessResponse {
    private Integer statusCode;
    private boolean success;
}
