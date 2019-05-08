package com.example.tag.mapper;

import com.example.tag.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface UserMapper {

    @SelectProvider(type = User.class, method = "findUserByNameAndPwd")
    User findUserByNameAndPwd(User tag);
}
