package com.example.tag.mapper;

import com.example.tag.domain.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface TagMapper {
    @Select("SELECT * FROM tag")
    Tag[] select(int id);
}
