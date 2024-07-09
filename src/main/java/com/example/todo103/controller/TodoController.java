package com.example.todo103.controller;

import com.example.todo103.dto.ChangeStatusTodoDto;
import com.example.todo103.dto.CreatedTodoDto;
import com.example.todo103.response.BaseSuccessResponse;
import com.example.todo103.response.CustomSuccessResponse;
import com.example.todo103.dto.GetNewsDto;
import com.example.todo103.entity.Tasks;
import com.example.todo103.service.TasksService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

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
                                     @Min(1)  Integer perPage,
                                     @RequestParam(value = "status",defaultValue = "false")boolean status){

        CustomSuccessResponse<GetNewsDto>response=service.getAll(page,perPage,status);
        return response;
    }

    @PatchMapping(value = "/patch")
    public BaseSuccessResponse setStatus(@RequestBody ChangeStatusTodoDto status){
        return service.setAllStatus(status);
    }

    @PatchMapping(value = "/status")
    public BaseSuccessResponse setByIdStatus(@RequestParam(value = "id")Integer id,
                                             @RequestBody ChangeStatusTodoDto status){

        service.updateStatus(id,status);
        return new BaseSuccessResponse(0,true);
    }

    @DeleteMapping(value = "deleteById")
    public BaseSuccessResponse deleteByid(@RequestParam(value = "id")Integer id){
        return service.deleteById(id);
    }

    @DeleteMapping(value = "/deleteAllReady")
    public BaseSuccessResponse deleteAllReady(){
        return service.deleteAllReady();
    }





}
