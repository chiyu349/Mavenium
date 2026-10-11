package com.mavenium.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 用户实体（对应表 user）
 *
 * @author Mavenium
 */
@TableName("user")
@Data
public class User {

    /** 主键，雪花算法生成 */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /** 用户名 */
    private String name;

    /** 逻辑删除标记：0-未删除 1-已删除 */
    @TableLogic
    private Integer deleted;
}


