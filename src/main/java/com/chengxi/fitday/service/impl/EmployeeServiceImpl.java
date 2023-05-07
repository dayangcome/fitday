package com.chengxi.fitday.service.impl;

import com.chengxi.fitday.entity.Employee;
import com.chengxi.fitday.mapper.EmployeeMapper;
import com.chengxi.fitday.service.IEmployeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements IEmployeeService {

}
