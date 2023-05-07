package com.chengxi.fitday.service.impl;

import com.chengxi.fitday.entity.User;
import com.chengxi.fitday.mapper.UserMapper;
import com.chengxi.fitday.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
