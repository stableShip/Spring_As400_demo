package com.example.tag.domain;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class User {

    private int id;

    @NotNull
    @Email
    private String name;

    @NotNull
    private transient String password;

    private String role;

    @NotNull
    private int status;

    private int createdAt;

    private int updatedAt;

    private String token;

    private int page;

    private int limit;

    public User() {
        // for param inject
    }

    public String findUserByNameAndPwd(User user) {
        StringBuffer sql = new StringBuffer( "select name from user where name=#{name} and password = #{password} " );
        return sql.toString();
    }

    public String findAll(User user) {
        StringBuffer sql = new StringBuffer( "select * from user where 1=1 " );
        if (user.getId() != 0) {
            sql.append( " and id =#{id}" );
        }
        if (user.getName() != null) {
            sql.append( " and name=#{name}" );
        }
        if (user.getRole() != null) {
            sql.append( " and role=#{role}" );
        }
//        if (user.getCreatedAt() != null) {
//            sql.append(" and getCreatedAt=#{getCreatedAt}");
//        }
//        if (user.getUpdatedAt() != null) {
//            sql.append(" and updateAt=#{updateAt}");
//        }
        return sql.toString();
    }

}


