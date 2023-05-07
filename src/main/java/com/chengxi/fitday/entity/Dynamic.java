package com.chengxi.fitday.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = false)
public class Dynamic implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long userId;

    private String picture;

    private String content;

    private Integer likes;

    private Integer comments;

    private Integer shares;

    private LocalDateTime uploadtime;

    private LocalDateTime modifytime;

    @TableLogic
    private Integer isDeleted;

    /**
     * 备用字段
     */
    private String prepare;

    /**
     * 动态标题（可以不要）
     */
    private String title;

    /**
     * 动态类型
     */
    private String category;


}
