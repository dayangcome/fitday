package com.chengxi.fitday.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 小程序商城-用户充值表
 * </p>
 *
 * @author 成溪科技公司开发
 * @since 2023-03-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Recharge implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 充值用户UID
     */
    private Long uid;

    /**
     * 订单号
     */
    private String orderId;

    /**
     * 充值金额
     */
    private BigDecimal price;

    /**
     * 充值类型
     */
    private String type;

    /**
     * 是否充值
     */
    private Boolean paid;

    /**
     * 充值支付时间
     */
    private LocalDateTime payTime;

    /**
     * 备用1
     */
    private BigDecimal prepare1;

    /**
     * 备用2
     */
    private String prepare2;

    /**
     * 逻辑删除
     */
    @TableLogic
    private Integer isDeleted;


}
