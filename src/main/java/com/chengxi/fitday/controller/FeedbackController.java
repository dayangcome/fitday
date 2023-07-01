package com.chengxi.fitday.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chengxi.fitday.common.R;
import com.chengxi.fitday.dto.Explaindto;
import com.chengxi.fitday.entity.Feedback;
import com.chengxi.fitday.entity.Freeze;
import com.chengxi.fitday.entity.User;
import com.chengxi.fitday.service.IFeedbackService;
import com.chengxi.fitday.service.IUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {

    @Autowired
    private IFeedbackService feedbackService;

    @Autowired
    private IUserService userService;

    @PostMapping("/nologinfeedback")
    public R<String> noLoginFeedback(@RequestBody Feedback feedback){
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

    //用户申诉被封禁的账号
    @PostMapping("/sendexplain")
    public R<String> sendExplain(@RequestBody Explaindto explaindto){
        User user1=null;
        if(explaindto.getAccount()!=""){
            LambdaQueryWrapper<User> queryWrapper=new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getAccount,explaindto.getAccount());
            user1= userService.getOne(queryWrapper);   //通过账号查找获得申诉用户
        }
        if(user1==null){
            LambdaQueryWrapper<User> queryWrapper=new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getPhone,explaindto.getPhone());
            user1= userService.getOne(queryWrapper);   //通过手机号查找获得申诉用户
        }
        Feedback feedback1=new Feedback();
        feedback1.setUseruid(user1.getUid());
        feedback1.setName(user1.getUsername());
        if(user1.getPhone()!=null){
            feedback1.setPhone(user1.getPhone());
        }
        String message="账号封禁原因：【"+explaindto.getReason()+"】   申诉信息：";
        message+=explaindto.getExplain();
        feedback1.setMessage(message);
        feedback1.setType(2);   //2:账号封号申诉
        feedback1.setStatus(0); //0:未接受状态
        feedback1.setCreateTime(LocalDateTime.now());
        feedbackService.save(feedback1);
        return R.success("反馈成功！");
    }

    //更改反馈状态（员工后台）
    @GetMapping("/change/{id}")
    public R<String> change(@PathVariable Long id){
        Feedback feedback=feedbackService.getById(id);
        if(feedback==null){
            return R.error("该反馈不存在！");
        }
        if(feedback.getStatus()==2){
            return R.error("该反馈不能再继续更改状态！");
        }
        feedback.setStatus(feedback.getStatus()+1);
        feedbackService.updateById(feedback);

        return R.success("成功更改反馈状态！");
    }

    //删除反馈信息（员工后台）
    @GetMapping("/delfeed/{id}")
    public R<String> delFeed(@PathVariable Long id){
        feedbackService.removeById(id);        //删除反馈信息
        return R.success("成功删除");
    }

}

