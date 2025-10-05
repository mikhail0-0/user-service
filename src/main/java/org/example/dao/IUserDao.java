package org.example.dao;

import org.example.models.User;

import java.util.List;

public interface IUserDao {
    List<User> find();
    User findById(int id);
    void save(User user);
    void update(User user);
    void delete(User user);
}
