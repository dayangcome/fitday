package com.chengxi.fitday.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Buydto {
    private Long uid;   //购买者id
    private Long proid; //商品id
    private int type;   //支付方式
    private int num;   //支付数量
    private int mycut;  //使用优惠券
    private BigDecimal finalpay;
    private String address;
}
