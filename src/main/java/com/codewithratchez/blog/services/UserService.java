package com.codewithratchez.blog.services;

import com.codewithratchez.blog.payloads.UserDto;
import com.codewithratchez.blog.payloads.UserRegReqDto;
import com.codewithratchez.blog.payloads.UserRegRespDto;

import java.util.List;
import java.util.UUID;

public interface UserService {
    UserRegRespDto registerNewUser(UserRegReqDto user);
    UserDto createUser(UserDto user);
    UserDto updateUser(UserDto user, Integer bloggerId);
    UserDto getUserByBloggerId(Integer bloggerId);
//    UserDto getUserById(Integer userId);
    List<UserDto> getAllUsers();
    void deleteUser(Integer bloggerId);
}
