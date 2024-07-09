package com.example.todo103.repository;

import com.example.todo103.entity.Tasks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TasksRepository extends JpaRepository<Tasks,Integer> {
   List<Tasks> findAll();
}
