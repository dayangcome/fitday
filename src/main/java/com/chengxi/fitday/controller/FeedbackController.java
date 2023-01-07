package com.chengxi.fitday.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chengxi.fitday.common.R;
import com.chengxi.fitday.entity.Feedback;
import com.chengxi.fitday.entity.User;
import com.chengxi.fitday.service.IFeedbackService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 成溪科技公司开发
 * @since 2023-01-07
 */
@RestController
@RequestMapping("/feedback")
public class FeedbackController {

    @Autowired
    private IFeedbackService feedbackService;

    @PostMapping("/nologinfeedback")
    public R<String> nologinfeedback(@RequestBody Feedback feedback){
        Feedback feedback1=new Feedback();
        feedback1.setName(feedback.getName());
        feedback1.setEmail(feedback.getEmail());
        feedback1.setPhone(feedback.getPhone());
        feedback1.setMessage(feedback.getMessage());
        feedback1.setType(0);   //0:普通用户反馈
        feedback1.setStatus(0); //0:未接受状态
        feedback1.setCreateTime(LocalDateTime.now());
        feedbackService.save(feedback1);
        return R.success("反馈成功！");
    }

    //反馈信息分页查询（员工后台）
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name){
        Page pageinfo=new Page<>(page,pageSize);
        LambdaQueryWrapper<Feedback> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotEmpty(name),Feedback::getName,name);   //精确查询昵称
        queryWrapper.orderByDesc(Feedback::getCreateTime);  //按照创建时间排序
        feedbackService.page(pageinfo,queryWrapper);
        return R.success(pageinfo);
    }
}

