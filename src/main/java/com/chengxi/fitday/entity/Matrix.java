package com.chengxi.fitday.entity;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class Matrix implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id")
    private Long id;

    /**
     * 矩阵行数（用户）
     */
    private Integer xs;

    /**
     * 矩阵列数（物品）
     */
    private Integer ys;

    /**
     * 矩阵描述
     */
    private String des;

    /**
     * 用户id
     */
    private Long myx;

    /**
     * 物品id
     */
    private Long myy;

    /**
     * 初始值
     */
    private Double myvalue;

    /**
     * 最终值
     */
    private Double finvalue;

    /**
     * 逻辑删除
     */
    @TableLogic
    private Integer is_deleted;

    /**
     * 创建时间
     */
    private LocalDateTime createtime;


}
