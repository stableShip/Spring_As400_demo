package com.example.tag.servive;

import com.example.tag.domain.Tag;
import com.example.tag.mapper.TagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagService {

    private TagMapper tagDao;

    @Autowired
    public TagService(TagMapper dao) {
        this.tagDao = dao;
    }

    public Tag[] getTag(String customerId, String type, String date, String time) {
        return tagDao.findAll(customerId, type, date, time);
    }

}
