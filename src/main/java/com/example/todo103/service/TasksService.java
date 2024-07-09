package com.example.todo103.service;

import com.example.todo103.dto.CreatedTodoDto;
import com.example.todo103.entity.Tasks;
import com.example.todo103.repository.TasksRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.PageRequest;
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

    public List<Tasks> getAll(Integer page ,Integer perPage){
          List<Tasks>tasksList= new ArrayList<>();
          tasksList=(ArrayList<Tasks>) repository.findAll(PageRequest.of(page, perPage));
        return tasksList;
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
