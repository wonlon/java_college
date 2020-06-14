package com.guli.vod.controller;

import com.guli.common.result.Result;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/video")
@CrossOrigin
@Api(description = "Oss视频管理")
public class VodController {
    @DeleteMapping(value = "/{videoId}")
    public Result removeVideo(@PathVariable("videoId") String videoId){
        return Result.ok().data("msg","远程调用api，删除："+videoId+", 调用成功了~~~");
    }
}
