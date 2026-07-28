package com.group2.cms.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;

import com.group2.cms.entity.User;
import com.group2.cms.service.IUserService;
import com.group2.cms.util.Result;
import com.group2.cms.web.vo.UserVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author qimz
 * @since 2025-12-22
 */
@Tag(name = "用户模块")
@RestController
@RequestMapping("/auth/user")
public class UserController {

    @Autowired
    IUserService userService;
    /**
     * 1.获取当前登录用户的基本信息
     *
     * @param authHeader
     * @return
     */
    @Operation(summary = "获取当前登录用户的基本信息")
    @GetMapping("/info")
    public Result<User> getAll(@RequestHeader(name = "Authorization") String authHeader) {
        Integer userId = userService.getOperatorIdFromToken(authHeader);
        User currentUser = userService.getAll(userId);
        return Result.success(currentUser);
    }

    /**
     * 2.分页多条件查询用户信息
     *
     * @param pageNum
     * @param pageSize
     * @param role
     * @param id
     * @param username
     * @return
     */
    @Operation(summary = "分页多条件查询用户信息")
    @Parameters({
            @Parameter(name = "pageNum", description = "当前页码", example = "1"),
            @Parameter(name = "pageSize", description = "每页大小", example = "5"),
            @Parameter(name = "role", description = "角色", example = "0"),
            @Parameter(name = "id", description = "编号", example = "1"),
            @Parameter(name = "username", description = "用户名", example = "admin"),
    })

    @GetMapping("/page")
    public Result<IPage<UserVO>> findByPage(@RequestParam Integer pageNum,
                                            @RequestParam Integer pageSize,
                                            @RequestParam(required = false) Integer role,
                                            @RequestParam(required = false) Integer id,
                                            @RequestParam(required = false) String username) {
        IPage<UserVO> page = userService.findByPage(pageNum, pageSize, role, id, username);
        return Result.success(page);
    }
    /**
     * 3.更新用户信息
     */
    @Operation(summary = "更新用户信息")
    @Parameters({
            @Parameter(name = "id", description = "编号", example = "1"),
            @Parameter(name = "dob", description = "出生日期", example = "2000-10-15"),
            @Parameter(name = "email", description = "邮箱", example = "jsck@briup.com"),
            @Parameter(name = "gender", description = "性别", example = "0"),
            @Parameter(name = "realname", description = "真实姓名", example = "张三"),
            @Parameter(name = "telephone", description = "电话", example = "13700001111")
    })

    @PutMapping
    public Result<String> updateUser(@RequestParam Integer id,
                                     @RequestParam(required = false) String dob,
                                     @RequestParam(required = false) String email,
                                     @RequestParam(required = false) Integer gender,
                                     @RequestParam(required = false) String realname,
                                     @RequestParam(required = false) String telephone) {
        userService.updateUser(id, dob, email, gender, realname, telephone);
        return Result.success("更新成功！");
    }

    /**
     * 4.修改用户头像
     *
     * @param id
     * @param userIcon
     * @return
     */

    @Operation(summary = "修改用户头像")
    @PutMapping("/icon")
    public Result<String> updateIconByForm(
            @RequestParam("id") Integer id,
            @RequestParam("userIcon") String userIcon) {

        try {
            String result = userService.updateIcon(id, userIcon);
            return Result.success(result);
        } catch (Exception e) {
            return Result.error("头像修改失败: " + e.getMessage());
        }
    }

    /**
     * 5.通过id删除用户信息
     * @param id
     * @return
     */
    @Operation(summary = "根据id删除用户信息")
    @DeleteMapping
    @Parameters({
            @Parameter(name = "id",description = "id",example = "1")
    })
    public Result<String> deleteById(@RequestParam(required = false) Integer id){
        userService.deleteById(id);
        return Result.success("成功删除该用户信息！");
    }


}