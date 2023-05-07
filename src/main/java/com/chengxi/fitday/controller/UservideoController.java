package com.chengxi.fitday.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chengxi.fitday.common.R;
import com.chengxi.fitday.dto.VideoinfoAdddto;
import com.chengxi.fitday.entity.User;
import com.chengxi.fitday.entity.Uservideo;
import com.chengxi.fitday.service.IUserService;
import com.chengxi.fitday.service.IUservideoService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/uservideo")
public class UservideoController {

    @Autowired
    private IUservideoService uservideoService;

    @Autowired
    private IUserService userService;

    //视频信息分页查询
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name){
        Page pageinfo=new Page<>(page,pageSize);
        LambdaQueryWrapper<Uservideo> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(name),Uservideo::getTitle,name);   //模糊查询视频标题
        uservideoService.page(pageinfo,queryWrapper);
        return R.success(pageinfo);
    }

    //查询所有视频信息
    @GetMapping("/all")
    public R<List<Uservideo>> getAll(){
        List <Uservideo> arr=uservideoService.list();
        return R.success(arr);
    }

    //查询单个视频信息
    @GetMapping("/one/{videoId}")
    public R<Uservideo> getonevideo(@PathVariable Long videoId){
        Uservideo uservideo=uservideoService.getById(videoId);
        if(uservideo==null){
            return R.error("未找到该视频！");
        }
        uservideo.setHits(uservideo.getHits()+1);       //视频播放量+1
        uservideoService.updateById(uservideo);
        return R.success(uservideo);
    }

    //查询所有视频更详细信息
    @GetMapping("/all2")
    public R<List<VideoinfoAdddto>> getAll2(){
        List <Uservideo> arr=uservideoService.list();
        List <VideoinfoAdddto> arr2=new ArrayList<>();
        for (Uservideo video:arr){
            User tempuser=userService.getById(video.getUserId());
            if(tempuser==null){
                return R.error("某些视频未找到作者");
            }
            VideoinfoAdddto videoinfoAdddto=new VideoinfoAdddto();
            videoinfoAdddto.setUservideo(video);
            videoinfoAdddto.setAuthorname(tempuser.getUsername());
            arr2.add(videoinfoAdddto);
        }
        return R.success(arr2);
    }


    //三连
    @GetMapping("thumb/{num}/{uid}/{videoId}")
    public R<Uservideo> thumb(@PathVariable Integer num,@PathVariable Long uid,@PathVariable Long videoId){
        Uservideo uservideo=uservideoService.getById(videoId);
        if(uservideo==null){
            return R.error("未找到该视频！");
        }
        if(num==1){
            uservideo.setLikes(uservideo.getLikes()+1);       //视频点赞量+1
        }else if(num==2){
            uservideo.setSaves(uservideo.getSaves()+1);
        }else if(num==3){
            uservideo.setShares(uservideo.getShares()+1);
        }
        uservideoService.updateById(uservideo);
        return R.success(uservideo);
    }
}

