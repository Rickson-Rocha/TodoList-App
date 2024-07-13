package com.br.todolist.service;
import org.springframework.stereotype.Service;
import com.br.todolist.repository.UserRepository;
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
    return user.orElseThrow(()-> new RuntimeException(
        "User not found! id: " + id + "Type" + User.class.getName()
    ));
  }

  @Transactional
  public User createUser(User user){
    user.setId(null);
    User  newUser =  this.userRepository.save(user);
    return newUser;
  }

  @Transactional
  public User updateUser(User user){
    User newUser = findById(user.getId());
    newUser.setPassword(user.getPassword());
    return this.userRepository.save(newUser);

  }

  public void deleteUser(Long id){
    findById(id);
    try{
        this.userRepository.deleteById(id);
    }catch (Exception e){
      throw new RuntimeException("Cannot delete has there is related entity");
    }
   

  }


}
