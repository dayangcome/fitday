package com.chengxi.fitday.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author 成溪科技公司开发
 * @since 2023-02-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("diet_plan")
public class DietPlan implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 饮食计划
     */
    @TableId(value = "diet_plan_id")
    private Long dietPlanId;

    private Long userid;
    /**
     * 日期
     */
    private LocalDateTime date;

    /**
     * 早餐计划
     */
    private String breakfast;

    /**
     * 午餐计划
     */
    private String lunch;

    /**
     * 晚餐计划
     */
    private String dinner;

    /**
     * 加餐计划
     */
    private String adddiet;

    /**
     * 摄入热量计算
     */
    private Float heatCount;

    /**
     * 饮食计划评价分析
     */
    private String analysis;

    /**
     * 是否删除
     */
    private Integer isDelect;


}
