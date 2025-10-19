package com.example.userservice.service;

import com.example.userservice.dto.UserDto;
import com.example.userservice.entity.User;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.utils.MappingUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final MappingUtils mappingUtils;

    public UserService(UserRepository userRepository, MappingUtils mappingUtils){
        this.userRepository = userRepository;
        this.mappingUtils = mappingUtils;
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream().map(mappingUtils::mapToUserDto).collect(Collectors.toList());
    }

    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if(user == null){
            return null;
        }
        return mappingUtils.mapToUserDto(user);
    }

    public UserDto createUser(User user){
        return mappingUtils.mapToUserDto(userRepository.save(user));
    }

    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }
}
