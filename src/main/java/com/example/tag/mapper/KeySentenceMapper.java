package com.example.tag.mapper;

import com.example.tag.domain.KeySentence;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface KeySentenceMapper {
    @SelectProvider(type = KeySentence.class, method = "findAll")
    KeySentence[] findAll(@Param("tagId") String tagId);

}
