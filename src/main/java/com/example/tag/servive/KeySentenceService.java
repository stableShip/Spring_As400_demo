package com.example.tag.servive;

import com.example.tag.domain.KeySentence;
import com.example.tag.mapper.KeySentenceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
