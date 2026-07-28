package com.group2.cms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author luojy
 * @since 2025-12-22
 */

@Data
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("base_log")
public class Log implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "log_id", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 访问用户账号
     */
    @TableField("log_username")
    private String username;

    /**
     * 访问用户真实姓名
     */
    @TableField("log_realname")
    private String realname;

    /**
     * 请求的方式，get post delete put
     */
    @TableField("log_request_method")
    private String requestMethod;

    /**
     * 请求的地址
     */
    @TableField("log_request_uri")
    private String requestUri;

    /**
     * 请求的时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("log_time")
    private LocalDateTime time;
}
