package com.example.todo103.service;

import com.example.todo103.dto.CreatedTodoDto;
import com.example.todo103.dto.CustomSuccessResponse;
import com.example.todo103.dto.GetNewsDto;
import com.example.todo103.entity.Tasks;
import com.example.todo103.repository.TasksRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TasksService {
    private final TasksRepository repository;

    public TasksRepository getRepository() {
        return repository;
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
                getNotReady(), // Replace with actual counts
                 getNumberOfElement(),
                 getReady() // Replace with actual counts
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

    public int getNumberOfElement() {
        int numberOfElements =(int)repository.findAll().stream().count();
        return numberOfElements;
    }
    public int getNotReady(){
        int notReady =(int) repository.findAll().stream().filter(t -> t.isStatus() == false).count();
        return notReady;
    }
    public int getReady(){
        int ready=(int) repository.findAll().stream().filter(t -> t.isStatus() == true).count();
        return ready;
    }

}
