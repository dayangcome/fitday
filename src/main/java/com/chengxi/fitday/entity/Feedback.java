package com.chengxi.fitday.entity;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = false)
public class Feedback implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id")
    private Long id;

    /**
     * 反馈类型，0：普通反馈，1：举报其他用户
     */
    private Integer type;

    /**
     * 反馈人uid
     */
    private Long useruid;

    private String name;

    private String email;

    private String phone;

    /**
     * 被举报的人uid，可以为null
     */
    private Long reportuid;

    /**
     * 举报类型，可以为null
     */
    private Integer reporttype;

    private String message;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 发送时间
     */
    private LocalDateTime createTime;

    /**
     * 逻辑删除
     */
    @TableLogic
    private Integer isDeleted;


}
