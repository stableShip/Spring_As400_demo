package com.example.tag.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class KeySentence implements Serializable {
    private static final long serialVersionUID = 1L;
    private transient int id;

    private transient String tagId;

    private String type;

    private String content;

    public String findAll(String tagId) {
        StringBuffer sql = new StringBuffer("select * from key_sentence where 1=1 ");
        if (tagId != null) {
            sql.append(" and tagId=#{tagId}");
        }
        return sql.toString();
    }

}
