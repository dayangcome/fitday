package com.chengxi.fitday.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chengxi.fitday.common.R;
import com.chengxi.fitday.dto.FreezeInfodto;
import com.chengxi.fitday.dto.Registerinfodto;
import com.chengxi.fitday.entity.*;
import com.chengxi.fitday.service.IFreezeService;
import com.chengxi.fitday.service.IUserService;
import com.chengxi.fitday.service.IUserinfoService;
import com.chengxi.fitday.service.IUservideoService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IFreezeService freezeService;

    @Autowired
    private IUserinfoService userinfoService;

    @Autowired
    private IUservideoService uservideoService;

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


        user1.setUpdateTime(LocalDateTime.now());  //最近登录时间
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



        user1.setUpdateTime(LocalDateTime.now());  //最近登录时间
        request.getSession().setAttribute("useruid",user1.getUid());
        return R.success(user1);
    }

    //获得用户信息1
    @GetMapping("/getuser/{uid}")
    public R<User> getuser(@PathVariable Long uid){
        User user=userService.getById(uid);
        if(user==null){
            return R.error("未找到用户");
        }else {
            return R.success(user);
        }
    }

    //获得所有用户信息
    @GetMapping("/all")
    public R<List<User>> getalluser(){
        List <User> arr=userService.list();
        return R.success(arr);
    }

    //获得用户信息2
    @GetMapping("/userinfo/{uid}")
    public R<Userinfo> userinfo(@PathVariable Long uid){
        LambdaQueryWrapper<Userinfo> queryWrapper=new LambdaQueryWrapper<>(); //获得用户的体重等信息
        queryWrapper.eq(Userinfo::getUserId,uid);
        queryWrapper.eq(Userinfo::getStage,1);
        Userinfo userinfo=userinfoService.getOne(queryWrapper);
        if(userinfo==null){
            return R.error("用户未设置");
        }else {
            return R.success(userinfo);
        }
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
        user.setAvatar("https://s1.ax1x.com/2023/01/30/pSwEhpF.jpg");
        user.setStatus(1);
        user.setExp(0);
        user.setLevel(1);
        user.setViplevel(0);
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.of(1970, 1, 1, 0, 0,0));
        user.setRemain(new BigDecimal("0"));
        user.setRolenum(1);
        user.setSex(2);
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
        System.out.println(user1);
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

    //用户账号退出登录
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        request.getSession().removeAttribute("useruid");
        return R.success("退出成功");
    }

    //更改账号个人信息
    @PutMapping("/changeuserinfo")
    public R<String> changeuserinfo(@RequestBody User user){
        System.out.println(user);
        User user1=userService.getById(user.getUid());
        user1.setUsername(user.getUsername());
        user1.setPhone(user.getPhone());
        user1.setAvatar(user.getAvatar());
        user1.setSex(user.getSex());
        user1.setWxNumber(user.getWxNumber());
        try{
            userService.updateById(user1);
        }catch (Exception e){
            return R.error("修改失败，可能是由于手机号有重复");
        }
        return R.success("修改成功");
    }

    //删除用户账号
    @GetMapping("/deluser/{uid}")
    public R<String> deluser(@PathVariable Long uid){
        User user=userService.getById(uid);
        if(user==null){
            return R.error("该账号已经注销！");
        }
        userService.removeById(uid);        //删除用户信息
        return R.success("成功删除");
    }

    //用户填写了信息
    @PostMapping("/updatemyinfo")
    public R<Userinfo> updatemyinfo(@RequestBody Userinfo userinfo){
        Userinfo userinfo1=new Userinfo();
        userinfo1.setAttention(0);
        userinfo1.setBlack(0);
        userinfo1.setCreateTime(LocalDateTime.now());
        userinfo1.setUserId(userinfo.getUserId());
        userinfo1.setHeight(userinfo.getHeight());
        userinfo1.setWeight(userinfo.getWeight());
        userinfo1.setFans(0);
        userinfo1.setBmi(userinfo.getWeight()/userinfo.getHeight()/userinfo.getHeight()*10000);
        userinfo1.setStage(1);      //还未作废
        userinfoService.save(userinfo1);
        return R.success(userinfo1);
    }

    //更改账号信息
    @GetMapping("/quest/{uid}/{number}/{info}")
    public R<Userinfo> quest(@PathVariable Long uid,@PathVariable int number,@PathVariable String info){
        LambdaQueryWrapper<Userinfo> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Userinfo::getUserId,uid);
        queryWrapper.eq(Userinfo::getStage,1);//未作废的
        Userinfo userinfo= userinfoService.getOne(queryWrapper);
        if(userinfo==null){
            Userinfo userinfo1=new Userinfo();
            userinfo1.setAttention(0);
            userinfo1.setBlack(0);
            userinfo1.setCreateTime(LocalDateTime.now());
            userinfo1.setUserId(uid);
            if(number==1){
                userinfo1.setHeight(Double.parseDouble(info.trim()));
            }
            if(number==2){
                userinfo1.setWeight(Double.parseDouble(info.trim()));
            }
            if(number==3){
                userinfo1.setFatRate(Float.parseFloat(info.trim()));
            }
            if(number==4){
                userinfo1.setPrepare(info);
            }
            userinfo1.setFans(0);
            userinfo1.setStage(1);
            userinfoService.save(userinfo1);
            return R.success(userinfo1);
        }else{
            Userinfo userinfo1=new Userinfo(null,userinfo.getUserId(),userinfo.getHeight(),userinfo.getWeight(),userinfo.getBmi(),userinfo.getStage(),LocalDateTime.now(),userinfo.getFatRate(),userinfo.getIsDeleted(),userinfo.getAge(),userinfo.getCertificate(),userinfo.getAttention(),userinfo.getFans(),userinfo.getBlack(),userinfo.getPrepare());
            if(number==1){
                userinfo1.setHeight(Double.parseDouble(info.trim()));
                if(userinfo1.getWeight()!=null){
                    userinfo1.setBmi(userinfo1.getWeight()/userinfo1.getHeight()/userinfo1.getHeight()*10000);
                }
            }
            if(number==2){
                userinfo1.setWeight(Double.parseDouble(info.trim()));
                if(userinfo1.getHeight()!=null){
                    userinfo1.setBmi(userinfo1.getWeight()/userinfo1.getHeight()/userinfo1.getHeight()*10000);
                }
            }
            if(number==3){
                userinfo1.setFatRate(Float.parseFloat(info.trim()));
            }
            if(number==4){
                userinfo1.setPrepare(info.trim());
            }
            userinfoService.save(userinfo1);
            userinfo.setStage(0);   //停用
            userinfoService.updateById(userinfo);
            return R.success(userinfo1);
        }
    }

    //通过视频id查找到作者信息
    @GetMapping("/getbyvideo/{videoId}")
    public R<User> getbyvideo(@PathVariable Long videoId){
        Uservideo video=uservideoService.getById(videoId);
        if(video==null){
            return R.error("视频信息未找到！");
        }
        User user=userService.getById(video.getUserId());
        if(user==null){
            return R.error("作者信息未找到！");
        }
        return R.success(user);
    }


    //获得体重变化信息
    @GetMapping("/getc/{uid}")
    public R<List<Userinfo>> getmychange(@PathVariable Long uid){
        LambdaQueryWrapper<Userinfo> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Userinfo::getUserId,uid);
        queryWrapper.orderByAsc(Userinfo::getCreateTime);
        List<Userinfo> userinfos= userinfoService.list(queryWrapper);
        if(userinfos==null){
            return R.error("用户信息未找到！");
        }
        return R.success(userinfos);
    }

}

