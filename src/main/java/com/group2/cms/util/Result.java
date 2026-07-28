package com.group2.cms.util;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 统一响应工具类
 * @param <T> 响应数据类型
 * @author liuzc
 */
@AllArgsConstructor
@Data
public class Result<T> { // 1 成功 0 失败
    private Integer status; // 响应状态
    private String message; // 响应信息
    private T data;         // 响应数据

    //设计一个操作成功的方法
    //新增 修改 删除
    public static Result success() {
        return new Result(1, "操作成功",null);
    }
    public static Result success(Integer code,String msg,Object data) {
        return new Result(code,msg,data);
    }
    public static Result success(String msg,Object data) {
        return new Result(1, msg,data);
    }
    //查询
    public static Result success(Object data) {
        return new Result(1, "操作成功",data);
    }
    //设计一个操作失败的方法
    public static Result error(Integer code,String msg,Object data) {
        return new Result(code,msg,data);
    }
    public static Result error(String msg,Object data) {
        return new Result(0, msg,data);
    }
    public static Result error(Object data) {
        return new Result(0, "操作失败",data);
    }
    public static Result error(String massage) {
        return new Result(0, massage,null);
    }
}
