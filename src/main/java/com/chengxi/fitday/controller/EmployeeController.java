package com.chengxi.fitday.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chengxi.fitday.common.R;
import com.chengxi.fitday.dto.Changecodedto;
import com.chengxi.fitday.dto.Empregisterdto;
import com.chengxi.fitday.dto.Registerinfodto;
import com.chengxi.fitday.entity.Employee;
import com.chengxi.fitday.entity.Freeze;
import com.chengxi.fitday.entity.User;
import com.chengxi.fitday.service.IEmployeeService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
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
            return R.error("账号已被封禁！请联系超级管理员！");
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

    //封禁员工账号（员工后台）
    @GetMapping("/freeze/{eid}")
    public R<String> freezeemp(@PathVariable Long eid){
        Employee employee= employeeService.getById(eid);
        if(employee==null){
            return R.error("该员工不存在！");
        }
        if(employee.getStatus()==0){
            return R.error("该员工已经被封禁！");
        }
        employee.setStatus(0);
        employeeService.updateById(employee);    //把员工状态改为0，标记为封禁
        return R.success("成功封禁");
    }

    //解封员工账号（员工后台）
    @GetMapping("/delfreeze/{eid}")
    public R<String> delfreezeemp(@PathVariable Long eid){
        Employee employee= employeeService.getById(eid);
        if(employee==null){
            return R.error("该员工不存在！");
        }
        if(employee.getStatus()==1){
            return R.error("该员工未处于封禁状态！");
        }
        employee.setStatus(1);
        employeeService.updateById(employee);    //把员工状态改为1，标记为解封
        return R.success("成功解除封禁");
    }

    //添加员工账号（员工后台）
    @PostMapping("/register")
    public R<String> register(@RequestBody Empregisterdto empregisterdto){
        LambdaQueryWrapper<Employee> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getAccount,empregisterdto.getAccount());
        Employee employee1= employeeService.getOne(queryWrapper);
        if(employee1!=null){
            return R.error("该账号已存在，请重新设置账号！");
        }
        if(!empregisterdto.getPassword().equals(empregisterdto.getAgainpassword())){
            return R.error("两次输入的密码不一致！");
        }

        Employee employee=new Employee();
        employee.setAccount(empregisterdto.getAccount());
        employee.setPassword(DigestUtils.md5DigestAsHex(empregisterdto.getPassword().getBytes()));
        employee.setName(empregisterdto.getName());
        employee.setPhone(empregisterdto.getPhone());
        //设置默认头像
        employee.setAvatar("https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png");
        employee.setStatus(1);
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
        employee.setRolenum(empregisterdto.getRolenum());
        employee.setSex(2);
//        System.out.println(employee);
//        return null;
        employeeService.save(employee);
        return R.success("注册成功");
    }

    //删除员工账号（员工后台）
    @GetMapping("/delemp/{eid}")
    public R<String> delemp(@PathVariable Long eid){
        Employee employee=employeeService.getById(eid);
        if(employee==null){
            return R.error("该账号已注销！");
        }
        employeeService.removeById(eid);        //删除员工信息
        return R.success("成功删除");
    }

    //更改员工账号个人信息（员工后台）
    @PutMapping("/changeempinfo")
    public R<String> changeempinfo(@RequestBody Employee employee){
        Employee emp=employeeService.getById(employee.getEid());

        LambdaQueryWrapper<Employee> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getPhone,employee.getPhone());
        Employee theemp=employeeService.getOne(queryWrapper);
        if(theemp!=null && !theemp.getEid().equals(emp.getEid())){
            return R.error("该手机号已存在！");
        }

        emp.setName(employee.getName());
        emp.setPhone(employee.getPhone());
        emp.setAvatar(employee.getAvatar());
        emp.setSex(employee.getSex());
        emp.setWxNumber(employee.getWxNumber());
        employeeService.updateById(emp);
        return R.success("修改成功");
    }

    //更改员工账号个人信息（员工后台）
    @PostMapping("/changecodeApi")
    public R<String> changecode(@RequestBody Changecodedto changecodedto){
        Employee emp=employeeService.getById(changecodedto.getEid());
        String oldcode=DigestUtils.md5DigestAsHex(changecodedto.getCode().getBytes());
        if(!oldcode.equals(emp.getPassword())){
            return R.error("原密码不正确！");
        }
        if(!changecodedto.getNewcode().equals(changecodedto.getNewcodeagain())){
            return R.error("两次输入的密码不一致！");
        }
        String newcode=DigestUtils.md5DigestAsHex(changecodedto.getNewcode().getBytes());
        emp.setPassword(newcode);
        employeeService.updateById(emp);
        return R.success("修改成功");
    }

}

