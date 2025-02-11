package com.br.todolist.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.br.todolist.model.task.Task;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long>  {

    List<Task> findByUser_id(Long userId);
    
}
