package com.example.tag.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class Tag implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;

    private String cordcustId;

    private String cordcorType;

    private String cordcrDate;

    private String cordcrTime;

    public String findAll(String customerId, String type, String cordcrDate, String cordcrTime) {
        StringBuffer sql = new StringBuffer("select * from tag where 1=1 ");
        if (type != null) {
            sql.append(" and cordcorType =#{type}");
        }
        if (customerId != null) {
            sql.append(" and cordcustId=#{customerId}");
        }
        if (cordcrDate != null) {
            sql.append(" and cordcrDate=#{cordcrDate}");
        }
        if (cordcrTime != null) {
            sql.append(" and cordcrTime=#{cordcrTime}");
        }
        return sql.toString();
    }

}
