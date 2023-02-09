package com.chengxi.fitday.entity;

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
 * @since 2023-02-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Userinfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户信息id
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 绑定用户
     */
    private Long userId;

    /**
     * 最新身高
     */
    private Double height;

    /**
     * 最新体重
     */
    private Double weight;

    /**
     * 最新BMI
     */
    private Double bmi;

    /**
     * 肥胖程度
     */
    private Integer stage;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 体脂率
     */
    private Float fatRate;

    /**
     * 是否删除
     */
    @TableLogic
    private Integer isDeleted;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 认证
     */
    private String certificate;

    /**
     * 关注数量
     */
    private Integer attention;

    /**
     * 粉丝数量
     */
    private Integer fans;

    /**
     * 拉黑数量
     */
    private Integer black;

    /**
     * 备用字段
     */
    private String prepare;


}
