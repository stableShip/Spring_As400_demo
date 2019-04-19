package com.example.tag.mapper;

import com.example.tag.domain.SecureData;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface SecureDataMapper {
    @SelectProvider(type = SecureData.class, method = "findAll")
    SecureData[] findAll(@Param("tagId") String tagId);

    @Insert("insert into secure_data(tagId, security, depositor, facilities, valuation, comments, tag) values(#{tagId},#{security}, #{depositor}, #{facilities}, #{valuation}, #{comments}, #{tag} )")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insect(SecureData secureData);

    @Delete("delete from secure_data where tagId = #{tagId}")
    int deleteByTagId(@Param("tagId") String TagId);
}
