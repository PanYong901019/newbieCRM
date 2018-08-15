package com.newbie.service.Impl;


import com.newbie.model.User;
import com.newbie.service.BaseService;
import com.newbie.service.UserService;
import com.newbie.utils.AppException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service(value = "userService")
public class UserServiceImpl extends BaseService implements UserService {
    @Override
    public User doLogin(String name, String password) throws AppException {
        User user = userDaoImpl.getUserByName(name);
        if (user != null) {
            if (user.getPassword().equals(password)) {
                if (user.getStatus() != 0) {
                    user.setLastLoginTime(String.valueOf(System.currentTimeMillis()));
                    userDaoImpl.update(user);
                    return user;
                } else {
                    throw new AppException(0, "restrict login \nplease ask your administrator for assistance");
                }
            } else {
                throw new AppException(0, "incorrect password");
            }
        } else {
            throw new AppException(0, "incorrect account");
        }
    }

    @Override
    public List<User> getAllUser() {
        return userDaoImpl.getAllUser();
    }

    @Override
    public Integer createUser(User user) {
        return userDaoImpl.createUser(user);
    }

    @Override
    public Integer changePassword(String userId, String oldPassword, String newPassword) {
        User user = userDaoImpl.getUserById(new Integer(userId));
        if (user != null) {
            if (user.getPassword().equals(oldPassword)) {
                user.setPassword(newPassword);
                return userDaoImpl.update(user);
            } else {
                throw new AppException(0, "incorrect password");
            }
        } else {
            throw new AppException(0, "incorrect account");
        }
    }

    @Override
    public User getUserById(String userId) {
        return userDaoImpl.getUserById(Integer.valueOf(userId));
    }

    @Override
    public Integer updateUser(String userId, String name, String password, String type, String status) {
        User user = userDaoImpl.getUserById(Integer.valueOf(userId));
        if (user != null) {
            user.setName(name).setPassword(password).setType(type).setStatus(status);
            return userDaoImpl.update(user);
        } else {
            throw new AppException(404, "user not found");
        }
    }

    @Override
    public List<User> getUserByType(String type) {
        return userDaoImpl.getUserByType(type);
    }
}
