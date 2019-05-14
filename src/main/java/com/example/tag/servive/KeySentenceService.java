package com.example.tag.servive;

import com.example.tag.domain.KeySentence;
import com.example.tag.domain.Tag;
import com.example.tag.mapper.KeySentenceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

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

    public KeySentence[] addKeySentence(Tag tag) {
        KeySentence[] keySentences = tag.getKeySentences();
        return Arrays.stream(keySentences).map(keySentence -> {
            keySentence.setTagId(Integer.toString(tag.getId()));
            keySentenceMapper.insect(keySentence);
            return keySentence;
        }).toArray(KeySentence[]::new);
    }

    public int deleteKeySentence(String id) {
        return keySentenceMapper.deleteKeySentenceByTagId(id);
    }
}
