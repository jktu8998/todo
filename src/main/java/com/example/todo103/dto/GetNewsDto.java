package com.example.todo103.dto;

import com.example.todo103.entity.Tasks;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GetNewsDto {
    private List<Tasks> content;
    private int notReady;
    private int ready;
    private int numberOfElement;

//    public GetNewsDto(T content) {
//        this.content = content;
//    }
}
