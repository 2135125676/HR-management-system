package com.group2.cms.service.dto;

import lombok.Data;

/**
 * 查询启用配置的返回信息，不包含 delete
 */
@Data
public class ConfigSelectDTO {
    private Integer id;

    private String name;

    private String info;

    private String icon;

    private Integer status;
}
