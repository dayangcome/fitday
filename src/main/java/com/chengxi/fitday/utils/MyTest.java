package com.chengxi.fitday.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MyTest {
    public static void main(String[] args) {
        String mytime="2019-05-21";
        LocalDate res = LocalDate.parse(mytime, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        System.out.println(LocalDate.now());
    }
}
