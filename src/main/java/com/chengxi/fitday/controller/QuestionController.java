package com.chengxi.fitday.controller;


import com.chengxi.fitday.common.R;
import com.chengxi.fitday.entity.Question;
import com.chengxi.fitday.entity.Sport;
import com.chengxi.fitday.service.IQuestionService;
import com.chengxi.fitday.service.ISportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private IQuestionService questionService;

    //查询问题信息
    @GetMapping("all")
    public R<List<Question>> getAll(){
        List <Question> arr=questionService.list();
        return R.success(arr);
    }

}

