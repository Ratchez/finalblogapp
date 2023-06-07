package com.codewithratchez.blog.controllers;

import com.codewithratchez.blog.payloads.ApiResponse;
import com.codewithratchez.blog.payloads.UserDto;
import com.codewithratchez.blog.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@SecurityRequirement(name = "Bearer Authentication")
public class UserController {
    @Autowired
    private UserService userService;

    // POST-create user
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping()
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
        UserDto createdUser = userService.createUser(userDto);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    // PUT-update user
    @PutMapping("/{bloggerId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable("bloggerId") Integer bloggerId){
        UserDto updateUser = userService.updateUser(userDto, bloggerId);
        return ResponseEntity.ok(updateUser);
    }

    //ADMIN
    // DELETE-delete user
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete user", description = "Delete user")
    @DeleteMapping("/{bloggerId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable("bloggerId") Integer bloggerId){
        userService.deleteUser(bloggerId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("User Deleted Successfully"), HttpStatus.OK);
    }

    // GET - user gets all users
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping()
    public ResponseEntity<List<UserDto>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // GET - user gets specific user
    @GetMapping("/{bloggerId}")
    public ResponseEntity<UserDto> getSingleUser(@PathVariable Integer bloggerId){
        return ResponseEntity.ok(userService.getUserByBloggerId(bloggerId));
    }
}
