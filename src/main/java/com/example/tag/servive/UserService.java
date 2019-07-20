package com.example.tag.servive;

import com.example.tag.domain.User;
import com.example.tag.exception.BaseException;
import com.example.tag.exception.ResourceNotFoundException;
import com.example.tag.mapper.UserMapper;
import com.example.tag.util.ErrorMsgConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserMapper userMapper;


    @Autowired
    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public User login(User user) {
        User findUser = userMapper.findUserByNameAndPwd(user);
        if (findUser == null) {
            throw new ResourceNotFoundException("user not found");
        }
        return user;
    }

    public User addUser(User user) {
        User findUser = userMapper.findUserByName(user.getName());
        if (findUser != null) {
            throw new BaseException(ErrorMsgConst.User_Exist);
        }
        userMapper.createUser(user);
        return userMapper.findById(user.getId());
    }

    public User[] getAllUsers(User user) {
        return userMapper.findAll(user);
    }

    public int deleteUser(User user) {
        return userMapper.deleteUser(user);
    }

}
