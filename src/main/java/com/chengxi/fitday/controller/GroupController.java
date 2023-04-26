package com.chengxi.fitday.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.chengxi.fitday.common.R;
import com.chengxi.fitday.entity.*;
import com.chengxi.fitday.service.IArticlesService;
import com.chengxi.fitday.service.IGroupService;
import com.chengxi.fitday.service.IUserService;
import com.chengxi.fitday.service.IUsergroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 成溪科技公司开发
 * @since 2023-04-15
 */
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
    @PostMapping("/add/{uid}/{groupid}/{content}")
    public R<Articles> addcomments(@PathVariable Long uid,@PathVariable Long groupid,@PathVariable String content){

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
        articlesService.save(articles);
        return R.success(articles);
    }

    //查询小组动态
    @GetMapping("/getcomments/{groupid}")
    public R<List<Articles>> getcomments(@PathVariable Long groupid){
        LambdaQueryWrapper<Articles> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Articles::getGroupid,groupid);
        queryWrapper.orderByDesc(Articles::getCreatedate);
        List <Articles> arr=articlesService.list(queryWrapper);
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
}

