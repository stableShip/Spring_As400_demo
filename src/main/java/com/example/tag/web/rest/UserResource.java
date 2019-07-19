package com.example.tag.web.rest;

import com.example.tag.domain.User;
import com.example.tag.servive.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

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
        String role = user.getRole();

        HashMap data = new HashMap();
        return new ResponseEntity(data, HttpStatus.OK);
    }


    @PostMapping("/addUser")
    public ResponseEntity<Void> login(@RequestBody Map<String, Object> params) {
        String token = (String) params.get("token");
        String username = (String) params.get("username");
        String password = (String) params.get("password");
        String role = (String) params.get("role");

        //todo 验证创建者权限
        User userInfo = userService.addUser(new User(username, password, role));
        HashMap data = new HashMap();
        data.put("user", userInfo);
        return new ResponseEntity(data, HttpStatus.OK);
    }


}
