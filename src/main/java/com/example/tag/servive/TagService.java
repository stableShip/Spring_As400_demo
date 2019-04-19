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

    public Tag[] getTag(Tag tag) {
        return tagDao.findAll(tag);
    }

    public Tag addTag(Tag tag) {
        tagDao.insert(tag);
        return tagDao.findById(tag.getId());
    }

}
