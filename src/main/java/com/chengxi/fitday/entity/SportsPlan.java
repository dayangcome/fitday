package com.chengxi.fitday.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author 成溪科技公司开发
 * @since 2023-01-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sports_plan")
public class SportsPlan implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 运动计划id
     */
    @TableId(value = "sport_plan_id")
    private Long sportPlanId;

    /**
     * 日期
     */
    private LocalDateTime date;

    /**
     * 运动计划内容
     */
    private String content;
    private Long userid;
    /**
     * 是否删除
     */
    private Integer isDelect;


}
