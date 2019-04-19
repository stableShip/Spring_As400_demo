package com.example.tag.servive;

import com.example.tag.domain.SecureData;
import com.example.tag.domain.Tag;
import com.example.tag.mapper.SecureDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.stream.Collectors;

@Service
public class SecureDataService {

    private SecureDataMapper secureDataMapper;

    @Autowired
    public SecureDataService(SecureDataMapper mapper) {
        this.secureDataMapper = mapper;
    }

    public SecureData[] getSecureData(String tagId) {
        return secureDataMapper.findAll(tagId);
    }

    public void addSecureDatas(Tag tag) {
        SecureData[] secureDatas = tag.getSecureDatas();
        Arrays.stream(secureDatas).map(secureData -> {
            secureData.setTagId(Integer.toString(tag.getId()));
            return secureDataMapper.insect(secureData);
        }).collect(Collectors.toList());
    }

    public int deleteSecure(String id) {
        return secureDataMapper.deleteByTagId(id);
    }
}
