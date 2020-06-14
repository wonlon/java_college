package com.guli.teacher.controller;


import com.guli.common.result.Result;
import com.guli.teacher.entity.EduSubject;
import com.guli.teacher.entity.vo.OneSubject;
import com.guli.teacher.service.EduSubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author guli
 * @since 2020-02-15
 */
@RestController
@RequestMapping("/subject")
@CrossOrigin
@Api(description = "课程分类管理")
public class EduSubjectController {

    @Autowired
    private EduSubjectService subjectService;

    @ApiOperation(value = "导入课程分类")
    @PostMapping("import")
    public Result importExl(MultipartFile file){
        List<String> errorMessage = subjectService.importExl(file);
        if(errorMessage.size() == 0){
            return Result.ok();
        } else{
            return Result.error().data("message",errorMessage);
        }
    }

    @GetMapping("list")
    @ApiOperation(value = "课程列表")
    public Result getSubjectList(){
        List<OneSubject> list = subjectService.getTree();
        return Result.ok().data("subjectList",list);
    }

    @DeleteMapping("{id}")
    @ApiOperation(value = "删除课程")
    public Result deleteById(@PathVariable  String id){
        boolean b = subjectService.removeById(id);
        if(b){
            return Result.ok();
        } else {
            return Result.error();
        }
    }
    @ApiOperation(value = "新增一级分类")
    @PostMapping("saveLevelOne")
    public Result saveLevelOne(
            @ApiParam(name = "subject", value = "课程分类对象", required = true)
            @RequestBody EduSubject subject){

        boolean result = subjectService.saveLevelOne(subject);
        if(result){
            return Result.ok();
        }else{
            return Result.error().message("删除失败");
        }
    }
    @ApiOperation(value = "新增二级分类")
    @PostMapping("saveLevelTwo")
    public Result saveLevelTwo(
            @ApiParam(name = "subject", value = "课程分类对象", required = true)
            @RequestBody EduSubject subject){

        boolean result = subjectService.saveLevelTwo(subject);
        if(result){
            return Result.ok();
        }else{
            return Result.error().message("保存失败");
        }
    }
}

