package com.example.tag.web.rest;

import com.example.tag.domain.Tag;
import com.example.tag.servive.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Slf4j
public class TagResource {

    private final TagService tagService;

    public TagResource(TagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping("/tags")
    public ResponseEntity<Void> getAllTags(@RequestBody Map<String, Object> params) {
        Tag[] tag = this.tagService.getTag(1);
        HashMap body = new HashMap();
        body.put("status", 200);
        HashMap data = new HashMap();
        data.put("tag", tag);
        body.put("data", data);
        return new ResponseEntity(body, HttpStatus.OK);
    }
}
