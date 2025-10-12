package org.example.services;

import org.example.dao.IUserDao;
import org.example.dao.UserDao;
import org.example.models.User;

import java.util.List;

public class UserService {
    private final IUserDao userDao = new UserDao();

    public List<User> find() {return userDao.find();}

    public User findUser(int id){
        return userDao.findById(id);
    }

    public void saveUser(User user){
        userDao.save(user);
    }

    public void updateUser(User user){
        userDao.update(user);
    }

    public void deleteUser(User user){
        userDao.delete(user);
    }

}
