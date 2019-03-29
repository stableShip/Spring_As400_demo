package com.example.tag.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class SecureData implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;

    private String tagId;

    private String security;

    private String depositor;

    private String facilities;

    private String valuation;

    private String tag;

    private String commonType;


    public String findAll(String tagId) {
        StringBuffer sql = new StringBuffer("select * from secure_data where 1=1 ");
        if (tagId != null) {
            sql.append(" and tagId=#{tagId}");
        }
        return sql.toString();
    }

}
