package com.example.tag.servive;

import com.example.tag.domain.File;
import com.example.tag.domain.Tag;
import com.example.tag.mapper.TagMapper;
import com.example.tag.reositories.FileDao;
import com.example.tag.util.RTFUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FileService {
    private final FileDao fileDao;

    private final TagMapper tagDao;

    public FileService(FileDao fileDao, TagMapper tagDao) {
        this.fileDao = fileDao;
        this.tagDao = tagDao;
    }


    public List<File> getAllFiles(String customerId, String type, int page, int limit) {
        List<File> files = this.fileDao.getAllFiles(customerId, type, page, limit);
        files = files.stream().map(file -> {
            Tag tag = new Tag();
            tag.setCordcustId(file.getCordcustId());
            tag.setCordcrTime(file.getCordcrTime());
            tag.setCordcorType(file.getCordcorType());
            tag.setCordcrDate(file.getCordcrDate());
            Tag[] tags = tagDao.findAll(tag);
            file.setStatus("None");
            if (tags.length > 0) {
                file.setStatus("existed");
            }
            return file;
        }).collect(Collectors.toList());
        return files;
    }

    public Integer getFilesCount(String customerId, String type) {
        return this.fileDao.getFilesCount(customerId, type);
    }

    public String generateFile(String customerId, String type, String date, String time) throws IOException {
        Map<String, List<Map<String, byte[]>>> dataMap = this.fileDao.getDataMap(customerId, type, date, time);
        return RTFUtil.toRtf(dataMap);
    }

    public String getRtfString(String customerId, String type, String date, String time) throws IOException {
        Map<String, List<Map<String, byte[]>>> dataMap = this.fileDao.getDataMap(customerId, type, date, time);
        return RTFUtil.toRtfString(dataMap);
    }

}
