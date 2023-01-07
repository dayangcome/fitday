package com.chengxi.fitday.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chengxi.fitday.common.R;
import com.chengxi.fitday.entity.Employee;
import com.chengxi.fitday.entity.User;
import com.chengxi.fitday.service.IEmployeeService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 成溪科技公司开发
 * @since 2022-12-26
 */
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private IEmployeeService employeeService;

    //员工账号登录（员工后台）
    @PostMapping("/login")
    public R<Employee> emplogin(HttpServletRequest request, @RequestBody Employee employee){
        String password=employee.getPassword();
        password= DigestUtils.md5DigestAsHex(password.getBytes());
        LambdaQueryWrapper<Employee> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getAccount,employee.getAccount());
        Employee emp=employeeService.getOne(queryWrapper);
        //user1代表使用前端传过来的数据在数据库中查到的账号
        if(emp==null){
            return R.error("账号不存在！");
        }
        if(!password.equals(emp.getPassword())){
            return R.error("账号或密码错误，请重新输入！");
        }
        if(emp.getStatus()==0){
            return R.error("账号已冻结！");
        }
        request.getSession().setAttribute("empeid",emp.getEid());
        return R.success(emp);
    }

    //员工账号退出登录（员工后台）
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        request.getSession().removeAttribute("empeid");
        return R.success("退出成功");
    }

    //员工信息分页查询（员工后台）
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name){
        Page pageinfo=new Page<>(page,pageSize);
        LambdaQueryWrapper<Employee> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotEmpty(name),Employee::getName,name);   //精确查询昵称
        queryWrapper.orderByDesc(Employee::getCreateTime);  //按照创建时间排序
        employeeService.page(pageinfo,queryWrapper);
        return R.success(pageinfo);
    }
}

