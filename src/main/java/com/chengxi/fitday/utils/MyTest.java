package com.chengxi.fitday.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

public class MyTest {
    // 测试运动协同过滤
    public static void main(String[] args) {
// 运动项目兴趣评分矩阵
        double[][] Amn = {
                {5, 4, 0, 0},
                {0, 0, 3, 4},
                {0, 0, 0, 5},
                {4, 5, 2, 0},
                {0, 3, 4, 0}
        };
// 运动项目相似矩阵
        double[][] Bmn = {
                {1, 0.2, 0.3, 0.4},
                {0.2, 1, 0.5, 0.1},
                {0.3, 0.5, 1, 0.6},
                {0.4, 0.1, 0.6, 1}
        };
        SportRecommendation sr = new SportRecommendation(Amn, Bmn);
        List<Integer> recommendationList = sr.getRecommendationList(0);
        System.out.println("推荐给用户0的运动项目列表：");
        System.out.println(recommendationList);
    }
}
