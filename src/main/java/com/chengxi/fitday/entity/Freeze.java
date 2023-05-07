package com.chengxi.fitday.entity;

import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableLogic;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = false)
public class Freeze implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long useruid;

    /**
     * 处理人
     */
    private Long empeid;

    /**
     * 详细描述
     */
    private String desciption;

    /**
     * 封号原因
     */
    private String reason;

    /**
     * 封号时间
     */
    private LocalDateTime createTime;

    @TableLogic
    private Integer isDeleted;


}
