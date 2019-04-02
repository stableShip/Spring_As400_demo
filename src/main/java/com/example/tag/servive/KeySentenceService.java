package com.example.tag.servive;

import com.example.tag.domain.KeySentence;
import com.example.tag.domain.Tag;
import com.example.tag.mapper.KeySentenceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.stream.Collectors;

@Service
public class KeySentenceService {

    private KeySentenceMapper keySentenceMapper;

    @Autowired
    public KeySentenceService(KeySentenceMapper mapper) {
        this.keySentenceMapper = mapper;
    }

    public KeySentence[] getKeySentence(String tagId) {
        return keySentenceMapper.findAll(tagId);
    }

    public void addKeySentence(Tag tag) {
        KeySentence[] keySentences = tag.getKeySentences();
        Arrays.stream(keySentences).map(keySentence -> {
            keySentence.setTagId(Integer.toString(tag.getId()));
            return keySentenceMapper.insect(keySentence);
        }).collect(Collectors.toList());
    }
}
