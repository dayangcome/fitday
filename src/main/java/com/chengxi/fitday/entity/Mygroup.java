package com.chengxi.fitday.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = false)
public class Mygroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id")
    private Long id;

    private String name;

    private String tags;

    private String introduction;

    private Integer members;

    private String avatar;

    private Long leader;

    private Long deputyleader1;

    private Long deputyleader2;

    @TableLogic
    private Integer is_deleted;

    /**
     * 最多人数限制
     */
    private Integer mymax;

    /**
     * 是否需要验证？
     */
    private String mycheck;

    /**
     * 组长名字
     */
    private String leadername;

    /**
     * 副组长1名字
     */
    private String dleadername1;

    /**
     * 副组长2名字
     */
    private String dleadername2;

    /**
     * 组长头像
     */
    private String leaderavatar;

    /**
     * 副组长1头像
     */
    private String davatar1;

    /**
     * 副组长2头像
     */
    private String davatar2;


}
