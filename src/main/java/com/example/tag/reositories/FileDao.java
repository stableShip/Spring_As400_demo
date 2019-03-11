package com.example.tag.reositories;

import com.example.tag.domain.File;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.apache.logging.log4j.ThreadContext.isEmpty;

@Repository
public class FileDao {
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public List<File> getAllFiles(String customerId, String type, int page, int size) {
        String sql = "select CORDCUSTID,CORDCRDATE,CORDCORTYP,CORDCRTIME from AMHCRMFPCU.GCCORDP where 1=1 and CORDBLKNUM = 1";
        MapSqlParameterSource param = new MapSqlParameterSource();
        //        String sql = "select  CORDCUSTID,CORDCRDATE,CORDCRTIME,CORDCORTYP from AMHCRMFPCU.GCCORDP limit 1";
//        return this.jdbcTemplate.query(sql, new Object[] {type, customerId}, rowMapper);
        page = Optional.ofNullable(page).orElse(1);
        size = Optional.ofNullable(size).orElse(10);
        int skip = (page - 1) * size;
        param.addValue("skip", skip);
        param.addValue("limit", size);
        if (Optional.ofNullable(customerId).isPresent() && !customerId.isEmpty()) {
            sql += " and CORDCUSTID = :customerId";
            param.addValue("customerId", customerId);
        }
        if (Optional.ofNullable(type).isPresent() && !type.isEmpty()) {
            sql += " and CORDCORTYP = :type";
            param.addValue("type", type);
        }
        sql += " LIMIT :limit  OFFSET :skip ";
        List<Map<String, Object>> rows = this.jdbcTemplate.queryForList(sql, param);
        List<File> files = new ArrayList<>();
        for (Map row : rows) {
            File file = new File();
            file.setCordcrTime(Optional.ofNullable(row.get("CORDCRTIME")).orElse("").toString());
            file.setCordcorType(Optional.ofNullable(row.get("CORDCORTYP")).orElse("").toString());
            file.setCordcustId(Optional.ofNullable(row.get("CORDCUSTID")).orElse("").toString());
            file.setCordcrDate(Optional.ofNullable(row.get("CORDCRDATE")).orElse("").toString());
            files.add(file);
        }
//        use rowMapper to transfer to File.
//        RowMapper<File> rowMapper = new BeanPropertyRowMapper<File>(File.class);
//        List<File> files= this.jdbcTemplate.query(sql, rowMapper);
        return files;
    }

    public boolean FileCount(String customerId, String type) {
//        String sql = "SELECT count(*) from AMHCRMFPCU.GCCORDP where CORDCORTYP = ? and CORDCUSTID = ? and CORDBLKNUM = 1";
//        RowMapper<File> rowMapper = new BeanPropertyRowMapper<File>(File.class);
//        int count = jdbcTemplate.query(sql, new Object[]{type, customerId}, Integer.class);
//        if(count == 0) {
//            return false;
//        } else {
        return true;
//        }
    }
}
