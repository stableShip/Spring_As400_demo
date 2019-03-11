package com.example.tag.web.rest;

import com.example.tag.domain.File;
import com.example.tag.servive.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Slf4j
public class FileResource {

    private final FileService fileService;

    public FileResource(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/files")
    public ResponseEntity<Void> getAllFiles(@RequestBody Map<String, Object> params) {
        String customerId = (String) params.get("customerId");
        String type = (String) params.get("type");
        int page = (int) params.get("page");
        int limit = (int) params.get("limit");
        List files = this.fileService.getAllFiles(customerId, type, page, limit);
        HashMap body = new HashMap();
        body.put("status", 200);
        HashMap data = new HashMap();
        data.put("files", files);
        body.put("data", data);
        return new ResponseEntity(body, HttpStatus.OK);
    }
}
