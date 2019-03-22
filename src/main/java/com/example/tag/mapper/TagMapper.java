package com.example.tag.mapper;

import com.example.tag.domain.Tag;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface TagMapper {
    @SelectProvider(type = Tag.class, method = "findAll")
    Tag[] findAll(@Param("customerId") String customerId, @Param("type") String type, @Param("cordcrDate") String cordcrDate, @Param("cordcrTime") String cordcrTime);

    @Insert("")
    Tag insert(Tag tag);
}
