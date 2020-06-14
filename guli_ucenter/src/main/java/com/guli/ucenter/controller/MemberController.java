package com.guli.ucenter.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.guli.common.result.Result;
import com.guli.ucenter.service.MemberService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author guli
 * @since 2020-02-22
 */
@RestController
@CrossOrigin
@RequestMapping("/admin/ucenter/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @ApiOperation(value = "今日注册数")
    @GetMapping(value = "count-register/{day}")
    public Result registerCount(
        @ApiParam(name = "day", value = "统计日期")
        @PathVariable String day){
        Integer count = memberService.countRegisterByDay(day);
        return Result.ok().data("countRegister", count);
    }
}

