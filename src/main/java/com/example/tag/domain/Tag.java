package com.example.tag.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class Tag implements Serializable {
    private static final long serialVersionUID = 1L;

    private transient int id;

    private String cordcustId;

    private String cordcorType;

    private String cordcrDate;

    private String cordcrTime;

    private String remark;

    private String serialNo;

    private String customerName;

    private SecureData[] secureDatas;

    private KeySentence[] keySentences;

    private long createdAt;

    public String findAll(Tag tag) {
        StringBuffer sql = new StringBuffer("select * from tag where 1=1 ");
        if (tag.getCordcorType() != null) {
            sql.append(" and cordcorType =#{cordcorType}");
        }
        if (tag.getCordcustId() != null) {
            sql.append(" and cordcustId=#{customerId}");
        }
        if (tag.getCordcrDate() != null) {
            sql.append(" and cordcrDate=#{cordcrDate}");
        }
        if (tag.getCordcrTime() != null) {
            sql.append(" and cordcrTime=#{cordcrTime}");
        }
        if (tag.getCustomerName() != null) {
            sql.append(" and customerName=#{customerName}");
        }
        if (tag.getSerialNo() != null) {
            sql.append(" and serialNo=#{serialNo}");
        }
        return sql.toString();
    }

}
