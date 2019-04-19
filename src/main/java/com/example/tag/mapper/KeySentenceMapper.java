package com.example.tag.mapper;

import com.example.tag.domain.KeySentence;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface KeySentenceMapper {
    @SelectProvider(type = KeySentence.class, method = "findAll")
    KeySentence[] findAll(@Param("tagId") String tagId);


    @Insert("insert into key_sentence(tagId, type, content) values(#{tagId},#{type}, #{content})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insect(KeySentence keySentence);

    @Delete("delete from key_sentence where tagId=#{tagId}")
    int deleteKeySentenceByTagId(@Param("tagId") String tagId);
}
