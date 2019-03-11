package com.example.tag.mapper;

import com.example.tag.domain.File;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FileRowMapper implements RowMapper<File> {
    @Override
    public File mapRow(ResultSet row, int rowNum) throws SQLException {
        File file = new File();
        file.setCordcrDate(row.getString("CORDCRDATE"));
        file.setCordcustId(row.getString("CORDCUSTID"));
        file.setCordcorType(row.getString("CORDCORTYP"));
        file.setCordcrTime(row.getString("CORDCRTIME"));
        return file;
    }
}
