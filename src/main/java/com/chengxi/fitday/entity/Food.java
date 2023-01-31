package com.chengxi.fitday.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author 成溪科技公司开发
 * @since 2023-01-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Food implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "food_id",type = IdType.AUTO)
    private Long foodId;

    /**
     * 食品/食材名称
     */
    private String foodName;

    /**
     * 食物类别（水果蔬菜，肉类等）
     */
    private String foodCategory;

    /**
     * 封面
     */
    private String picture;

    /**
     * 热量
     */
    private String heat;

    /**
     * 营养分析
     */
    private String analysis;

    /**
     * 适宜人群
     */
    private String suitablePerson;

    /**
     * 饮食禁忌
     */
    private String dietTaboo;

    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updataeTime;

    /**
     * 是否删除
     */
    @TableLogic
    private Integer isDelete;

    /**
     * 脂肪
     */
    private String fat;

    /**
     * 碳水化合物
     */
    private String carbohydrate;

    /**
     * 蛋白质
     */
    private String protein;


}
