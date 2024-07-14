package com.br.todolist.service;
import org.springframework.stereotype.Service;
import com.br.todolist.repository.TaskRepository;
import org.springframework.transaction.annotation.Transactional;

import com.br.todolist.exceptions.DataIntegrityException;
import com.br.todolist.exceptions.ResourceNotFoundException;
import com.br.todolist.model.task.Task;


import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }

    public Task findById(Long id){
     Optional<Task> task = this.taskRepository.findById(id);
        return task.orElseThrow(()-> new ResourceNotFoundException(
        "Task not found! id: " + id + "Type" + Task.class.getName()));
    }

    public List<Task> findAllByUserId(Long userId){
      List<Task> tasks  = this.taskRepository.findByUser_id(userId);
     return tasks;
    }

    @Transactional
    public Task createTask(Task task){
      task.setId(null);
      try{
        task.setUser(task.getUser());
        Task  newTask =  this.taskRepository.save(task);
        return newTask;
      }catch(DataIntegrityException e ){
         throw new DataIntegrityException(" Data integrity  violation:" + e.getMessage());
      }
    }

   @Transactional
   public Task updateTask(Task task){
    Task newTask = findById(task.getId());
    try{
      newTask.setName(task.getName());
      newTask.setDescription(task.getDescription());
      newTask.setStartAt(task.getStartAt());
      newTask.setEndAt(task.getEndAt());
      return this.taskRepository.save(newTask);
    }catch(DataIntegrityException e){
      throw new DataIntegrityException(" Data integrity  violation:" + e.getMessage());
    }
  }

  public void deleteTask(Long id){
    findById(id);
    taskRepository.deleteById(id);
  }
  
}
