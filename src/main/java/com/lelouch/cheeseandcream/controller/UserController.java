package com.lelouch.cheeseandcream.controller;

import com.lelouch.cheeseandcream.service.UserCrudService;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserCrudService userCrudService;

    public UserController(UserCrudService userCrudService) {
        this.userCrudService = userCrudService;
    }

    @PostMapping
    public ResponseEntity<Void> createUser(@Param("username") String username, @Param("password") String password) {
        userCrudService.createUser(username, password);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Void> updateUser(@PathVariable Long id, @Param("username") String username, @Param("password") String password) {
        userCrudService.updateUser(username, password);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userCrudService.deleteUserById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }



}
