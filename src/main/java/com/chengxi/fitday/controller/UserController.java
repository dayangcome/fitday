package com.chengxi.fitday.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chengxi.fitday.common.R;
import com.chengxi.fitday.dto.FreezeInfodto;
import com.chengxi.fitday.dto.Registerinfodto;
import com.chengxi.fitday.entity.Freeze;
import com.chengxi.fitday.entity.User;
import com.chengxi.fitday.service.IFreezeService;
import com.chengxi.fitday.service.IUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private IFreezeService freezeService;

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
            return R.error("freeze"); //账号封禁，后端接收
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
            return R.error("手机号或密码错误，请重新输入！");
        }
        if(user1.getStatus()==0){
            return R.error("freeze");   //账号封禁，后端接收
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
        //设置默认头像
        user.setAvatar("https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png");
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

    //用户信息分页查询（员工后台）
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name){
        Page pageinfo=new Page<>(page,pageSize);
        LambdaQueryWrapper<User> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotEmpty(name),User::getUsername,name);   //精确查询昵称
        queryWrapper.orderByDesc(User::getCreateTime);  //按照创建时间排序
        userService.page(pageinfo,queryWrapper);
        return R.success(pageinfo);
    }

    //封禁用户账号（员工后台）
    @PutMapping("/freeze")
    public R<String> freezeuser(@RequestBody FreezeInfodto freezeInfodto){
        User user1= userService.getById(freezeInfodto.getId());
        if(user1==null){
            return R.error("该用户不存在！");
        }
        if(user1.getStatus()==0){
            return R.error("该用户已经被封禁！");
        }
        user1.setStatus(0);
        userService.updateById(user1);    //把用户状态改为0，标记为封禁

        Freeze freeze=new Freeze();
        freeze.setUseruid(freezeInfodto.getId());
        freeze.setEmpeid(freezeInfodto.getEid());
        freeze.setReason(freezeInfodto.getReason());
        freeze.setDesciption(freezeInfodto.getDes());
        freeze.setCreateTime(LocalDateTime.now());
        freezeService.save(freeze);        //把封禁信息写入数据库
        return R.success("成功封禁");
    }

    //解封用户账号（员工后台）
    @GetMapping("/delfreeze/{id}")
    public R<String> delfreezeuser(@PathVariable Long id){
        User user1= userService.getById(id);
        if(user1==null){
            return R.error("该用户不存在！");
        }
        if(user1.getStatus()==1){
            return R.error("该用户未处于封禁状态！");
        }
        user1.setStatus(1);
        userService.updateById(user1);    //把用户状态改为1，代表解封

        LambdaQueryWrapper<Freeze> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Freeze::getUseruid,id);
        Freeze freeze= freezeService.getOne(queryWrapper);
        freezeService.removeById(freeze.getId());        //把封禁信息删除
        return R.success("成功解封");
    }

    //获得账号封禁信息（账号登录）
    @GetMapping("/freezeInfo/{account}")
    public R<Freeze> freezeInfo(@PathVariable String account){
        LambdaQueryWrapper<User> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getAccount,account);
        User user1= userService.getOne(queryWrapper);
        if(user1==null){
            return R.error("请求出错");
        }
        LambdaQueryWrapper<Freeze> queryWrapper2=new LambdaQueryWrapper<>();
        queryWrapper2.eq(Freeze::getUseruid,user1.getUid());
        Freeze freeze= freezeService.getOne(queryWrapper2);
        if(freeze==null){
            return R.error("请求出错");
        }
        return R.success(freeze);
    }

    //获取账号封禁信息（手机登录）
    @GetMapping("/phonefreezeInfo/{phone}")
    public R<Freeze> phonefreezeInfo(@PathVariable String phone){
        LambdaQueryWrapper<User> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getPhone,phone);
        User user1= userService.getOne(queryWrapper);
        if(user1==null){
            return R.error("请求出错");
        }
        LambdaQueryWrapper<Freeze> queryWrapper2=new LambdaQueryWrapper<>();
        queryWrapper2.eq(Freeze::getUseruid,user1.getUid());
        Freeze freeze= freezeService.getOne(queryWrapper2);
        if(freeze==null){
            return R.error("请求出错");
        }
        return R.success(freeze);
    }


}

