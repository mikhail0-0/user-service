package com.example.userservice;

import com.example.userservice.entity.User;
import com.example.userservice.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserServiceApplicationTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void cleanDatabase() {
        userRepository.deleteAll();
    }

    static class TestUser {
        public static String name = "user";
        public static String email = "user@email.com";
        public static Integer age = 32;

        static User createUser(){
            User user = new User();
            user.setName(TestUser.name);
            user.setEmail(TestUser.email);
            user.setAge(TestUser.age);

            return user;
        }
    }

    @Test
    void testCreateUser() throws Exception {
        User user = TestUser.createUser();

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id").exists())
                    .andExpect(jsonPath("$.createdAt").exists())
                    .andExpect(jsonPath("$.name").value(TestUser.name))
                    .andExpect(jsonPath("$.email").value(TestUser.email))
                    .andExpect(jsonPath("$.age").value(TestUser.age));
    }

    @Test
    void testGetUserById() throws Exception {
        User user = TestUser.createUser();
        userRepository.save(user);

        mockMvc.perform(get("/users/{id}", user.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.createdAt").exists())
            .andExpect(jsonPath("$.name").value(TestUser.name))
            .andExpect(jsonPath("$.email").value(TestUser.email))
            .andExpect(jsonPath("$.age").value(TestUser.age));
    }

    @Test
    void testDeleteUserById() throws Exception {
        User user = TestUser.createUser();
        userRepository.save(user);
        Long userId = user.getId();

        mockMvc.perform(delete("/users/{id}", user.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

        mockMvc.perform(get("/users/{id}", userId)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").doesNotExist());
    }
}
