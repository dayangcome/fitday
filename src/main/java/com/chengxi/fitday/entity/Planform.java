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
 * @since 2023-02-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Planform implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id")
    private Long id;

    /**
     * 绑定用户
     */
    private Long userid;

    private String plan1;

    private String plan2;

    private String plan3;

    private String plan4;

    private String plan5;

    @TableLogic
    private Integer isDeleted;

    /**
     * 备注
     */
    private String des;

    /**
     * 计划名
     */
    private String title;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;


}
