package com.example.tag.servive;

import com.example.tag.domain.SecureData;
import com.example.tag.mapper.SecureDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
