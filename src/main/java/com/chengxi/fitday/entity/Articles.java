package com.chengxi.fitday.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableLogic;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class Articles implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 博文ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 发表用户ID
     */
    private Long userid;

    /**
     * 小组id
     */
    private Long groupid;

    /**
     * 博文内容
     */
    private String content;

    /**
     * 浏览量
     */
    private Long views;

    /**
     * 评论总数
     */
    private Long commentcount;

    /**
     * 发表时间
     */
    private LocalDateTime createdate;

    /**
     * 点赞总数
     */
    private Long likecount;

    /**
     * 预留字段1
     */
    private String prepare1;

    /**
     * 预留字段2
     */
    private String prepare2;

    /**
     * 逻辑删除
     */
    @TableLogic
    private Integer is_deleted;

    /**
     * 昵称
     */
    private String name;

    /**
     * 头像
     */
    private String avatar;


}
