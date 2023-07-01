package com.chengxi.fitday.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chengxi.fitday.common.R;
import com.chengxi.fitday.dto.Buydto;
import com.chengxi.fitday.entity.*;
import com.chengxi.fitday.service.IProductOrderService;
import com.chengxi.fitday.service.IProductService;
import com.chengxi.fitday.service.IUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private IProductService productService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IProductOrderService productOrderService;

    //商品信息分页查询
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name){
        Page pageinfo=new Page<>(page,pageSize);
        LambdaQueryWrapper<Product> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(name),Product::getProductName,name);   //模糊查询商品名称
        productService.page(pageinfo,queryWrapper);
        return R.success(pageinfo);
    }

    //查询当前分类下的所有商品信息
    @GetMapping("/all/{type}")
    public R<List<Product>> getAll(@PathVariable Integer type){
        LambdaQueryWrapper<Product> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Product::getType,type);
        List <Product> arr=productService.list(queryWrapper);
        return R.success(arr);
    }

    //购买商品业务
    @Transactional(rollbackFor = Exception.class)      //添加事务，如果购买商品过程出了问题则进行回滚
    @PostMapping("/buy")
    public R<String> buyGoods(@RequestBody Buydto buydto){
        System.out.println("budto+++++++++"+buydto);
        try{
            User user=userService.getById(buydto.getUid());
            if(user==null){         //购买用户找不到
                return R.error("找不到购买用户");
            }
            Product product=productService.getById(buydto.getProid());
            if(product==null){         //商品找不到
                return R.error("找不到商品");
            }
            if(buydto.getType()==1){    //余额购买
                if(user.getRemain().compareTo(buydto.getFinalpay()) < 0){
                    return R.error("余额不足，支付失败！");
                }
                user.setRemain(user.getRemain().subtract(buydto.getFinalpay()));    //减去余额
                userService.updateById(user);
            }
            product.setSales(product.getSales()+buydto.getNum());       //购买量增加

            ProductOrder productOrder=new ProductOrder();   //增添订单
            productOrder.setProductId(buydto.getProid());
            productOrder.setUserId(buydto.getUid());
            productOrder.setNum(buydto.getNum());
            productOrder.setTotalPrice(buydto.getFinalpay());
            productOrder.setAddressId(buydto.getAddress());
            productOrder.setOrderTime(LocalDateTime.now());
            productOrder.setOrderState(1);
            productOrder.setPayMethod(buydto.getType());
            productOrder.setAvatar(product.getProductPicture());
            productOrder.setPrepare2(product.getProductName());
            productOrderService.save(productOrder);

            user.setExp(user.getExp()+100);     //购买商品加100经验
            user.setLevel(user.getExp()/1000+1);    //检查用户是否升级
            userService.updateById(user);           //更新用户信息

            productService.updateById(product);
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();      //手动回滚
            return R.error("支付过程出现异常");
        }
        return R.success("购买成功");
    }
}

