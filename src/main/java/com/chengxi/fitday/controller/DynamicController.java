package com.chengxi.fitday.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chengxi.fitday.common.R;
import com.chengxi.fitday.entity.*;
import com.chengxi.fitday.service.IDynamicService;
import com.chengxi.fitday.service.IUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/dynamic")
public class DynamicController {

    @Autowired
    private IDynamicService dynamicService;

    @Autowired
    private IUserService userService;

    //发布动态
    @PostMapping("/add")
    public R<String> addDynamic(@RequestBody Dynamic dynamic){
        Dynamic dynamic1=new Dynamic();
        dynamic1.setUserId(dynamic.getUserId());
        dynamic1.setPicture(dynamic.getPicture());
        dynamic1.setContent(dynamic.getContent());
        dynamic1.setUploadtime(LocalDateTime.now());
        dynamic1.setModifytime(LocalDateTime.now());
        dynamic1.setTitle(dynamic.getTitle());
        dynamic1.setCategory(dynamic.getCategory());
        dynamicService.save(dynamic1);

        User user=userService.getById(dynamic.getUserId());
        if(user==null){
            return R.error("没有找到用户");
        }
        user.setExp(user.getExp()+300);     //发一篇动态加300经验
        user.setLevel(user.getExp()/1000+1);    //检查用户是否升级
        userService.updateById(user);           //更新用户信息

        return R.success("发布成功");
    }

    //查询部分动态信息
    @GetMapping("/{uid}")
    public R<List<Dynamic>> getDynamic(@PathVariable Long uid){
        LambdaQueryWrapper<Dynamic> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Dynamic::getUserId,uid);
        List <Dynamic> arr=dynamicService.list(queryWrapper);
        return R.success(arr);
    }

    //查询单个动态信息
    @GetMapping("/one/{theid}")
    public R<Dynamic> getOneVideo(@PathVariable Long theid){
        Dynamic dynamic=dynamicService.getById(theid);
        if(dynamic==null){
            return R.error("未找到该动态！");
        }
        dynamic.setLikes(dynamic.getLikes()+1);       //动态阅读数+1
        dynamicService.updateById(dynamic);
        return R.success(dynamic);
    }

    //动态信息分页查询
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, Long uid){
        Page pageinfo=new Page<>(page,pageSize);
        LambdaQueryWrapper<Dynamic> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Dynamic::getUserId,uid);
        queryWrapper.orderByDesc(Dynamic::getUploadtime);  //按照发布时间排序
        dynamicService.page(pageinfo,queryWrapper);
        return R.success(pageinfo);
    }

    //查询所有动态信息
    @GetMapping("all")
    public R<List<Dynamic>> getAllDynamic(){
        LambdaQueryWrapper<Dynamic> queryWrapper=new LambdaQueryWrapper<>();
        List <Dynamic> arr=dynamicService.list(queryWrapper);
        return R.success(arr);
    }

    //删除动态
    @GetMapping("/del/{id}")
    public R<String> delDynamic(@PathVariable Long id){
       try{
           dynamicService.removeById(id);        //把动态删除
           return R.success("成功解封");
       }catch (Exception e){
           return R.error(e.getMessage());
       }
    }

}

