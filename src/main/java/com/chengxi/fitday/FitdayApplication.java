package com.chengxi.fitday;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class FitdayApplication {

    public static void main(String[] args) {
        SpringApplication.run(FitdayApplication.class, args);
    }

}
