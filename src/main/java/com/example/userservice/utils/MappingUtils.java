package com.example.userservice.utils;

import com.example.userservice.dto.UserDto;
import com.example.userservice.entity.User;
import org.springframework.stereotype.Service;

@Service
public class MappingUtils {
    public UserDto mapToUserDto(User user){
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setAge(user.getAge());
        dto.setCreatedAt(user.getCreatedAt());

        return dto;
    }

    public User mapToUser(UserDto dto){
        User user = new User();
        user.setId(dto.getId());
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setAge(dto.getAge());
        user.setCreatedAt(dto.getCreatedAt());

        return user;
    }
}
