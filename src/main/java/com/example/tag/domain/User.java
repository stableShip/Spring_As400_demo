package com.example.tag.domain;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class User {

    @NotNull
    private String name;

    @NotNull
    private transient String password;

    public String findUserByNameAndPwd(User user) {
        StringBuffer sql = new StringBuffer("select name from user where name=#{name} and password = #{password} ");
        return sql.toString();
    }
}
