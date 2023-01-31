package com.chengxi.fitday.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chengxi.fitday.common.R;
import com.chengxi.fitday.entity.Food;
import com.chengxi.fitday.entity.Sport;
import com.chengxi.fitday.entity.SportsPlan;
import com.chengxi.fitday.service.IFoodService;
import com.chengxi.fitday.service.ISportService;
import com.chengxi.fitday.service.ISportsPlanService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 成溪科技公司开发
 * @since 2023-01-28
 */
@RestController
@RequestMapping("/sport")
public class SportController {

    @Autowired
    private ISportService sportService;

    @Autowired
    private ISportsPlanService sportsPlanService;

    //运动信息分页查询
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name){
        Page pageinfo=new Page<>(page,pageSize);
        LambdaQueryWrapper<Sport> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(name),Sport::getSportName,name);   //模糊查询运动名称
        sportService.page(pageinfo,queryWrapper);
        return R.success(pageinfo);
    }

    //查询所有运动信息
    @GetMapping("all")
    public R<List<Sport>> getAll(){
        List <Sport> arr=sportService.list();
        return R.success(arr);
    }

    //查询运动计划
    @GetMapping("plan/{uid}/{mydate}")
    public R<List<SportsPlan>> getplan(@PathVariable Long uid,@PathVariable int mydate){

        LambdaQueryWrapper<SportsPlan> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(SportsPlan::getUserid,uid);
        queryWrapper.ge(SportsPlan::getDate,LocalDateTime.of(LocalDate.now(), LocalTime.MIN));
        queryWrapper.le(SportsPlan::getDate,LocalDateTime.now());
        List<SportsPlan> arr=sportsPlanService.list(queryWrapper);
        return R.success(arr);
    }

    //添加运动计划
    @GetMapping("addplan/{uid}/{context}")
    public R<String> addplan(@PathVariable Long uid,@PathVariable String context){
        SportsPlan sportsPlan=new SportsPlan();
        sportsPlan.setContent(context);
        sportsPlan.setUserid(uid);
        sportsPlan.setDate(LocalDateTime.now());
        sportsPlanService.save(sportsPlan);
        return R.success("添加成功");
    }

    //删除某运动计划
    @GetMapping("/delplan/{id}")
    public R<String> delemp(@PathVariable Long id){
        sportsPlanService.removeById(id);     //删除运动计划信息
        return R.success("成功删除");
    }

    //添加运动（员工后台）
    @PostMapping("/addsport")
    public R<String> addsport(@RequestBody Sport sport){
        Sport sport1=new Sport();
        sport1.setSportName(sport.getSportName());
        sport1.setSportCategory(sport.getSportCategory());
        sport1.setPicture(sport.getPicture());
        sport1.setIntensity(sport.getIntensity());
        sport1.setLinkfoodId(sport.getLinkfoodId());
        sportService.save(sport1);
        return R.success("注册成功");
    }
}

