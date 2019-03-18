package com.example.tag.servive;

import com.example.tag.domain.File;
import com.example.tag.reositories.FileDao;
import com.example.tag.util.RTFUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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

    public String generateFile(String customerId, String type) throws IOException {
        Map<String, List<Map<String, byte[]>>> dataMap = this.fileDao.getDataMap(customerId, type);
        return RTFUtil.toRtf(dataMap);
    }

}
