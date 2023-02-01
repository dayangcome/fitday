package com.chengxi.fitday.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chengxi.fitday.common.R;
import com.chengxi.fitday.dto.Empregisterdto;
import com.chengxi.fitday.entity.Employee;
import com.chengxi.fitday.entity.Food;
import com.chengxi.fitday.entity.User;
import com.chengxi.fitday.service.IFoodService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 成溪科技公司开发
 * @since 2023-01-28
 */
@RestController
@RequestMapping("/food")
public class FoodController {
    @Autowired
    private IFoodService foodService;

    //食物信息分页查询
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name){
        Page pageinfo=new Page<>(page,pageSize);
        LambdaQueryWrapper<Food> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(name),Food::getFoodName,name);   //模糊查询食物名称
        foodService.page(pageinfo,queryWrapper);
        return R.success(pageinfo);
    }

    //添加食物（员工后台）
    @PostMapping("/addfood")
    public R<String> addfood(@RequestBody Food food){
        Food food1=new Food();
        food1.setFoodName(food.getFoodName());
        food1.setHeat(food.getHeat());
        food1.setFat(food.getFat());
        food1.setFoodCategory(food.getFoodCategory());
        food1.setPicture(food.getPicture());
        food1.setSuitablePerson(food.getSuitablePerson());
        food1.setDietTaboo(food.getDietTaboo());
        food1.setCreateTime(LocalDateTime.now());
        food1.setUpdataeTime(LocalDateTime.now());
        food1.setProtein(food.getProtein());
        food1.setCarbohydrate(food.getCarbohydrate());
        foodService.save(food1);
        return R.success("注册成功");
    }

    //删除食物信息（员工后台）
    @GetMapping("/delfood/{id}")
    public R<String> delfood(@PathVariable Long id){
        foodService.removeById(id);        //删除食物信息
        return R.success("成功删除");
    }

    //修改食物（员工后台）
    @PostMapping("/changefood")
    public R<String> changefood(@RequestBody Food food){
        Food food1=foodService.getById(food.getFoodId());
        food1.setFoodName(food.getFoodName());
        food1.setHeat(food.getHeat());
        food1.setFat(food.getFat());
        food1.setFoodCategory(food.getFoodCategory());
        food1.setPicture(food.getPicture());
        food1.setSuitablePerson(food.getSuitablePerson());
        food1.setDietTaboo(food.getDietTaboo());
        food1.setCreateTime(LocalDateTime.now());
        food1.setUpdataeTime(LocalDateTime.now());
        food1.setProtein(food.getProtein());
        food1.setCarbohydrate(food.getCarbohydrate());
        foodService.updateById(food1);
        return R.success("更改成功");
    }
}

