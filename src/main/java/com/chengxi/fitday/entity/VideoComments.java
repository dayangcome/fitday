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
public class VideoComments implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 评论ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 发表用户ID
     */
    private Long userid;

    /**
     * 视频ID
     */
    private Long videoid;

    /**
     * 点赞数
     */
    private Long likecount;

    /**
     * 评论日期
     */
    private LocalDateTime commentdate;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 父评论ID
     */
    private Long parentcommentid;

    /**
     * 备用字段
     */
    private String prepare;


    //用户头像
    private String avatar;

    /**
     * 逻辑删除
     */
    @TableLogic
    private Integer isDeleted;


}
