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
        HashMap body = new HashMap();
        body.put("code", 200);
        HashMap data = new HashMap();
        data.put("token", userInfo);
        body.put("data", data);
        return new ResponseEntity(body, HttpStatus.OK);
    }
}
