package com.example.todo103.service;

import com.example.todo103.dto.ChangeStatusTodoDto;
import com.example.todo103.dto.CreatedTodoDto;
import com.example.todo103.response.BaseSuccessResponse;
import com.example.todo103.response.CustomSuccessResponse;
import com.example.todo103.dto.GetNewsDto;
import com.example.todo103.entity.Tasks;
import com.example.todo103.repository.TasksRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import java.util.List;

@Service
public class TasksService {
    private final TasksRepository repository;
    private List<Tasks> tasksList;


    private List<Tasks> getTodoList() {
        tasksList=new ArrayList<>();
        repository.findAll().stream().forEach(tasksList::add);
        return tasksList;
    }

    @Autowired
    public TasksService(TasksRepository repository) {
        this.repository = repository;
    }

    public Tasks create( Tasks send){
        var task=new Tasks();
        task.setText(send.getText());
        task.setCreatedAt(send.getCreatedAt());
        task.setStatus(send.isStatus());
        task.setUpdatedAt(send.getUpdatedAt());

        return repository.save(task);

    }

    public ArrayList<Tasks> get(){
        ArrayList<Tasks> tasksList= (ArrayList<Tasks>) repository.findAll();
        return tasksList;
    }

    public CustomSuccessResponse<GetNewsDto>  getAll(Integer page, Integer perPage, Boolean  status) {
        Pageable pageable = PageRequest.of(page, perPage);
        Page<Tasks> pageResult = repository.findAll(pageable);
        if (status != null) {
            pageResult = repository.findByStatus(status, pageable); // Use your repository method
        } else {
            pageResult = repository.findAll(pageable);
        }
        // Create the TasksDataDTO
         GetNewsDto newsDto  = new GetNewsDto(
                pageResult.getContent(),
                // ... calculate notReady, ready, numberOfElements
                 (int) pageResult.getContent().stream().filter(task -> !task.isStatus()).count(),
                 (int)pageResult.getContent().stream().filter(task -> task.isStatus()).count(),
                 (int) pageResult.getTotalElements()
//                (int) pageResult.getTotalElements() // Total count
        );

        // Create the TasksResponseDTO
        CustomSuccessResponse<GetNewsDto> responseDTO = new CustomSuccessResponse<>(
                newsDto,
                0, // Status code
                true // Success
        );

        return responseDTO;
    }

    public Tasks updateStatus(Integer id,  ChangeStatusTodoDto newStatus) {
        Tasks task =(Tasks) repository.findById(id).get();
        if (task != null) {
            task.setStatus(newStatus.getStatus());
            repository.save(task);
            return task;
        } else {
            return null; // Or throw an exception if the task is not found
        }
    }
    public BaseSuccessResponse setAllStatus(ChangeStatusTodoDto status){
        tasksList=repository.findAll();
        tasksList.stream().forEach(tasks -> tasks.setStatus(status.getStatus()));
        repository.saveAll(tasksList);
        var responseDto=new BaseSuccessResponse(0,true);
        return responseDto;
    }

    public BaseSuccessResponse deleteById(Integer id){
        repository.deleteById(id);
        return new BaseSuccessResponse(0,true);
    }

    public BaseSuccessResponse deleteAllReady(){
        delete();
        return new BaseSuccessResponse(0,true);
    }

    public void delete() {
        repository.deleteAll(getTodoList().stream().filter(t -> t.isStatus() == true).toList());
    }

    public Tasks updateText(Integer id, CreatedTodoDto text){
        Tasks task =(Tasks) repository.findById(id).get();
        if (task != null) {
            task.setText(text.getText());
            repository.save(task);
            return task;
        } else {
            return null; // Or throw an exception if the task is not found
        }
    }
    public BaseSuccessResponse setText(Integer id, CreatedTodoDto text){
        updateText(id,text);
        return new BaseSuccessResponse(0,true);
    }

}
