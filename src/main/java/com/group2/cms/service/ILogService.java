package com.group2.cms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.group2.cms.entity.Log;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @since 2025-12-22
 */
public interface ILogService extends IService<Log> {
    IPage<Log> findByPage(Integer pageNum, Integer pageSize);
}
