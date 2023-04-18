package com.chengxi.fitday.controller;


import com.chengxi.fitday.common.R;
import com.chengxi.fitday.entity.Mygroup;
import com.chengxi.fitday.service.IGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    //添加小组
    @PostMapping("/add")
    public R<Mygroup> add(@RequestBody Mygroup mygroup){
        System.out.println(mygroup);
        mygroup.setMembers(0);
        groupService.save(mygroup);
        return R.success(mygroup);
    }
}

