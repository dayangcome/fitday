package com.chengxi.fitday.service.impl;

import com.chengxi.fitday.entity.Food;
import com.chengxi.fitday.mapper.FoodMapper;
import com.chengxi.fitday.service.IFoodService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 */
@Service
public class FoodServiceImpl extends ServiceImpl<FoodMapper, Food> implements IFoodService {

}
