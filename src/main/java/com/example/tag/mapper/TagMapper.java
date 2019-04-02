package com.example.tag.mapper;

import com.example.tag.domain.Tag;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface TagMapper {
    @SelectProvider(type = Tag.class, method = "findAll")
    Tag[] findAll(@Param("customerId") String customerId, @Param("type") String type, @Param("cordcrDate") String cordcrDate, @Param("cordcrTime") String cordcrTime);

    @Insert("insert into tag(cordcustId,cordcorType, cordcrDate, cordcrTime) values(#{cordcustId}, #{cordcorType}, #{cordcrDate}, #{cordcrTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Tag tag);
}
