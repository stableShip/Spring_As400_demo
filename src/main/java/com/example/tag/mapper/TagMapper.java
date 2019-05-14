package com.example.tag.mapper;

import com.example.tag.domain.Tag;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface TagMapper {

    @Select("select * from tag where id = #{id}")
    Tag findById(@Param("id") int id);

    @Delete("delete from tag where id = #{id}")
    int deleteById(@Param("id") String id);

    @SelectProvider(type = Tag.class, method = "findAll")
    Tag[] findAll(Tag tag);

    @Insert("insert into tag(cordcustId, cordcorType, cordcrDate, cordcrTime, remark, serialNo, customerName, createdAt) values(#{cordcustId}, #{cordcorType}, #{cordcrDate}, #{cordcrTime}, #{remark}, #{serialNo}, #{customerName}, #{createdAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert(Tag tag);

    @Update("update tag set cordcustId=#{cordcustId}, cordcorType=#{cordcorType}, cordcrDate=#{cordcrDate}, cordcrTime=#{cordcrTime}, remark=#{remark}, serialNo = #{serialNo}, customerName = #{customerName} where id = #{id}")
    int update(Tag tag);
}
