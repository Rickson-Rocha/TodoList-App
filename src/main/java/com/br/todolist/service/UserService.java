package com.br.todolist.service;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import com.br.todolist.repository.UserRepository;
import com.br.todolist.exceptions.DataIntegrityException;
import com.br.todolist.exceptions.EntityInUseException;
import com.br.todolist.exceptions.ResourceNotFoundException;
import com.br.todolist.model.user.User;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;
@Service
public class UserService {
    private final UserRepository userRepository;

   public UserService(UserRepository userRepository){
     this.userRepository = userRepository;
   }
 

  public User findById(Long id){
    Optional<User> user = this.userRepository.findById(id);
    return user.orElseThrow(()-> new ResourceNotFoundException(
        "User not found! id: " + id + "Type" + User.class.getName()
    ));
  }

  @Transactional
  public User createUser(User user){
    user.setId(null);
    try{
      User  newUser =  this.userRepository.save(user);
      return newUser;
    }catch(DataIntegrityException e){
       throw new DataIntegrityException(" Data integrity  violation:" + e.getMessage());
    }
  }

  @Transactional
  public User updateUser(User user){
    User newUser = findById(user.getId());
    try{
      newUser.setPassword(user.getPassword());
      return this.userRepository.save(newUser);
    }catch(DataIntegrityException e){
      throw new DataIntegrityException(" Data integrity  violation:" + e.getMessage());
    }
   

  }

  public void deleteUser(Long id){
    findById(id);
    try{
        this.userRepository.deleteById(id);
    }catch (DataIntegrityViolationException e){
      throw new EntityInUseException("Cannot delete has there is related entity");
    }
   

  }


}
