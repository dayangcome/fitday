package com.chengxi.fitday.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author 成溪科技公司开发
 * @since 2023-03-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Uservideo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "video_id")
    private Long videoId;

    private Long userId;

    private String title;

    private String videolink;

    private Long hits;

    private Integer likes;

    private Integer dislikes;

    private Integer hots;

    private Integer categroy;

    private String introduction;

    private String taps;

    private LocalDateTime uploadtime;

    private LocalDateTime updatetime;

    private String picture;

    private Integer rank;

    private Integer shares;

    private Integer saves;

    private Integer comments;

    private Integer status;

    @TableLogic
    private Integer isDeleted;


}
