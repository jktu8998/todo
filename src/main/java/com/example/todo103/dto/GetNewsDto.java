package com.example.todo103.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GetNewsDto <T>{
    private T content;
    private int notReady;
    private int numberOfElement;
    private int ready;

//    public GetNewsDto(T content) {
//        this.content = content;
//    }
}
