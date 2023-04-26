package com.chengxi.fitday.entity;

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
 * @since 2023-04-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Usergroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id")
    private Long id;

    private Long userId;

    private Long groupId;

    @TableLogic
    private Integer isDelete;


}
