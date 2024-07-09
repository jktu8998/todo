package com.example.todo103.controller;

import com.example.todo103.dto.CreatedTodoDto;
import com.example.todo103.dto.CustomSuccessResponse;
import com.example.todo103.dto.GetNewsDto;
import com.example.todo103.entity.Tasks;
import com.example.todo103.service.TasksService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.config.Task;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/v1/todo")
public class TodoController {
    private final TasksService service;
    @Autowired
    public TodoController(TasksService service) {
        this.service = service;
    }

    @PostMapping(value = "/create")
    public CustomSuccessResponse<?> createTask(@RequestBody @Valid CreatedTodoDto text){
        var task=new Tasks();
        task.setText(text.getText());
        service.create(task);
        CustomSuccessResponse<Tasks> response=new CustomSuccessResponse<>();
        response.setData(task);
        response.setStatusCode(200);
        response.setSuccess(true);
        return response;
    }
    @GetMapping(value = "/get")
    public ResponseEntity<?> get(){
        ArrayList<Tasks> list=service.get();
        return new ResponseEntity<>(list,HttpStatus.OK);
    }

    @GetMapping(value = "/getPaginated")
    public CustomSuccessResponse<?> getTodo(@RequestParam(value = "page", defaultValue = "1") @Min(1) Integer page,
                                     @RequestParam(value = "perPage", defaultValue = "1")
                                     @Min(1) @Max(5) Integer perPage){
        List<Tasks>list=service.getAll(page,perPage);
        int numberOfElements=service.getNumberOfElement();
        int notReady=service.getNotReady();
        int ready=service.getReady();
        GetNewsDto<List<Tasks>> getNewsDto=new GetNewsDto<>(list,numberOfElements,ready,notReady);

        return new CustomSuccessResponse<GetNewsDto<List<Tasks>>>(getNewsDto,200,true);
    }

//    @GetMapping(value = "/getPaginated")
//    public ResponseEntity<?> getTodo(@RequestParam(value = "page", defaultValue = "1") @Min(1) Integer page,
//                                     @RequestParam(value = "perPage", defaultValue = "1")
//                                     @Min(1) @Max(5) Integer perPage) {
//        var response = service.getTodoRepository().findAll(PageRequest.of(page, perPage));
//
//        int notReady = todoService.getNotReady();
//        int numberOfElements = todoService.getNumberOfElement();
//        int ready = todoService.getReady();
//
//        CustomSuccessResponse customSuccessResponse = new CustomSuccessResponse();
//        GetNewsDto getNewsDto = new GetNewsDto();
//        getNewsDto.setTodoList(response.getContent());
//        getNewsDto.setNotReady(notReady);
//        getNewsDto.setNumberOfElements(numberOfElements);
//        getNewsDto.setReady(ready);
//        customSuccessResponse.setGetNewsDto(getNewsDto);
//        customSuccessResponse.setSuccess(true);
//        customSuccessResponse.setStatusCode(200);
//        return new ResponseEntity<>(customSuccessResponse, HttpStatus.OK);
//    }
}
