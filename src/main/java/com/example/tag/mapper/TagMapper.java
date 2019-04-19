package com.example.tag.mapper;

import com.example.tag.domain.Tag;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface TagMapper {

    @Select("select * from tag where id = #{id}")
    Tag findById(@Param("id") int id);

    @SelectProvider(type = Tag.class, method = "findAll")
    Tag[] findAll(Tag tag);

    @Insert("insert into tag(cordcustId, cordcorType, cordcrDate, cordcrTime, remark, serialNo, customerName) values(#{cordcustId}, #{cordcorType}, #{cordcrDate}, #{cordcrTime}, #{remark}, #{serialNo}, #{customerName})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert(Tag tag);
}
