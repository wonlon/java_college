package com.guli.oss.controller;

import com.guli.common.result.Result;
import com.guli.oss.service.FileService;
import com.guli.oss.util.ConstantPropertiesUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api(description="阿里云文件管理")
@CrossOrigin //跨域
@RestController
@RequestMapping("/oss/file")
public class FileController {

    @Autowired
    private FileService fileService;

    /**
     * 文件上传
     *
     * @param file
     */
    @ApiOperation(value = "文件上传")
    @PostMapping("upload")
    @CrossOrigin
    public Result upload(
            @ApiParam(name = "file", value = "文件", required = true)
            @RequestParam("file") MultipartFile file,
            @ApiParam(name = "host", value = "文件上传路径", required = false)
            @RequestParam(value = "host", required = false) String host) {

        if(!StringUtils.isEmpty(host)){
            ConstantPropertiesUtil.FILE_HOST = host;
        }

        String uploadUrl = fileService.upload(file);
        //返回r对象
        return Result.ok().message("文件上传成功").data("url", uploadUrl);
    }

    @ApiOperation(value = "文件上传")
    @DeleteMapping(value = "/video/{videoId}")
    @CrossOrigin
    public Result removeVideo(@PathVariable("videoId") String videoId){
        //返回r对象
        return Result.ok().message("api 跨服务调用成功");
    }

}
