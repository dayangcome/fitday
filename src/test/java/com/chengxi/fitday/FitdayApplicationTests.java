package com.chengxi.fitday;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;

@SpringBootTest
class FitdayApplicationTests {

    @Test
    void contextLoads() {
        String ss="abcdefg1";
        System.out.println(LocalDateTime.of(LocalDate.now(), LocalTime.MIN));
    }

}
