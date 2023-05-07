package com.chengxi.fitday.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class Question implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 题目
     */
    private String title;

    /**
     * A选项描述
     */
    private String ansa;

    private String ansb;

    private String ansc;

    private String ansd;

    /**
     * 正确答案（A/B/C/D）
     */
    private String rightans;

    /**
     * 备用字段
     */
    private String prepared;


}
