package com.chengxi.fitday;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

@SpringBootTest
class FitdayApplicationTests {

    @Test
    void contextLoads() {
        String ss="abcdefg1";
        System.out.println(DigestUtils.md5DigestAsHex(ss.getBytes()));
    }

}
