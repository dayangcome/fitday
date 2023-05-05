package com.chengxi.fitday.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chengxi.fitday.common.R;
import com.chengxi.fitday.entity.Recharge;
import com.chengxi.fitday.entity.User;
import com.chengxi.fitday.service.IRechargeService;
import com.chengxi.fitday.service.IUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 用户充值表 前端控制器
 * </p>
 *
 * @author 成溪科技公司开发
 * @since 2023-03-29
 */
@RestController
@RequestMapping("/recharge")
public class RechargeController {
    @Autowired
    private IRechargeService rechargeService;
    @Autowired
    private IUserService userService;

    //充值业务
    @Transactional(rollbackFor = Exception.class)      //添加事务，如果充值出了问题则进行回滚
    @PostMapping("/moneyadd")
    public R<String> mymoneyadd(@RequestBody Recharge recharge){
        try{
            User user=userService.getById(recharge.getUid());
            if(user==null){
                return R.error("用户未找到");
            }
            user.setRemain(user.getRemain().add(recharge.getPrice()));      //用户充值
            userService.updateById(user);       //更新用户

            Recharge recharge1=new Recharge();
            recharge1.setUid(recharge.getUid());

            String num1=(int)(Math.random()*1000000)+"";           //6位随机数
            String num2=(int)(Math.random()*1000000)+"";           //6位随机数
            String num3=(int)(Math.random()*1000000)+"";           //6位随机数
            String orderid=new Date().getTime()+num1+num2+num3;          //当前时间戳
            recharge1.setOrderId(orderid);                               //生成订单号

            recharge1.setPrice(recharge.getPrice());
            recharge1.setType(recharge.getType());
            recharge1.setPrepare2("模拟充值"+recharge.getPrice()+"元，账户当前余额为"+user.getRemain()+"元");
            recharge1.setPaid(true);
            recharge1.setPayTime(LocalDateTime.now());
            rechargeService.save(recharge1);

        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();      //手动回滚
            return R.error("出现异常");
        }
       return R.success("充值成功");
    }

    //充值历史查询
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, Long uid){
        Page pageinfo=new Page<>(page,pageSize);
        LambdaQueryWrapper<Recharge> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Recharge::getUid,uid);
        queryWrapper.orderByDesc(Recharge::getPayTime);  //按照创建时间排序
        rechargeService.page(pageinfo,queryWrapper);
        return R.success(pageinfo);
    }

}

