package com.group2.cms.service;

import com.group2.cms.service.dto.LoginDTO;

public interface ILoginService {

    /***
     * 登录操作
     * @param dto
     * @return
     */
    String login(LoginDTO dto);
}
