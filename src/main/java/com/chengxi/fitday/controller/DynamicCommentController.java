package com.chengxi.fitday.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.chengxi.fitday.common.R;
import com.chengxi.fitday.entity.Dynamic;
import com.chengxi.fitday.entity.DynamicComment;
import com.chengxi.fitday.entity.User;
import com.chengxi.fitday.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 成溪科技公司开发
 * @since 2023-04-12
 */
@RestController
@RequestMapping("/dynamicComment")
public class DynamicCommentController {

    @Autowired
    private IDynamicCommentService dynamicCommentService;

    @Autowired
    private IDynamicService dynamicService;

    @Autowired
    private IUserService userService;

    //查询动态评论
    @GetMapping("/getcomments/{dyId}")
    public R<List<DynamicComment>> getcomments(@PathVariable Long dyId){
        LambdaQueryWrapper<DynamicComment> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DynamicComment::getDynamicId,dyId);
        List <DynamicComment> arr=dynamicCommentService.list(queryWrapper);
        return R.success(arr);
    }

    //用户发表评论
    @PostMapping("/addcomments/{uid}/{dyId}/{content}")
    public R<String> addcomments(@PathVariable Long uid,@PathVariable Long dyId,@PathVariable String content){
        DynamicComment comments=new DynamicComment();
        comments.setCreateTime(LocalDateTime.now());
        comments.setDynamicId(dyId);
        User user=userService.getById(uid);
        if(user==null){
            return R.error("用户信息未找到！");
        }
        comments.setProductPicture(user.getAvatar());   //用户头像
        comments.setLikes(0);
        comments.setCommentText(content);
        comments.setUserId(uid);
        comments.setUsername(user.getUsername());
        dynamicCommentService.save(comments);
        Dynamic dynamic=dynamicService.getById(dyId);
        if(dynamic!=null){
            dynamic.setComments(dynamic.getComments()+1);       //评论数+1
        }else {
            return R.error("动态信息未找到！");
        }
        dynamicService.updateById(dynamic);

        user.setExp(user.getExp()+50);     //发表评论加50经验
        user.setLevel(user.getExp()/1000+1);    //检查用户是否升级
        userService.updateById(user);           //更新用户信息

        return R.success("发表成功");
    }
}

