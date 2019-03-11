package com.example.tag.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class File implements Serializable {

    private static final long serialVersionUID = 1L;

    private String cordcustId;

    private String cordcorType;

    private String cordcrDate;

    private String cordcrTime;

}
