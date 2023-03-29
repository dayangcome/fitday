package com.chengxi.fitday.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.regex.Pattern;

public class MyTest {
    public static void main(String[] args) {
        String num1=(int)(Math.random()*1000000)+"";           //6位随机数
        String num2=(int)(Math.random()*1000000)+"";           //6位随机数
        String num3=(int)(Math.random()*1000000)+"";           //6位随机数
        String orderid=num1+num2+new Date().getTime()+num3;          //当前时间戳
        System.out.println(orderid);
    }
}
