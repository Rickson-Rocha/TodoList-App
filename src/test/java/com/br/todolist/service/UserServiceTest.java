package com.br.todolist.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import com.br.todolist.exceptions.DataIntegrityException;
import com.br.todolist.exceptions.EntityInUseException;
import com.br.todolist.exceptions.ResourceNotFoundException;
import com.br.todolist.model.user.User;
import com.br.todolist.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;


     private User user;

    @BeforeEach
    public void setup() {
        user = new User();
        user.setId(1L);
        user.setPassword("password123");
    }

    @Test
    public void testFindById_UserFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User foundUser = userService.findById(1L);

        assertNotNull(foundUser);
        assertEquals(user.getId(), foundUser.getId());
    }

    @Test
    public void testFindById_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.findById(1L));
    }

    @Test
    public void testCreateUser_Success() {
        when(userRepository.save(any(User.class))).thenReturn(user);

        User newUser = userService.createUser(user);

        assertNotNull(newUser);
        assertEquals(user.getId(), newUser.getId());
    }

    @Test
    public void testCreateUser_DataIntegrityViolation() {
        when(userRepository.save(any(User.class))).thenThrow(DataIntegrityViolationException.class);

        assertThrows(DataIntegrityException.class, () -> userService.createUser(user));
    }

    @Test
    public void testUpdateUser_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        user.setPassword("newpassword123");
        User updatedUser = userService.updateUser(user);

        assertNotNull(updatedUser);
        assertEquals("newpassword123", updatedUser.getPassword());
    }

    @Test
    public void testUpdateUser_DataIntegrityViolation() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenThrow(DataIntegrityViolationException.class);

        assertThrows(DataIntegrityException.class, () -> userService.updateUser(user));
    }

    @Test
    public void testDeleteUser_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        doNothing().when(userRepository).deleteById(1L);

        assertDoesNotThrow(() -> userService.deleteUser(1L));
    }

    
    
   
    @Test
    public void testDeleteUser_EntityInUse() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        doThrow(DataIntegrityViolationException.class).when(userRepository).deleteById(1L);

        assertThrows(EntityInUseException.class, () -> userService.deleteUser(1L));
    }
    
   
}


