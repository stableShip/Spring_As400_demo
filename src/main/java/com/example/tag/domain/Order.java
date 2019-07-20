package com.example.tag.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Order {

    private int id;

    private String systemId;

    private String applyEnterpriseId;

    private String deliverWay;

    private String deliverAddress;

    private int status;

    private BigDecimal fee;

    private BigDecimal deliverFee;

    private int payStatus;



}
