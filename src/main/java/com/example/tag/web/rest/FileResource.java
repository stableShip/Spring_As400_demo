package com.example.tag.web.rest;

import com.example.tag.domain.File;
import com.example.tag.servive.FileService;
import com.example.tag.util.RTFUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
        files.stream().map(file -> {
            String id = file.getCordcustId();
            String type_ = file.getCordcorType();
            String date = file.getCordcrDate();
            String time = file.getCordcrTime();
            String filePath = null;
            try {
                filePath = this.fileService.generateFile(id, type_, date, time);
                RTFUtil.coverToHtml(new FileReader(filePath));
            } catch (Exception e) {
                e.printStackTrace();
                file.setStatus("broken");
            }
            return file;
        }).collect(Collectors.toList());
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
    public ResponseEntity<Void> generateRTF(@RequestBody Map<String, Object> params) throws Exception {
        String customerId = (String) params.get("customerId");
        String type = (String) params.get("type");
        String date = (String) params.get("date");
        String time = (String) params.get("time");
        String filePath = this.fileService.generateFile(customerId, type, date, time);
        Path currentRelativePath = Paths.get(filePath);
        String html = RTFUtil.coverToHtml(new FileReader(filePath));
        Path p = Paths.get(filePath);
        String fileName = p.getFileName().toString();
        Path path = Paths.get(fileName + ".html");
        Files.write(path, html.getBytes());
//        filePath = currentRelativePath.toAbsolutePath().toString();
        HashMap body = new HashMap();
        body.put("status", 200);
        HashMap data = new HashMap();
        data.put("file", path.toAbsolutePath().toString());
        body.put("data", data);
        return new ResponseEntity(body, HttpStatus.OK);
    }
}
