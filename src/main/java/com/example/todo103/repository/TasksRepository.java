package com.example.todo103.repository;

import com.example.todo103.entity.Tasks;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TasksRepository extends JpaRepository<Tasks,Integer> {
   List<Tasks> findAll();

   Page<Tasks> findByStatus(boolean status, Pageable pageable);
//   Tasks updateStatus(Integer id, Boolean status);
//   List<Tasks> save(List<Tasks> list);
//      @Query("DELETE FROM Tasks t WHERE t.status = true")
//      void deleteAllWithStatusTrue();

}
