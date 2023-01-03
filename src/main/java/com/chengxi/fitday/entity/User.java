package com.chengxi.fitday.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author 成溪科技公司开发
 * @since 2022-12-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户的id
     */
    @TableId(value = "uid")
    private Long uid;

    /**
     * 账号
     */
    private String account;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    private String username;

    /**
     * 绑定的电话号码
     */
    private String phone;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 个性签名
     */
    private String sign;

    /**
     * 账号状态 0:冻结 1:正常
     */
    private Integer status;

    /**
     * 用户当前经验
     */
    private Integer exp;

    /**
     * 用户当前等级，从1开始
     */
    private Integer level;

    /**
     * 用户vip等级，0为非vip
     */
    private Integer viplevel;

    /**
     * 用户当前账户余额
     */
    private BigDecimal remain;

    /**
     * 用户地址
     */
    private String address;

    /**
     * 身份证号
     */
    private String idNumber;

    /**
     * 微信号
     */
    private String wxNumber;

    /**
     * 创建账号时间
     */
    private LocalDateTime createTime;

    /**
     * 上一次更新账号的时间
     */
    private LocalDateTime updateTime;

    /**
     * 属于的角色类型，1：普通用户
     */
    private Integer rolenum;

    /**
     * 是否删除
     */
    @TableLogic
    private Integer isDeleted;


}
