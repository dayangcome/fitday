package com.chengxi.fitday.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chengxi.fitday.common.R;
import com.chengxi.fitday.entity.*;
import com.chengxi.fitday.service.IFoodService;
import com.chengxi.fitday.service.IPlanformService;
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

    @Autowired
    private IFoodService foodService;

    @Autowired
    private IPlanformService planformService;

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

    //查询所有运动计划表信息
    @GetMapping("all2")
    public R<List<Planform>> getAll2(){
        List <Planform> arr=planformService.list();
        return R.success(arr);
    }

    //查询运动计划
    @GetMapping("plan/{uid}/{mydate}")
    public R<List<SportsPlan>> getplan(@PathVariable Long uid,@PathVariable int mydate){
        LambdaQueryWrapper<SportsPlan> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(SportsPlan::getUserid,uid);
        if(mydate==0){
            queryWrapper.ge(SportsPlan::getDate,LocalDateTime.of(LocalDate.now(), LocalTime.MIN));
            queryWrapper.le(SportsPlan::getDate,LocalDateTime.now());
        }else if(mydate<0){
            mydate=-mydate;
            LocalDate theday=LocalDate.now().minusDays(mydate);
            queryWrapper.ge(SportsPlan::getDate,LocalDateTime.of(theday, LocalTime.MIN));
            queryWrapper.le(SportsPlan::getDate,LocalDateTime.of(theday, LocalTime.MAX));
        }else {
            LocalDate theday=LocalDate.now().plusDays(mydate);
            queryWrapper.ge(SportsPlan::getDate,LocalDateTime.of(theday, LocalTime.MIN));
            queryWrapper.le(SportsPlan::getDate,LocalDateTime.of(theday, LocalTime.MAX));
        }
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
    public R<String> delplan(@PathVariable Long id){
        sportsPlanService.removeById(id);     //删除运动计划信息
        return R.success("成功删除");
    }

    //完成某运动计划
    @GetMapping("/finplan/{id}")
    public R<String> finplan(@PathVariable Long id){
        SportsPlan sportsPlan=sportsPlanService.getById(id);
        sportsPlan.setIsDelect(1);
        sportsPlanService.updateById(sportsPlan);
        return R.success("计划已完成");
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

    //删除运动信息（员工后台）
    @GetMapping("/delsport/{id}")
    public R<String> delsport(@PathVariable Long id){
        sportService.removeById(id);        //删除运动信息
        return R.success("成功删除");
    }

    //修改运动（员工后台）
    @PostMapping("/changesport")
    public R<String> changesport(@RequestBody Sport sport){
        Sport sport1=sportService.getById(sport.getSportId());
        sport1.setSportName(sport.getSportName());
        sport1.setSportCategory(sport.getSportCategory());
        sport1.setPicture(sport.getPicture());
        sport1.setIntensity(sport.getIntensity());
        sport1.setLinkfoodId(sport.getLinkfoodId());
        sportService.updateById(sport1);
        return R.success("更改成功");
    }

    //通过运动计划得到消耗的热量
    @PostMapping("/getheat")
    public R<Double> getheat(@RequestBody List<SportsPlan> plans){
        double sumheat=0;
        for(SportsPlan sp:plans){
            if(sp.getIsDelect()==1){
                String content=sp.getContent();
                int shour=Integer.parseInt(content.substring(0,2));
                int smin=Integer.parseInt(content.substring(3,5));
                int ehour=Integer.parseInt(content.substring(8,10));
                int emin=Integer.parseInt(content.substring(11,13));
                int sportime=ehour*60+emin-shour*60-smin;
                if(sportime<0){
                    sportime=0;
                }

                String name=content.substring(16);
                LambdaQueryWrapper<Sport> queryWrapper=new LambdaQueryWrapper<>();
                queryWrapper.eq(Sport::getSportName,name);   //查询运动
                Sport sport=sportService.getOne(queryWrapper);

                double intense=0;   //运动强度
                if (sport==null){
                    intense=0.05;   //自定义的运动强度暂时按0.05算
                }else {
                    intense=sport.getIntensity();
                }
                sumheat+=intense*sportime;
            }
        }
        return R.success(sumheat);
    }

    //获取推荐食物
    @GetMapping("/getrecom/{sport}")
    public R<String> getrecom(@PathVariable String sport){
        System.out.println(sport);
        LambdaQueryWrapper<Sport> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Sport::getSportName,sport.trim());
        Sport sport1=sportService.getOne(queryWrapper);
        if(sport1==null){
            return R.error("没找到1");
        }
        if(sport1.getLinkfoodId()==null){
            return R.error("没找到2");
        }
        Food food=foodService.getById(sport1.getLinkfoodId());
        if(food==null){
            return R.error("没找到3");
        }
        return R.success(food.getFoodName());
    }
}

