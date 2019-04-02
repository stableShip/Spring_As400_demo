package com.example.tag.web.rest;

import com.example.tag.domain.KeySentence;
import com.example.tag.domain.SecureData;
import com.example.tag.domain.Tag;
import com.example.tag.servive.KeySentenceService;
import com.example.tag.servive.SecureDataService;
import com.example.tag.servive.TagService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

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


    @PostMapping("/tag")
    public ResponseEntity<Void> addTag(@RequestBody Tag tag) {
        this.tagService.addTag(tag);
        this.secureDataService.addSecureDatas(tag);
        this.keySentenceService.addKeySentence(tag);
        HashMap body = new HashMap();
        body.put("status", 200);
        HashMap data = new HashMap();
        data.put("tag", tag);
        body.put("data", data);
        return new ResponseEntity(body, HttpStatus.OK);
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

    @GetMapping("/tag/detail/export")
    public ResponseEntity<Resource> exportDetail(Map<String, Object> params) throws IOException {
        Tag[] tags = this.tagService.getTag(null, null, null, null);
        List<Tag> tagList = Arrays.stream(tags).map(tag -> {
            String tagId = String.valueOf(tag.getId());
            SecureData[] secureDatas = this.secureDataService.getSecureData(tagId);
            KeySentence[] keySentences = this.keySentenceService.getKeySentence(tagId);
            tag.setSecureDatas(secureDatas);
            tag.setKeySentences(keySentences);
            return tag;
        }).collect(Collectors.toList());
        Gson gson = new Gson();
        String json = gson.toJson(tagList);
        Path path = Paths.get("./tags.json");
        Files.write(path, json.getBytes());

        InputStreamResource resource = new InputStreamResource(new FileInputStream("./tags.json"));
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=tags.json");
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }

}
