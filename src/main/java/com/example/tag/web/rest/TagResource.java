package com.example.tag.web.rest;

import com.example.tag.domain.KeySentence;
import com.example.tag.domain.SecureData;
import com.example.tag.domain.Tag;
import com.example.tag.servive.KeySentenceService;
import com.example.tag.servive.SecureDataService;
import com.example.tag.servive.TagService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@Slf4j
public class TagResource {

    private final TagService tagService;

    private final SecureDataService secureDataService;

    private final KeySentenceService keySentenceService;

    public TagResource(TagService tagService, SecureDataService secureDataService, KeySentenceService keySentenceService) {
        this.tagService = tagService;
        this.secureDataService = secureDataService;
        this.keySentenceService = keySentenceService;
    }

    @PostMapping("/tags")
    public ResponseEntity<Void> getAllTags(@RequestBody Map<String, Object> params) {
        String customerId = (String) params.get("customerId");
        String type = (String) params.get("type");
        String date = (String) params.get("date");
        String time = (String) params.get("time");
        int page = (int) Optional.ofNullable(params.get("page")).orElse(1);
        int limit = (int) Optional.ofNullable(params.get("limit")).orElse(10);
        Page pageInfo = PageHelper.startPage(page, limit);
        Tag[] tag = this.tagService.getTag(customerId, type, date, time);
        long total = pageInfo.getTotal();
        HashMap body = new HashMap();
        body.put("status", 200);
        HashMap data = new HashMap();
        data.put("tag", tag);
        data.put("total", total);
        body.put("data", data);
        return new ResponseEntity(body, HttpStatus.OK);
    }

    @PostMapping("/tag/secure")
    public ResponseEntity<Void> getSecure(@RequestBody Map<String, Object> params) {
        String tagId = (String) params.get("tagId");
        SecureData[] secureDatas = this.secureDataService.getSecureData(tagId);
        HashMap body = new HashMap();
        body.put("status", 200);
        HashMap data = new HashMap();
        data.put("secureDatas", secureDatas);
        body.put("data", data);
        return new ResponseEntity(body, HttpStatus.OK);
    }

    @PostMapping("/tag/keysentence")
    public ResponseEntity<Void> getKeySentence(@RequestBody Map<String, Object> params) {
        String tagId = (String) params.get("tagId");
        KeySentence[] keySentences = this.keySentenceService.getKeySentence(tagId);
        HashMap body = new HashMap();
        body.put("status", 200);
        HashMap data = new HashMap();
        data.put("keySentences", keySentences);
        body.put("data", data);
        return new ResponseEntity(body, HttpStatus.OK);
    }
}
