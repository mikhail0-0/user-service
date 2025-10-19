package com.example.userservice.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UserDto {
    Long id;
    String name;
    String email;
    Integer age;
    Date createdAt;

    final String message = "this is dto";
}
