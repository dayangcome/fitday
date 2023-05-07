package com.chengxi.fitday.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = false)
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商品ID
     */
    @TableId(value = "product_id", type = IdType.AUTO)
    private Long productId;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品来源/厂家
     */
    private String source;

    /**
     * 当前价格
     */
    private Float price;

    /**
     * 原始价格
     */
    private Float originalPrice;

    /**
     * 图片
     */
    private String productPicture;

    /**
     * 视频链接
     */
    private String videolink;

    /**
     * 销量
     */
    private Integer sales;

    /**
     * 产品介绍
     */
    private String info;

    /**
     * 库存数量
     */
    private Integer stock;

    /**
     * 适用人群标签
     */
    private String suitTag;

    /**
     * 相关推荐产品
     */
    private String relateRecomm;

    /**
     * 是否删除
     */
    @TableLogic
    private Integer isDeleted;

    /**
     * 商品类型 1：课程 ，2：健身器材，3：食物，4：其他
     */
    private Integer type;


}
