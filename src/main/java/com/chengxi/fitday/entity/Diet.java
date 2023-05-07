package com.chengxi.fitday.entity;

import java.math.BigDecimal;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = false)
public class Diet implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 食谱ID
     */
    @TableId(value = "diet_id",type = IdType.AUTO)
    private Long dietId;

    /**
     * 食谱标题
     */
    private String dietTitle;

    /**
     * 食谱类别ID（每一个id代表一种类别）:
1表示素菜

:
2表示肉菜

     */
    private Long dietCategoryId;

    /**
     * 封面
     */
    private String cover;

    /**
     * 烹饪方法
     */
    private String cookingMethod;

    /**
     * 食谱热量
     */
    private Float dietHeat;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 点击数
     */
    private Integer hits;

    private Integer praiseNum;

    /**
     * 适宜人群
     */
    private String suitablePerson;

    /**
     * 是否删除
     */
    private Integer isDelete;


}
