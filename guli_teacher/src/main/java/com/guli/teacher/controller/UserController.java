package com.guli.teacher.controller;

import com.guli.common.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
@CrossOrigin
@Api(description = "后台用户管理")
public class UserController {

    @PostMapping("login")
    @ApiOperation(value = "登录")
    public Result login(){
        return Result.ok().data("token","admin");
    }
    @GetMapping("info")
    @ApiOperation(value = "获取登录的信息")
    public Result info(){
        return Result.ok()
                .data("roles","[\"admin\"]")
                .data("name","admin")
                .data("avatar","./404");
    }
}
