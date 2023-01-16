package com.chengxi.fitday.dto;

import lombok.Data;

@Data
public class Empregisterdto {
    private String account;
    private String password;
    private String againpassword;
    private String name;
    private String phone;
    private int rolenum;
}
