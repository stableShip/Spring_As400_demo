package com.example.tag.reositories;

import com.example.tag.domain.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.*;

@Repository
public class FileDao {
    @Autowired
    @Qualifier("as400JdbcTemplate")
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Value("${spring.datasource.as400.library}")
    private String library;

    public List<File> getAllFiles(String customerId, String type, int page, int size) {
        String sql = MessageFormat.format("select CORDCUSTID,CORDCRDATE,CORDCORTYP,CORDCRTIME from {0}.GCCORDP where 1=1 and CORDBLKNUM = 1", library);
        MapSqlParameterSource param = new MapSqlParameterSource();

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
        sql += " ORDER BY CORDCRDATE DESC LIMIT :limit  OFFSET :skip ";
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
//        String sql = "select  CORDCUSTID,CORDCRDATE,CORDCRTIME,CORDCORTYP from AMHCRMFPCU.GCCORDP limit 1";
//        return this.jdbcTemplate.query(sql, new Object[] {type, customerId}, rowMapper);
//        RowMapper<File> rowMapper = new BeanPropertyRowMapper<File>(File.class);
//        List<File> files= this.jdbcTemplate.query(sql, rowMapper);
        return files;
    }

    public Integer getFilesCount(String customerId, String type) {
        String sql = MessageFormat.format("select count(*) from {0}.GCCORDP where 1=1 and CORDBLKNUM = 1", library);
        MapSqlParameterSource param = new MapSqlParameterSource();
        if (Optional.ofNullable(customerId).isPresent() && !customerId.isEmpty()) {
            sql += " and CORDCUSTID = :customerId";
            param.addValue("customerId", customerId);
        }
        if (Optional.ofNullable(type).isPresent() && !type.isEmpty()) {
            sql += " and CORDCORTYP = :type";
            param.addValue("type", type);
        }
        int count = this.jdbcTemplate.queryForObject(sql, param, Integer.class);
        return count;
    }


    /**
     * get Data
     *
     * @param custId
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public Map<String, List<Map<String, byte[]>>> getDataMap(String custId, String type, String date, String time) {
        Map<String, List<Map<String, byte[]>>> map = new HashMap<String, List<Map<String, byte[]>>>();
        StringBuffer sql = new StringBuffer();
        sql.append(" select t.CORDCUSTID,t.CORDCRDATE,t.CORDCRTIME,t.CORDBLKNUM,t.CORDCORTXT from " + library + ".GCCORDP t inner join  ");
        sql.append(" (select CORDCUSTID,CORDCRDATE,CORDCRTIME from " + library + ".GCCORDP where CORDCORTYP =  '" + type.trim() + "' and CORDCUSTID = '" + custId + "' and CORDCRDATE = '" + date + "' and CORDCRTIME = '" + time + "' ORDER BY " +
                "CORDCRDATE DESC LIMIT 1 ) m on  ");
        sql.append(" t.CORDCUSTID = m.CORDCUSTID and t.CORDCRDATE = m.CORDCRDATE and t.CORDCRTIME = m.CORDCRTIME ");

        jdbcTemplate.query(new String(sql), rs -> {
            List<Map<String, byte[]>> list;
            String key = rs.getString("CORDCUSTID") + "-" + rs.getString("CORDCRDATE") + "-"
                    + rs.getString("CORDCRTIME");
            Map<String, byte[]> map1 = new TreeMap<String, byte[]>(new Comparator<String>() {
                public int compare(String obj1, String obj2) {
                    return obj1.compareTo(obj2);
                }
            });
            if (map.containsKey(key)) {
                list = map.get(key);
                map1.put(rs.getString("CORDBLKNUM"), rs.getBytes("CORDCORTXT"));// CORDCORTXT
            } else {
                list = new ArrayList<Map<String, byte[]>>();
                map1.put(rs.getString("CORDBLKNUM"), rs.getBytes("CORDCORTXT"));
            }
            list.add(map1);
            map.put(key, list);
        });
        return map;
    }
}
