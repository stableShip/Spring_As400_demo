package com.example.tag.servive;

import com.example.tag.domain.File;
import com.example.tag.reositories.FileDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class FileService {
    private final FileDao fileDao;

    public FileService(FileDao fileDao) {
        this.fileDao = fileDao;
    }


    public List<File> getAllFiles(String customerId, String type, int page, int limit) {
        return this.fileDao.getAllFiles(customerId, type, page, limit);
    }

    public Integer getFilesCount(String customerId, String type) {
        return this.fileDao.getFilesCount(customerId, type);
    }

}
