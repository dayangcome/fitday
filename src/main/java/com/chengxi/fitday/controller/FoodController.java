package com.chengxi.fitday.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chengxi.fitday.common.R;
import com.chengxi.fitday.dto.Empregisterdto;
import com.chengxi.fitday.dto.Fooddto;
import com.chengxi.fitday.dto.Foodplandto;
import com.chengxi.fitday.entity.*;
import com.chengxi.fitday.service.IDietPlanService;
import com.chengxi.fitday.service.IFoodService;
import com.chengxi.fitday.service.IUserService;
import com.chengxi.fitday.service.IUserinfoService;
import com.chengxi.fitday.utils.FoodRecommendation;
import com.chengxi.fitday.utils.SportRecommendation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/food")
public class FoodController {
    @Autowired
    private IFoodService foodService;

    @Autowired
    private IDietPlanService dietPlanService;

    @Autowired
    private IUserService userService;

    @Resource
    private IUserinfoService userinfoService;

    //食物信息分页查询
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name){
        Page pageinfo=new Page<>(page,pageSize);
        LambdaQueryWrapper<Food> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(name),Food::getFoodName,name);   //模糊查询食物名称

//        FoodRecommendation foodRecommendation = new FoodRecommendation();
//        List<String> recommendedRecipes = foodRecommendation.getRecommendation(userinfoService.getById(0), 5).getRecommendedRecipes();
//
//        for (String recipe : recommendedRecipes) {
//            System.out.println(recipe);
//        }

        foodService.page(pageinfo,queryWrapper);
        return R.success(pageinfo);
    }

    //添加食物（员工后台）
    @PostMapping("/addfood")
    public R<String> addFood(@RequestBody Food food){
        Food food1=new Food();
        food1.setFoodName(food.getFoodName());
        food1.setHeat(food.getHeat());
        food1.setFat(food.getFat());
        food1.setFoodCategory(food.getFoodCategory());
        food1.setPicture(food.getPicture());
        food1.setSuitablePerson(food.getSuitablePerson());
        food1.setDietTaboo(food.getDietTaboo());
        food1.setCreateTime(LocalDateTime.now());       //设置添加时间
        food1.setUpdataeTime(LocalDateTime.now());
        food1.setProtein(food.getProtein());
        food1.setCarbohydrate(food.getCarbohydrate());
        foodService.save(food1);
        return R.success("添加成功");
    }

    //删除食物信息（员工后台）
    @GetMapping("/delfood/{id}")
    public R<String> delFood(@PathVariable Long id){
        foodService.removeById(id);        //删除食物信息
        return R.success("成功删除");
    }

    //修改食物（员工后台）
    @PostMapping("/changefood")
    public R<String> changeFood(@RequestBody Food food){
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

    //查询所有食物信息（根据用户信息协同过滤）
    @GetMapping("recom/{uid}")
    public R<List<Food>> Recommendation(@PathVariable Long uid){
        List <Food> arr=foodService.list();
        FoodRecommendation foodRecommendation = new FoodRecommendation();  //实现协同过滤,开始协同过滤算法
        List<String> recommendedRecipes = foodRecommendation.getRecommendation(userinfoService.getById(uid), arr.size()).getRecommendedRecipes();
        LambdaQueryWrapper<Food> queryWrapper=new LambdaQueryWrapper<>();
        List<Food> farr=new ArrayList<>();
        for(int i=0;i<recommendedRecipes.size();i++){
            queryWrapper.eq(Food::getFoodName,recommendedRecipes.get(i));
            farr.add(foodService.getOne(queryWrapper));
        }
        return R.success(farr);
    }

    //查询所有食物信息
    @GetMapping("/getall")
    public R<List<Food>> getAll(){
        List <Food> arr=foodService.list();
        return R.success(arr);
    }

    //添加早餐计划信息
    @PostMapping("/breakfast")
    public R<String> myBreakFast(@RequestBody Foodplandto foodplandto){
        LambdaQueryWrapper<DietPlan> queryWrapper=new LambdaQueryWrapper<>();
        System.out.println("???"+foodplandto.getUserid());
        queryWrapper.eq(DietPlan::getUserid,foodplandto.getUserid());
        queryWrapper.ge(DietPlan::getDate,LocalDateTime.of(LocalDate.now(), LocalTime.MIN));
        queryWrapper.le(DietPlan::getDate,LocalDateTime.now());
        DietPlan dietPlan=dietPlanService.getOne(queryWrapper);
        if(dietPlan==null){
            DietPlan dietPlan1=new DietPlan();
            List<Fooddto> foods=foodplandto.getFoods();
            String breakfast="";
            for (Fooddto myfood:foods){         //拼接早餐计划字符串
                breakfast+=myfood.getFood();
                breakfast+="  ";
                breakfast+=myfood.getIntake();
                breakfast+="g   \n";
            }
            System.out.println(breakfast);
            dietPlan1.setDate(LocalDateTime.now());
            dietPlan1.setUserid(foodplandto.getUserid());
            dietPlan1.setBreakfast(breakfast);
            dietPlanService.save(dietPlan1);
        }else {
            List<Fooddto> foods=foodplandto.getFoods();
            String breakfast="";
            for (Fooddto myfood:foods){
                breakfast+=myfood.getFood();
                breakfast+="  ";
                breakfast+=myfood.getIntake();
                breakfast+="g   \n";
            }
            System.out.println(breakfast);
            dietPlan.setDate(LocalDateTime.now());
            dietPlan.setUserid(foodplandto.getUserid());
            dietPlan.setBreakfast(breakfast);
            dietPlanService.updateById(dietPlan);
        }

        User user=userService.getById(foodplandto.getUserid());
        if(user==null){
            return R.error("没有找到用户");
        }
        user.setExp(user.getExp()+50);     //制定计划加50经验
        user.setLevel(user.getExp()/1000+1);    //检查用户是否升级
        userService.updateById(user);           //更新用户信息

        return R.success("成功");
    }

    //添加午餐计划信息
    @PostMapping("/lunch")
    public R<String> mylunch(@RequestBody Foodplandto foodplandto){
        LambdaQueryWrapper<DietPlan> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DietPlan::getUserid,foodplandto.getUserid());
        queryWrapper.ge(DietPlan::getDate,LocalDateTime.of(LocalDate.now(), LocalTime.MIN));
        queryWrapper.le(DietPlan::getDate,LocalDateTime.now());
        DietPlan dietPlan=dietPlanService.getOne(queryWrapper);
        if(dietPlan==null){
            DietPlan dietPlan1=new DietPlan();
            List<Fooddto> foods=foodplandto.getFoods();
            String lunch="";
            for (Fooddto myfood:foods){
                lunch+=myfood.getFood();
                lunch+="  ";
                lunch+=myfood.getIntake();
                lunch+="g   \n";
            }
            System.out.println(lunch);
            dietPlan1.setDate(LocalDateTime.now());
            dietPlan1.setUserid(foodplandto.getUserid());
            dietPlan1.setBreakfast(lunch);
            dietPlanService.save(dietPlan1);
        }else {
            List<Fooddto> foods=foodplandto.getFoods();
            String lunch="";
            for (Fooddto myfood:foods){
                lunch+=myfood.getFood();
                lunch+="  ";
                lunch+=myfood.getIntake();
                lunch+="g   \n";
            }
            System.out.println(lunch);
            dietPlan.setDate(LocalDateTime.now());
            dietPlan.setUserid(foodplandto.getUserid());
            dietPlan.setLunch(lunch);
            dietPlanService.updateById(dietPlan);
        }

        User user=userService.getById(foodplandto.getUserid());
        if(user==null){
            return R.error("没有找到用户");
        }
        user.setExp(user.getExp()+50);     //制定计划加50经验
        user.setLevel(user.getExp()/1000+1);    //检查用户是否升级
        userService.updateById(user);           //更新用户信息

        return R.success("成功");
    }

    //添加晚餐计划信息
    @PostMapping("/dinner")
    public R<String> mydinner(@RequestBody Foodplandto foodplandto){
        LambdaQueryWrapper<DietPlan> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DietPlan::getUserid,foodplandto.getUserid());
        queryWrapper.ge(DietPlan::getDate,LocalDateTime.of(LocalDate.now(), LocalTime.MIN));
        queryWrapper.le(DietPlan::getDate,LocalDateTime.now());
        DietPlan dietPlan=dietPlanService.getOne(queryWrapper);
        if(dietPlan==null){
            DietPlan dietPlan1=new DietPlan();
            List<Fooddto> foods=foodplandto.getFoods();
            String dinner="";
            for (Fooddto myfood:foods){
                dinner+=myfood.getFood();
                dinner+="  ";
                dinner+=myfood.getIntake();
                dinner+="g   \n";
            }
            System.out.println(dinner);
            dietPlan1.setDate(LocalDateTime.now());
            dietPlan1.setUserid(foodplandto.getUserid());
            dietPlan1.setBreakfast(dinner);
            dietPlanService.save(dietPlan1);
        }else {
            List<Fooddto> foods=foodplandto.getFoods();
            String dinner="";
            for (Fooddto myfood:foods){
                dinner+=myfood.getFood();
                dinner+="  ";
                dinner+=myfood.getIntake();
                dinner+="g   \n";
            }
            System.out.println(dinner);
            dietPlan.setDate(LocalDateTime.now());
            dietPlan.setUserid(foodplandto.getUserid());
            dietPlan.setDinner(dinner);
            dietPlanService.updateById(dietPlan);
        }

        User user=userService.getById(foodplandto.getUserid());
        if(user==null){
            return R.error("没有找到用户");
        }
        user.setExp(user.getExp()+50);     //制定计划加50经验
        user.setLevel(user.getExp()/1000+1);    //检查用户是否升级
        userService.updateById(user);           //更新用户信息

        return R.success("成功");
    }

    //添加加餐计划信息
    @PostMapping("/adddiet")
    public R<String> myadddiet(@RequestBody Foodplandto foodplandto){
        LambdaQueryWrapper<DietPlan> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DietPlan::getUserid,foodplandto.getUserid());
        queryWrapper.ge(DietPlan::getDate,LocalDateTime.of(LocalDate.now(), LocalTime.MIN));
        queryWrapper.le(DietPlan::getDate,LocalDateTime.now());
        DietPlan dietPlan=dietPlanService.getOne(queryWrapper);
        if(dietPlan==null){
            DietPlan dietPlan1=new DietPlan();
            List<Fooddto> foods=foodplandto.getFoods();
            String adddiet="";
            for (Fooddto myfood:foods){
                adddiet+=myfood.getFood();
                adddiet+="  ";
                adddiet+=myfood.getIntake();
                adddiet+="g   \n";
            }
            System.out.println(adddiet);
            dietPlan1.setDate(LocalDateTime.now());
            dietPlan1.setUserid(foodplandto.getUserid());
            dietPlan1.setBreakfast(adddiet);
            dietPlanService.save(dietPlan1);
        }else {
            List<Fooddto> foods=foodplandto.getFoods();
            String adddiet="";
            for (Fooddto myfood:foods){
                adddiet+=myfood.getFood();
                adddiet+="  ";
                adddiet+=myfood.getIntake();
                adddiet+="g   \n";
            }
            System.out.println(adddiet);
            dietPlan.setDate(LocalDateTime.now());
            dietPlan.setUserid(foodplandto.getUserid());
            dietPlan.setAdddiet(adddiet);
            dietPlanService.updateById(dietPlan);
        }

        User user=userService.getById(foodplandto.getUserid());
        if(user==null){
            return R.error("没有找到用户");
        }
        user.setExp(user.getExp()+50);     //制定计划加50经验
        user.setLevel(user.getExp()/1000+1);    //检查用户是否升级
        userService.updateById(user);           //更新用户信息

        return R.success("成功");
    }

    //查询饮食计划
    @GetMapping("geteatplan/{uid}/{value2}")
    public R<DietPlan> geteatplan(@PathVariable Long uid,@PathVariable String value2){
        LocalDate res = LocalDate.parse(value2, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        LambdaQueryWrapper<DietPlan> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DietPlan::getUserid,uid);
        queryWrapper.ge(DietPlan::getDate,LocalDateTime.of(res, LocalTime.MIN));
        queryWrapper.le(DietPlan::getDate,LocalDateTime.of(res, LocalTime.MAX));
        DietPlan dietPlan=dietPlanService.getOne(queryWrapper);
        if (dietPlan==null){
            return R.error("没有设置饮食计划");
        }
        return R.success(dietPlan);
    }

    //通过食物获得的热量
    @PostMapping("/getmyheat")
    public R<Double> getmyheat(@RequestBody List<Fooddto> plans){
        double sumheat=0;
        for(Fooddto fooddto:plans){
            String name=fooddto.getFood();
            LambdaQueryWrapper<Food> queryWrapper=new LambdaQueryWrapper<>();
            queryWrapper.eq(Food::getFoodName,name);   //查询食物热量
            Food food=foodService.getOne(queryWrapper);
            double heat=0;
            try {
                if(food==null){
                    heat=100;       //查不到则默认值为100
                }else{
                    System.out.println("heat的值为"+heat);
                    heat=Double.parseDouble(food.getHeat());
                }
                sumheat += Double.parseDouble(fooddto.getIntake())*heat/100;
            }catch (Exception e){
                return R.error("请检查摄入量是否输入的是数字！");
            }
        }
        return R.success(sumheat);
    }
}

