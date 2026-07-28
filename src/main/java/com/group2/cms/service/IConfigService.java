package com.group2.cms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.group2.cms.entity.Config;
import com.group2.cms.service.dto.ConfigAddDTO;
import com.group2.cms.service.dto.ConfigSelectDTO;
import com.group2.cms.service.dto.ConfigUpdateDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public interface IConfigService {
    
    IPage<Config> findByPage(Integer pageNum, Integer pageSize);

    void save(@Valid ConfigAddDTO dto);

    void updateById(@Valid ConfigUpdateDTO dto);

    ConfigSelectDTO getAllEnabledConfig();

    void updateConfigStatus(Integer id, Integer status);

    void deleteConfigById(Integer id);
}
