package com.chengxi.fitday.entity;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = false)
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "eid")
    private Long eid;

    private String account;

    private String password;

    private String name;

    private String phone;

    private Integer sex;

    private String avatar;
    /**
     * 账号状态 0:冻结 1:正常
     */
    private Integer status;
    /**
     * 属于的角色类型，0：超级管理员
     */
    private Integer rolenum;

    private String idNumber;

    private String wxNumber;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    @TableLogic
    private Integer isDeleted;

}
