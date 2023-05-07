package com.chengxi.fitday.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class DynamicComment implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "comment_id")
    private Long commentId;

    private Long dynamicId;

    private String commentText;

    private String productPicture;

    private Integer likes;

    private LocalDateTime createTime;

    private Long userId;

    @TableLogic
    private Integer isDelete;

    private String username;
}
