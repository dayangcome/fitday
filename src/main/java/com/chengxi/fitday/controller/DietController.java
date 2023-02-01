package com.chengxi.fitday.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chengxi.fitday.common.R;
import com.chengxi.fitday.entity.Diet;
import com.chengxi.fitday.entity.Food;
import com.chengxi.fitday.service.IDietService;
import com.chengxi.fitday.service.IFoodService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 成溪科技公司开发
 * @since 2023-02-01
 */
@RestController
@RequestMapping("/diet")
public class DietController {
    @Autowired
    private IDietService dietService;

    //食物信息分页查询
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name){
        Page pageinfo=new Page<>(page,pageSize);
        LambdaQueryWrapper<Diet> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(name),Diet::getDietTitle,name);   //模糊查询食物名称
        dietService.page(pageinfo,queryWrapper);
        return R.success(pageinfo);
    }
}

