package com.br.todolist.controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import com.br.todolist.service.UserService;

import jakarta.validation.Valid;

import com.br.todolist.model.user.User;
import com.br.todolist.model.user.User.createUser;
import com.br.todolist.model.user.User.updateUser;


@RestController
@RequestMapping("api/users")
@Validated

public class UserController {
    
    private  final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping
    @Validated(createUser.class)
    public ResponseEntity<Void> create(@Valid @RequestBody User user, UriComponentsBuilder uriBuilder){
        User createUser = userService.createUser(user);
        var uri = uriBuilder.path("/user/{id}").buildAndExpand(createUser.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id){
        User user = userService.findById(id);
        return ResponseEntity.ok().body(user);
    }

    @PutMapping("/{id}")
    @Validated(updateUser.class)
    public ResponseEntity<Void>update(@Valid @RequestBody User user, @PathVariable Long id){
        user.setId(null);
        this.userService.updateUser(user);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void>delete(@PathVariable Long id){
        this.userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}