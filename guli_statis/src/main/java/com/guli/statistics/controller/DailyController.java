package com.guli.statistics.controller;


import com.guli.common.result.Result;
import com.guli.statistics.service.DailyService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author guli
 * @since 2020-02-22
 */
@CrossOrigin
@RestController
@RequestMapping("/admin/statistics/daily")
public class DailyController {


    @Autowired
    private DailyService dailyService;


    @ApiOperation(value = "展示统计数据")
    //返回进行统计的数据，两个数组
    @GetMapping("getCountData/{begin}/{end}/{type}")
    public Result getDataCount(@PathVariable String begin,
                          @PathVariable String end,
                          @PathVariable String type) {
        //返回数据包含两部分，使用map进行封装，返回
        Map<String,Object> map = dailyService.getCountData(begin,end,type);
        return Result.ok().data(map);
    }


    @ApiOperation(value = "生成统计数据")
    @PostMapping("{day}")
    public Result createStatisticsByDate(@PathVariable String day) {
        dailyService.createStatisticsByDay(day);
        return Result.ok();
    }

}

