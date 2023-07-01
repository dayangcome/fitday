package com.chengxi.fitday.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chengxi.fitday.common.R;
import com.chengxi.fitday.entity.*;
import com.chengxi.fitday.service.IArticlesService;
import com.chengxi.fitday.service.IGroupService;
import com.chengxi.fitday.service.IUserService;
import com.chengxi.fitday.service.IUsergroupService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/group")
public class GroupController {

    @Autowired
    private IGroupService groupService;

    @Autowired
    private IUsergroupService usergroupService;

    @Autowired
    private IArticlesService articlesService;

    @Autowired
    private IUserService userService;

    //创建小组
    @PostMapping("/add")
    public R<Mygroup> add(@RequestBody Mygroup mygroup){
        System.out.println(mygroup);
        Usergroup usergroup=new Usergroup();
        try{
            mygroup.setMembers(1);                      //初始人数为1，只有组长
            groupService.save(mygroup);
            usergroup.setGroupId(mygroup.getId());
            usergroup.setUserId(mygroup.getLeader());   //把组长加入这个小组
            usergroupService.save(usergroup);
        }catch (Exception e){
            e.printStackTrace();
            return R.error("小组创建失败");
        }
        return R.success(mygroup);
    }

    //小组信息分页查询
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name){
        Page pageinfo=new Page<>(page,pageSize);
        LambdaQueryWrapper<Mygroup> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(name),Mygroup::getName,name);   //模糊查询小组名称
        groupService.page(pageinfo,queryWrapper);
        return R.success(pageinfo);
    }

    //查询用户所在的小组
    @GetMapping("/getgroup/{uid}")
    public R<Mygroup> serch(@PathVariable Long uid){
        LambdaQueryWrapper<Usergroup> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Usergroup::getUserId,uid);
        Usergroup usergroup= usergroupService.getOne(queryWrapper);
        if(usergroup==null){
            return R.error("没有找到");
        }
        Mygroup group = groupService.getById(usergroup.getGroupId());
        if (group==null){
            return R.error("没有找到小组");
        }
        return R.success(group);
    }

    //查询当前所有小组信息
    @GetMapping("/all")
    public R<List<Mygroup>> getAll(){
        List <Mygroup> arr=groupService.list();
        return R.success(arr);
    }

    //用户在小组发动态
    @PostMapping("/addd/{uid}/{groupid}/{content}/{prepare1}")
    public R<Articles> addComments(@PathVariable Long uid,@PathVariable Long groupid,@PathVariable String content,@PathVariable String prepare1){

        Articles articles=new Articles();
        articles.setCreatedate(LocalDateTime.now());
        articles.setUserid(uid);
        User user=userService.getById(uid);
        if(user==null){
            return R.error("发布者信息未找到！");
        }
        articles.setAvatar(user.getAvatar());   //用户头像
        articles.setName(user.getUsername());
        articles.setGroupid(groupid);
        articles.setContent(content);
        articles.setPrepare1(prepare1);     //动态配图
        articlesService.save(articles);

        user.setExp(user.getExp()+100);     //发布动态加100经验
        user.setLevel(user.getExp()/1000+1);    //检查用户是否升级
        userService.updateById(user);           //更新用户信息

        return R.success(articles);
    }

    //查询小组动态
    @GetMapping("/getcomments/{groupid}")
    public R<List<Articles>> getComments(@PathVariable Long groupid){
        LambdaQueryWrapper<Articles> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Articles::getGroupid,groupid);
        queryWrapper.orderByDesc(Articles::getCreatedate);
        List <Articles> arr=articlesService.list(queryWrapper);
        return R.success(arr);
    }

    //查询小组动态
    @GetMapping("/allmem/{groupid}")
    public R<List<User>> getAllmems(@PathVariable Long groupid){
        LambdaQueryWrapper<Usergroup> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Usergroup::getGroupId,groupid);
        List<Usergroup> usergroups= usergroupService.list(queryWrapper);
        List<User> arr=new ArrayList<>();
        for(Usergroup u:usergroups){
            User user=userService.getById(u.getUserId());
            arr.add(user);
        }
        return R.success(arr);
    }

    //修改小组信息
    @PostMapping("/change")
    public R<Mygroup> change(@RequestBody Mygroup mygroup){
        System.out.println("change+++++++++++="+mygroup);
        Mygroup mygroup1=groupService.getById(mygroup.getId());
        try{
            mygroup1.setName(mygroup.getName());
            mygroup1.setIntroduction(mygroup.getIntroduction());
            mygroup1.setTags(mygroup.getTags());
            groupService.updateById(mygroup1);
        }catch (Exception e){
            e.printStackTrace();
            return R.error("小组修改失败");
        }
        return R.success(mygroup);
    }

    //用户加入小组
    @GetMapping("/join/{groupid}/{uid}")
    public R<Mygroup> join(@PathVariable Long groupid,@PathVariable Long uid){
        Mygroup mygroup=groupService.getById(groupid);
        if (mygroup.getMembers()+1>mygroup.getMymax()){
            return R.error("小组人数已满，不能继续加入！");
        }
        mygroup.setMembers(mygroup.getMembers()+1);  //小组成员+1
        groupService.updateById(mygroup);

        Usergroup usergroup=new Usergroup();
        usergroup.setUserId(uid);
        usergroup.setGroupId(groupid);
        usergroupService.save(usergroup);

        return R.success(mygroup);
    }

    //用户退出小组
    @GetMapping("/out/{uid}")
    public R<String> out(@PathVariable Long uid){
        LambdaQueryWrapper<Usergroup> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Usergroup::getUserId,uid);
        Usergroup usergroup = usergroupService.getOne(queryWrapper);
        usergroupService.removeById(usergroup.getId());
        Mygroup mygroup=groupService.getById(usergroup.getGroupId());
        mygroup.setMembers(mygroup.getMembers()-1); //小组人数-1
        groupService.updateById(mygroup);
        return R.success("退出小组");
    }

    //删除小组动态
    @GetMapping("/del/{id}")
    public R<String> delMydt(@PathVariable Long id){
        try{
            articlesService.removeById(id);       //把动态删除
            return R.success("成功删除");
        }catch (Exception e){
            return R.error(e.getMessage());
        }
    }
}

