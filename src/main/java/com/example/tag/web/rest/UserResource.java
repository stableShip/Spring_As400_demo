package com.example.tag.web.rest;

import com.example.tag.domain.User;
import com.example.tag.servive.UserService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserResource {

    private final UserService userService;


    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody  @Valid User user) {
        User userInfo = userService.login(user);
        HashMap data = new HashMap();
        data.put("token", userInfo);
        return new ResponseEntity(data, HttpStatus.OK);
    }

    @PostMapping("/navigate")
    public ResponseEntity<Void> navigate(@RequestBody @Valid User user) {
        HashMap data = new HashMap();
        return new ResponseEntity(data, HttpStatus.OK);
    }


    @PostMapping("/addUser")
    public ResponseEntity<Void> addUser(@RequestBody @Valid User user) {

        //todo 验证创建者权限
        User userInfo = userService.addUser(user);
        HashMap data = new HashMap();
        data.put("user", userInfo);
        return new ResponseEntity(data, HttpStatus.OK);
    }

    @PostMapping("/users")
    public ResponseEntity<Void> getAllUsers(@RequestBody User user) {
        //todo 验证权限
        int page = (int) Optional.ofNullable(user.getPage()).orElse(1);
        int limit = (int) Optional.ofNullable(user.getLimit()).orElse(10);
        Page pageInfo = PageHelper.startPage(page, limit);
        User[] userList = userService.getAllUsers(user);
        HashMap data = new HashMap();
        long total = pageInfo.getTotal();
        data.put("users", userList);
        data.put("total", total);
        return new ResponseEntity(data, HttpStatus.OK);
    }

    @DeleteMapping("")
    public ResponseEntity<Void> deleteUser(@RequestBody User user) {
        //todo 验证权限
        int userId = userService.deleteUser(user);
        HashMap data = new HashMap();
        data.put("userId", userId);
        return new ResponseEntity(data, HttpStatus.OK);
    }

}
