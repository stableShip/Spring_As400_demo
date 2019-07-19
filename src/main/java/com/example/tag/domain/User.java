package com.example.tag.domain;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class User {

    private transient int id;

    @NotNull
    private String name;

    @NotNull
    private transient String password;

    private String role;

    public User() {
        // for param inject
    }

    public User(String username, String password, String role) {
        this.name = username;
        this.password = password;
        this.role = role;
    }

    public String findUserByNameAndPwd(User user) {
        StringBuffer sql = new StringBuffer("select name from user where name=#{name} and password = #{password} ");
        return sql.toString();
    }

}

