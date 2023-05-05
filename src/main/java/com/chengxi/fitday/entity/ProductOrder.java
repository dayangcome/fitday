package com.chengxi.fitday.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = false)
public class ProductOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 订单ID
     */
    @TableId(value = "order_id")
    private Long orderId;

    /**
     * 商品ID
     */
    private Long productId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 数量
     */
    private Integer num;

    /**
     * 付款金额		
     */
    private BigDecimal totalPrice;

    /**
     * 地址
     */
    private String addressId;

    /**
     * 下单时间
     */
    private LocalDateTime orderTime;

    /**
     * 订单状态（0为未完成，1为完成下单，2为已签收）
     */
    private Integer orderState;

    /**
     * 备注
     */
    private String remark;

    /**
     * 付款方式
     */
    private Integer payMethod;

    /**
     * 是否删除
     */
    @TableLogic
    private Integer isDeleted;

    /**
     * 图片
     */
    private String avatar;

    /**
     * 商品名
     */
    private String prepare2;


}
