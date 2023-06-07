package com.codewithratchez.blog.services.impl;

import com.codewithratchez.blog.config.AppConstants;
import com.codewithratchez.blog.entities.Role;
import com.codewithratchez.blog.entities.User;
import com.codewithratchez.blog.exceptions.ApiException;
import com.codewithratchez.blog.exceptions.ResourceNotFoundException;
import com.codewithratchez.blog.payloads.UserDto;
import com.codewithratchez.blog.payloads.UserRegReqDto;
import com.codewithratchez.blog.payloads.UserRegRespDto;
import com.codewithratchez.blog.repositories.RoleRepo;
import com.codewithratchez.blog.repositories.UserRepo;
import com.codewithratchez.blog.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepo roleRepo;

    @Override
    public UserDto createUser(UserDto userDto){
        User user = this.dtoToUser(userDto);

        //encode the password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        //set roles for users
//        Role role = roleRepo.findById(AppConstants.ROLE_USER).get();
        Role role;
        Optional<Role> roleOpt = roleRepo.findById(AppConstants.ROLE_USER);
        // return error, role not configured
        role = roleOpt.orElseGet(() -> new Role(3, "ROLE_USER"));

        user.getRoles().add(role);

        SecureRandom secureRandom = new SecureRandom();
        int rnum = secureRandom.nextInt(10000000);
        String snum = String.valueOf(rnum);
        BigInteger bloggerId = new BigInteger(snum);

        user.setBloggerId(Integer.valueOf(bloggerId.toString()));

        User savedUser = userRepo.save(user);

        return this.userToDto(savedUser);
    }
    @Override
    public UserDto updateUser(UserDto userDto, Integer bloggerId){
        User user = userRepo.findByBloggerId(bloggerId).orElseThrow(()-> new ResourceNotFoundException("User"));
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
//        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());

        User updatedUser = userRepo.save(user);
        UserDto userDto1 = this.userToDto(updatedUser);
        return userDto1;
    }
    @Override
    public UserDto getUserByBloggerId(Integer bloggerId){
        User user = userRepo.findByBloggerId(bloggerId).orElseThrow(() -> new ResourceNotFoundException("User"));
        return this.userToDto(user);
    }
    @Override
    public List<UserDto> getAllUsers(){
        List<User> users = userRepo.findAll();
        List<UserDto> userDtos = users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());
        return userDtos;
    }
    public void deleteUser(Integer bloggerId){
        User user = userRepo.findByBloggerId(bloggerId).orElseThrow(()-> new ResourceNotFoundException("User"));
        userRepo.delete(user);
    }
    private User dtoToUser(UserDto userDto){
        User user = modelMapper.map(userDto, User.class);
//        user.setId(userDto.getId());
//        user.setUsername(userDto.getUsername());
//        user.setEmail(userDto.getEmail());
//        user.setAbout(userDto.getAbout());
//        user.setPassword(userDto.getPassword());
        return user;
    }
    private UserDto userToDto(User user){
        UserDto userDto = modelMapper.map(user, UserDto.class);
        return userDto;
    }

//    private String UUIDGenerator(User user) {
//        SecureRandom secureRandom = new SecureRandom();
//        int rnum = secureRandom.nextInt(1000);
//        String snum = String.valueOf(rnum);
//        BigInteger val = new BigInteger("2023" + "/" + snum);
//        SecureRandom random = new SecureRandom();
//        int upperbound = 10000000;
//        int user = random.nextInt(upperbound);
//        log.info(String.valueOf(user.hashCode()));
//        return String.valueOf(user.hashCode());
//    }

    @Override
    public UserRegRespDto registerNewUser(UserRegReqDto userDto) {
        User user = modelMapper.map(userDto, User.class);

        // check for email exists in database
        if (userRepo.existsByEmail(user.getEmail())){
            throw new ApiException("User with this email already exists");
        }

        //encode the password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        //set roles for users
//        Role role = roleRepo.findById(AppConstants.ROLE_USER).get();
        Role role;
        Optional<Role> roleOpt = roleRepo.findById(AppConstants.ROLE_USER);
        // return error, role not configured
        role = roleOpt.orElseGet(() -> new Role(3, "ROLE_USER"));

        user.getRoles().add(role);

        SecureRandom secureRandom = new SecureRandom();
        int rnum = secureRandom.nextInt(10000000);
        String snum = String.valueOf(rnum);
        BigInteger bloggerId = new BigInteger(snum);

        user.setBloggerId(Integer.valueOf(bloggerId.toString()));

//        user.setBloggerId(UUIDGenerator(user));

        User newUser = userRepo.save(user);
        return modelMapper.map(newUser, UserRegRespDto.class);
    }
}
