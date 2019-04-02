package com.example.tag.domain;

import lombok.Data;
import org.springframework.lang.Nullable;

import java.io.Serializable;

@Data
public class SecureData implements Serializable {
    private static final long serialVersionUID = 1L;
    private transient int id;

    @Nullable
    private transient String tagId;

    @Nullable
    private String security;

    @Nullable
    private String depositor;

    @Nullable
    private String facilities;

    @Nullable
    private String valuation;

    private String tag;

    @Nullable
    private String comments;

    public String findAll(String tagId) {
        StringBuffer sql = new StringBuffer("select * from secure_data where 1=1 ");
        if (tagId != null) {
            sql.append(" and tagId=#{tagId}");
        }
        return sql.toString();
    }

}
