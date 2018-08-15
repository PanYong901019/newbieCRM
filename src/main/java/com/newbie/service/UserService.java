package com.newbie.service;


import com.newbie.model.User;
import com.newbie.utils.AppException;

import java.util.List;

public interface UserService {
    User doLogin(String name, String password) throws AppException;

    List<User> getAllUser();

    Integer createUser(User user);

    Integer changePassword(String userId, String oldPassword, String newPassword);

    User getUserById(String userId);

    Integer updateUser(String userId, String name, String password, String type, String status);

    List<User> getUserByType(String type);
}
