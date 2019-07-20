package com.example.tag.mapper;

import com.example.tag.domain.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface UserMapper {

    @Select("select * from user where id = #{id}")
    User findById(@Param("id") int id);

    @SelectProvider(type = User.class, method = "findUserByNameAndPwd")
    User findUserByNameAndPwd(User user);

    @Insert("insert into user(name, password, role) values(#{name}, #{password}, #{role})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int createUser(User user);

    @Select("select * from user where name = #{name}")
    User findUserByName(String name);


    @SelectProvider(type = User.class, method = "findAll")
    User[] findAll(User user);

    @Delete("delete from user where id = #{id}")
    int deleteUser(User user);
}
