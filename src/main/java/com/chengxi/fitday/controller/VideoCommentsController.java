package com.chengxi.fitday.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.chengxi.fitday.common.R;
import com.chengxi.fitday.entity.Sport;
import com.chengxi.fitday.entity.User;
import com.chengxi.fitday.entity.Uservideo;
import com.chengxi.fitday.entity.VideoComments;
import com.chengxi.fitday.service.ISportService;
import com.chengxi.fitday.service.IUserService;
import com.chengxi.fitday.service.IUservideoService;
import com.chengxi.fitday.service.IVideoCommentsService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("/videoComments")
public class VideoCommentsController {
    @Autowired
    private IVideoCommentsService videoCommentsService;

    @Autowired
    private IUservideoService uservideoService;

    @Autowired
    private IUserService userService;

    //查询视频评论
    @GetMapping("/getcomments/{videoId}")
    public R<List<VideoComments>> getComments(@PathVariable Long videoId){
        LambdaQueryWrapper<VideoComments> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(VideoComments::getVideoid,videoId);
        List <VideoComments> arr=videoCommentsService.list(queryWrapper);
        return R.success(arr);
    }

    //查询视频评论
    @GetMapping("/getcommentsd/{videoId}")
    public R<List<VideoComments>> getCommentsd(@PathVariable Long videoId){
        LambdaQueryWrapper<VideoComments> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(VideoComments::getVideoid,videoId);
        queryWrapper.orderByDesc(VideoComments::getCommentdate);
        List <VideoComments> arr=videoCommentsService.list(queryWrapper);
        return R.success(arr);
    }

    //用户发表评论
    @PostMapping("/addcomments/{uid}/{videoId}/{content}")
    public R<VideoComments> addComments(@PathVariable Long uid,@PathVariable Long videoId,@PathVariable String content){
        VideoComments comments=new VideoComments();
        comments.setCommentdate(LocalDateTime.now());
        comments.setVideoid(videoId);
        User user=userService.getById(uid);
        if(user==null){
            return R.error("用户信息未找到！");
        }
        comments.setPrepare(user.getUsername());
        comments.setAvatar(user.getAvatar());
        comments.setLikecount(0l);
        comments.setContent(content);
        comments.setUserid(uid);
        videoCommentsService.save(comments);

        user.setExp(user.getExp()+50);     //发表评论加50经验
        user.setLevel(user.getExp()/1000+1);    //检查用户是否升级
        userService.updateById(user);           //更新用户信息

        return R.success(comments);
    }

}

