package com.chengxi.fitday.dto;

import lombok.Data;

import java.util.List;

@Data
public class Foodplandto {
    private Long userid;
    private List<Fooddto> foods;
}
