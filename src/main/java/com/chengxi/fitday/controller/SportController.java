package com.chengxi.fitday.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chengxi.fitday.common.R;
import com.chengxi.fitday.entity.Food;
import com.chengxi.fitday.entity.Sport;
import com.chengxi.fitday.service.IFoodService;
import com.chengxi.fitday.service.ISportService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
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
}

