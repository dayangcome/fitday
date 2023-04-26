package com.chengxi.fitday.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.chengxi.fitday.common.R;
import com.chengxi.fitday.dto.Buydto;
import com.chengxi.fitday.entity.*;
import com.chengxi.fitday.service.IProductService;
import com.chengxi.fitday.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 成溪科技公司开发
 * @since 2023-04-23
 */
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private IProductService productService;

    @Autowired
    private IUserService userService;

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
    public R<String> buygoods(@RequestBody Buydto buydto){
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
            productService.updateById(product);
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();      //手动回滚
            return R.error("支付过程出现异常");
        }
        return R.success("购买成功");
    }
}

