package com.group2.cms.web.vo;

import cn.hutool.core.date.DateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;


@Schema(name = "UserVO",description = "记录信息")
@Data
public class UserVO {
    @Schema(name = "id",description = "编号")
    private Integer id;
    @Schema(name = "username",description = "用户名")
    private String username;
    @Schema(name = "password",description = "密码")
    private String password;
    @Schema(name = "telephone",description = "电话")
    private String telephone;
    @Schema(name = "realname",description = "用户真实姓名")
    private String realname;
    @Schema(name = "icon",description = "头像地址")
    private String icon;
    @Schema(name = "gender",description = "性别")
    private Integer gender;
    @Schema(name = "dob",description = "生日")
    @JsonFormat(pattern = "yyyy-MM-dd ")
    private LocalDate dob;
    @Schema(name = "email",description = "邮箱")
    private String email;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @Schema(name = "registerTime",description = "注册时间")
    private LocalDateTime registerTime;
    @Schema(name = "status",description = "账户状态：0-正常，1-禁用")
    private Integer status;
    @Schema(name = "role",description = "角色：0-管理员，1-普通用户")
    private Integer role;
}