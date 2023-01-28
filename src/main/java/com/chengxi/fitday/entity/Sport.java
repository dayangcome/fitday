package com.chengxi.fitday.entity;

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
 * @since 2023-01-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Sport implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 运动id
     */
    @TableId(value = "sport_id")
    private Long sportId;

    /**
     * 运动名称
     */
    private String sportName;

    /**
     * 运动类别
     */
    private String sportCategory;

    /**
     * 封面
     */
    private String picture;

    /**
     * 运动强度(千卡/斤/分钟)
     */
    private Double intensity;

    /**
     * 推荐食物id
     */
    private Long linkfoodId;

    /**
     * 是否删除
     */
    @TableLogic
    private Integer isDeleted;


}
