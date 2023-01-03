package com.chengxi.fitday.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.chengxi.fitday.common.R;
import com.chengxi.fitday.dto.Registerinfodto;
import com.chengxi.fitday.entity.User;
import com.chengxi.fitday.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 成溪科技公司开发
 * @since 2022-12-26
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    //用户账号登录
    @PostMapping("/login")
    public R<User> login(HttpServletRequest request, @RequestBody User user){
        String password=user.getPassword();
        password=DigestUtils.md5DigestAsHex(password.getBytes());
        LambdaQueryWrapper<User> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getAccount,user.getAccount());
        User user1= userService.getOne(queryWrapper);
        //user1代表使用前端传过来的数据在数据库中查到的账号
        if(user1==null){
            return R.error("账号不存在！");
        }
        if(!password.equals(user1.getPassword())){
            return R.error("账号或密码错误，请重新输入！");
        }
        if(user1.getStatus()==0){
            return R.error("账号已冻结！");
        }
        request.getSession().setAttribute("useruid",user1.getUid());
        return R.success(user1);
    }

    //用户手机号登录
    @PostMapping("/phonelogin")
    public R<User> phonelogin(HttpServletRequest request, @RequestBody User user){
        String password=user.getPassword();
        password=DigestUtils.md5DigestAsHex(password.getBytes());
        LambdaQueryWrapper<User> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getPhone,user.getPhone());
        User user1= userService.getOne(queryWrapper);
        //user1代表使用前端传过来的数据在数据库中查到的账号
        if(user1==null){
            return R.error("该手机号不存在！");
        }
        if(!password.equals(user1.getPassword())){
            return R.error("账号或密码错误，请重新输入！");
        }
        if(user1.getStatus()==0){
            return R.error("账号已冻结！");
        }
        request.getSession().setAttribute("useruid",user1.getUid());
        return R.success(user1);
    }

    //注册账号
    @PostMapping("/register")
    public R<String> register( HttpSession session,@RequestBody Registerinfodto registerinfodto){
        LambdaQueryWrapper<User> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getAccount,registerinfodto.getMyaccount());
        User user1= userService.getOne(queryWrapper);
        if(user1!=null){
            return R.error("该账号已存在，请重新设置账号！");
        }
        if(!registerinfodto.getMypassword().equals(registerinfodto.getMyagainpassword())){
            return R.error("两次输入的密码不一致！");
        }
        String correctcode=(String)session.getAttribute("identifyCode");
        if(!correctcode.equalsIgnoreCase(registerinfodto.getCode())){
            return R.error("验证码错误！");
        }
        User user=new User();
        user.setAccount(registerinfodto.getMyaccount());
        user.setPassword(DigestUtils.md5DigestAsHex(registerinfodto.getMypassword().getBytes()));
        user.setUsername(registerinfodto.getMyname());
        user.setStatus(1);
        user.setExp(0);
        user.setLevel(1);
        user.setViplevel(0);
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        user.setRemain(new BigDecimal("0"));
        user.setRolenum(1);
//        System.out.println(user);
//        return null;
        userService.save(user);
        return R.success("注册成功");
    }
}

