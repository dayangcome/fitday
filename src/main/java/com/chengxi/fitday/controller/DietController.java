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
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


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

    //添加套餐（员工后台）
    @PostMapping("/adddiet")
    public R<String> addDiet(@RequestBody Diet diet){
        Diet diet1=new Diet();
        diet1.setDietTitle(diet.getDietTitle());
        diet1.setDietCategoryId(diet.getDietCategoryId());
        diet1.setCover(diet.getCover());
        diet1.setCookingMethod(diet.getCookingMethod());
        diet1.setDietHeat(diet.getDietHeat());
        diet1.setPrice(diet.getPrice());
        diet1.setSuitablePerson(diet.getSuitablePerson());
        diet1.setHits(0);
        diet1.setPraiseNum(0);
        dietService.save(diet1);
        return R.success("添加成功");
    }

    //删除套餐信息（员工后台）
    @GetMapping("/deldiet/{id}")
    public R<String> delFood(@PathVariable Long id){
        dietService.removeById(id);        //删除套餐信息
        return R.success("成功删除");
    }

    //修改套餐（员工后台）
    @PostMapping("/changediet")
    public R<String> changeDiet(@RequestBody Diet diet){
        Diet diet1 =dietService.getById(diet.getDietId());
        diet1.setDietTitle(diet.getDietTitle());
        diet1.setDietCategoryId(diet.getDietCategoryId());
        diet1.setCover(diet.getCover());
        diet1.setCookingMethod(diet.getCookingMethod());
        diet1.setDietHeat(diet.getDietHeat());
        diet1.setPrice(diet.getPrice());
        diet1.setSuitablePerson(diet.getSuitablePerson());
        diet1.setHits(0);
        diet1.setPraiseNum(0);
        dietService.updateById(diet1);
        return R.success("添加成功");
    }

    //查询所有食物信息
    @GetMapping("getall")
    public R<List<Diet>> getAll(){
        List <Diet> arr=dietService.list();
        return R.success(arr);
    }
}

