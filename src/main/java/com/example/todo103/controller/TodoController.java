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
    public ResponseEntity<?> createTask(@RequestBody @Valid CreatedTodoDto text){
        var task=new Tasks();
        task.setText(text.getText());
        service.create(task);
        CustomSuccessResponse<Tasks> body=new CustomSuccessResponse<>();
        body.setData(task);
        body.setStatusCode(200);
        body.setSuccess(true);
        ResponseEntity<CustomSuccessResponse<Tasks>> response=new ResponseEntity<>(body,HttpStatus.OK);
        return response;
    }

    @GetMapping(value = "/get")
    public ResponseEntity<?> get(){
        ArrayList<Tasks> list=service.get();
        return new ResponseEntity<>(list,HttpStatus.OK);
    }

    @GetMapping(value = "/getPaginated")
    public ResponseEntity<?> getTodo(@RequestParam(value = "page", defaultValue = "1") @Min(1) Integer page,
                                     @RequestParam(value = "perPage", defaultValue = "1")
                                     @Min(1)  Integer perPage,
                                     @RequestParam(value = "status",defaultValue = "false")boolean status){

        CustomSuccessResponse<GetNewsDto>body=service.getAll(page,perPage,status);
        ResponseEntity<CustomSuccessResponse<GetNewsDto>> response=new ResponseEntity<>(body,HttpStatus.OK);
        return response;
    }

    @PatchMapping(value = "/patch")
    public ResponseEntity<?> setStatus(@RequestBody ChangeStatusTodoDto status){
        BaseSuccessResponse body= service.setAllStatus(status);
        return new ResponseEntity<>(body,HttpStatus.OK);

    }

    @PatchMapping(value = "/status")
    public ResponseEntity<?> setByIdStatus(@RequestParam(value = "id")Integer id,
                                             @RequestBody ChangeStatusTodoDto status){
         service.updateStatus(id,status);
       BaseSuccessResponse body= new BaseSuccessResponse(0,true);
        return new ResponseEntity<>(body,HttpStatus.OK);
    }

    @DeleteMapping(value = "deleteById")
    public ResponseEntity<?> deleteByid(@RequestParam(value = "id")Integer id){
        BaseSuccessResponse  body= service.deleteById(id);
        return new ResponseEntity<>(body,HttpStatus.OK);
    }

    @DeleteMapping(value = "/deleteAllReady")
    public ResponseEntity<?> deleteAllReady(){
        BaseSuccessResponse bod= service.deleteAllReady();
        return new ResponseEntity<>(bod,HttpStatus.OK);

    }

    @PatchMapping(value = "patchText")
    public ResponseEntity<?> setText(@RequestParam(value = "id")Integer id,
                                       @RequestBody @Valid CreatedTodoDto text){
        BaseSuccessResponse body= service.setText(id,text);
        return new ResponseEntity<>(body,HttpStatus.OK);
    }





}
