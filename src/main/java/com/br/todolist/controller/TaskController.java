package com.br.todolist.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import com.br.todolist.model.task.Task;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import com.br.todolist.service.TaskService;
import java.util.List;

@RestController
@RequestMapping("api/tasks")
@Validated

public class TaskController {
    
    private  final TaskService taskService;

    public TaskController(TaskService taskService){
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody Task task, UriComponentsBuilder uriBuilder){
        Task createTask = taskService.createTask(task);
        var uri = uriBuilder.path("/tasks/{id}").buildAndExpand(createTask.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> findById(@PathVariable Long id){
        Task user = taskService.findById(id);
        return ResponseEntity.ok().body(user);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Task>>findAllByUser(@PathVariable long userId){
        List<Task> tasks = this.taskService.findAllByUserId(userId);
        return ResponseEntity.ok().body(tasks);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Void>update(@Valid @RequestBody Task task, @PathVariable Long id){
        task.setId(null);
        this.taskService.updateTask(task);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void>delete(@PathVariable Long id){
        this.taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}


