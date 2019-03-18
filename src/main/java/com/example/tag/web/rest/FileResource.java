package com.example.tag.web.rest;

import com.example.tag.servive.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
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
        int total = this.fileService.getFilesCount(customerId, type);
        HashMap body = new HashMap();
        body.put("status", 200);
        HashMap data = new HashMap();
        data.put("files", files);
        data.put("total", total);
        body.put("data", data);
        return new ResponseEntity(body, HttpStatus.OK);
    }

    @PostMapping("/files/rtf")
    public ResponseEntity<Void> generateRTF(@RequestBody Map<String, Object> params) throws IOException {
        String customerId = (String) params.get("customerId");
        String type = (String) params.get("type");
        String fileName = this.fileService.generateFile(customerId, type);
        HashMap body = new HashMap();
        body.put("status", 200);
        HashMap data = new HashMap();
        data.put("file", fileName);
        body.put("data", data);
        return new ResponseEntity(body, HttpStatus.OK);
    }
}
