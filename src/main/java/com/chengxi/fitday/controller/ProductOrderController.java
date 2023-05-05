package com.chengxi.fitday.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chengxi.fitday.common.R;
import com.chengxi.fitday.entity.ProductOrder;
import com.chengxi.fitday.entity.Recharge;
import com.chengxi.fitday.service.IProductOrderService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/productOrder")
public class ProductOrderController {

    @Autowired
    private IProductOrderService productOrderServie;
    //商品信息分页查询
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name,Long uid){
        Page pageinfo=new Page<>(page,pageSize);
        LambdaQueryWrapper<ProductOrder> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(name),ProductOrder::getPrepare2,name);   //模糊查询商品名称
        queryWrapper.eq(ProductOrder::getUserId,uid);
        queryWrapper.orderByAsc(ProductOrder::getOrderTime);  //按照创建时间排序
        productOrderServie.page(pageinfo,queryWrapper);
        return R.success(pageinfo);
    }
}

