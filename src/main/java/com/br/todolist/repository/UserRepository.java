package com.br.todolist.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.br.todolist.model.user.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long>  {
    
}
