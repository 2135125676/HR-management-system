package com.group2.cms.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author luojy
 * @since 2025-12-22
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Getter
@Setter
@ToString
@TableName("base_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId("id")
    private Integer id;

    /**
     * 用户名
     */
    @TableField("username")
    private String username;

    /**
     * 密码
     */
    @TableField("password")
    private String password;

    /**
     * 电话
     */
    @TableField("telephone")
    private String telephone;

    /**
     * 邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 用户真实姓名
     */
    @TableField("realname")
    private String realname;

    /**
     * 头像地址
     */
    @TableField("icon")
    private String icon;

    /**
     * 性别
     */
    @TableField("gender")
    private Integer gender;

    /**
     * 生日
     */
    @JsonProperty("dob")
    @TableField("dob")
    private LocalDate dob;

    /**
     * 注册时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("register_time")
    private LocalDateTime registerTime;

    /**
     * 账户状态：0-正常，1-禁用
     */
    @TableField("status")
    private Integer status;

    /**
     * 角色：0-管理员，1-普通用户
     */
    @TableField("role")
    private Integer role;

}

