package com.guli.teacher.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.guli.common.result.Result;
import com.guli.teacher.entity.EduVideo;
import com.guli.teacher.service.EduVideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author guli
 * @since 2020-02-16
 */
@RestController
@RequestMapping("/video")
@CrossOrigin
@Api(description = "小节管理")
public class EduVideoController {

    @Autowired
    private EduVideoService videoService;

    /**
     * 1、保存
     */
    @ApiOperation(value = "新增小节")
    @PostMapping("save")
    public Result save(@RequestBody EduVideo video){
        boolean save = videoService.save(video);
        if(save)
        {
            return  Result.ok();
        }
        else
        {
            return Result.error();
        }
    }
    /**
     * 2、根据ID查询video对象的 -回显
     */
    @ApiOperation("根据ID查询video对象")
    @GetMapping("{id}")
    public Result getVideoById(@PathVariable String id){
        EduVideo video = videoService.getById(id);
        return Result.ok().data("video",video);
    }
    /**
     * 3、修改
     */
    @ApiOperation("修改小节")
    @PutMapping("update")
    public Result update(@RequestBody EduVideo video){
        boolean update = videoService.updateById(video);
        if(update)
        {
            return Result.ok();
        }
        else
        {
            return Result.error();
        }
    }
    /**
     * 4、删除
     */
    @ApiOperation("删除小节")
    @DeleteMapping("{id}")
    public Result deleteById(@PathVariable String id)
    {
        /**
         * 这块的删除要把oss上的视频也删除掉
         */
        boolean flag = videoService.removeVideoById(id);
        if(flag)
        {
            return Result.ok();
        }
        else
        {
            return Result.error();
        }
    }
    @ApiOperation("删除OSS小节")
    @DeleteMapping("/oss/{id}")
    public Result deleteByOSSId(@PathVariable String id)
    {
        R result =  videoService.deleteByOSSId(id);
        return  Result.ok().data("res",result);
    }
}

