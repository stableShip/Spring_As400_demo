package com.example.tag.web.rest;

import com.example.tag.domain.File;
import com.example.tag.servive.FileService;
import com.example.tag.util.OfficeParseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
        int page = (int) Optional.ofNullable(params.get("page")).orElse(1);
        int limit = (int) Optional.ofNullable(params.get("limit")).orElse(10);
        List<File> files = this.fileService.getAllFiles(customerId, type, page, limit);
        int total = this.fileService.getFilesCount(customerId, type);
        HashMap body = new HashMap();
        body.put("code", 200);
        HashMap data = new HashMap();
        data.put("files", files);
        data.put("total", total);
        body.put("data", data);
        return new ResponseEntity(body, HttpStatus.OK);
    }

    @PostMapping("/files/rtf")
    public ResponseEntity<Void> generateRTF(@RequestBody Map<String, Object> params) throws Exception {
        String customerId = (String) params.get("customerId");
        String type = (String) params.get("type");
        String date = (String) params.get("date");
        String time = (String) params.get("time");
        String filePath = this.fileService.generateFile(customerId, type, date, time);
        String outFilePath = OfficeParseUtil.coverToPdf(filePath, filePath + ".pdf");

        HashMap body = new HashMap();
        body.put("code", 200);
        HashMap data = new HashMap();
        data.put("file", Files.readAllBytes(Paths.get(outFilePath)));
        body.put("data", data);
        Files.deleteIfExists(Paths.get(outFilePath));
        Files.deleteIfExists(Paths.get(filePath));
        return new ResponseEntity(body, HttpStatus.OK);
    }
}
