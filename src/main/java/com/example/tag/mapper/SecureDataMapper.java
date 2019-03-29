package com.example.tag.mapper;

import com.example.tag.domain.SecureData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface SecureDataMapper {
    @SelectProvider(type = SecureData.class, method = "findAll")
    SecureData[] findAll(@Param("tagId") String tagId);

}
