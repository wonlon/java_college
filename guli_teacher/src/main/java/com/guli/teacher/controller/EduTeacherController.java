package com.guli.teacher.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.common.result.EduException;
import com.guli.common.result.Result;
import com.guli.common.result.ResultCode;
import com.guli.teacher.entity.EduTeacher;
import com.guli.teacher.entity.query.TeacherQuery;
import com.guli.teacher.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author guli
 * @since 2020-02-13
 */
@RestController
@RequestMapping("/teacher")
@CrossOrigin
@Api(description = "讲师管理")
public class EduTeacherController {

    @Autowired
    private EduTeacherService teacherService;

    @ApiOperation(value = "所有讲师列表")
    @GetMapping("list")
    public Result list(){
        try {
            List<EduTeacher> list = teacherService.list(null);
            return Result.ok().data("items",list);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error();
        }
    }
    @ApiOperation(value = "讲师删除")
    @DeleteMapping("{id}")
    //占位符:
    //1、如果占位符和形参名一致，那么@PathVariable可以省略
    //2、如果配置了swagger，并在形参前加了其他的注解，那么@PathVariable 必须加
    public Result deleteTeacherById(
            @ApiParam(name = "id" , value = "讲师id", required = true)
            @PathVariable(value = "id") String id)
    {
        try {
            teacherService.removeById(id);
            return Result.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error();
        }
    }

    @ApiOperation(value = "讲师列表分页")
    @PostMapping("/{page}/{limit}")
    public Result selectTeacheryPageAndWrapper(
            @ApiParam(name = "page" , value = "当前页",required = true)
            @PathVariable Integer page,
            @ApiParam(name = "limit" , value = "每页显示记录数",required = true)
            @PathVariable Integer limit,
            @RequestBody TeacherQuery teacherQuery
            ){

        try {
            Page<EduTeacher> pageParam = new Page<>(page, limit);

            teacherService.pageQuery(pageParam, teacherQuery);

            List<EduTeacher> records = pageParam.getRecords();
            long total = pageParam.getTotal();

//            teacherService.page(pageParam, null);
//            List<EduTeacher> records = pageParam.getRecords();
//            long total = pageParam.getTotal();

            return  Result.ok().data("total", total).data("rows", records);
        } catch (Exception e) {
            e.printStackTrace();
            return  Result.error();
        }

    }

    @ApiOperation(value = "新增讲师")
    @PostMapping("add")
    public Result save(
            @ApiParam(name = "teacher", value = "讲师对象", required = true)
            @RequestBody EduTeacher teacher){

        try {
            teacherService.save(teacher);
            return Result.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error();
        }
    }

    @ApiOperation(value = "根据ID查询讲师")
    @GetMapping("{id}")
    public Result getById(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id){
        //测试自定义异常
        EduTeacher teacher = teacherService.getById(id);
        if(teacher == null)
        {
            throw new EduException(ResultCode.EDU_ID_ERROR, "没有此讲师信息");
        }
        try {
//            EduTeacher teacher = teacherService.getById(id);
            return Result.ok().data("item", teacher);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error();
        }
    }


    @ApiOperation(value = "根据ID修改讲师")
    @PutMapping("{id}")
    public Result updateById(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id,

            @ApiParam(name = "teacher", value = "讲师对象", required = true)
            @RequestBody EduTeacher teacher){

        try {
            teacher.setId(id);
            teacherService.updateById(teacher);
            return Result.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error();
        }
    }

    @ApiOperation(value = "分页讲师列表")
    @GetMapping(value = "{page}/{limit}")
    public Result pageList(
        @ApiParam(name = "page", value = "当前页码", required = true)
        @PathVariable Long page,
        @ApiParam(name = "limit", value = "每页记录数", required = true)
        @PathVariable Long limit){
        Page<EduTeacher> pageParam = new Page<EduTeacher>(page, limit);
        Map<String, Object> map = teacherService.pageListWeb(pageParam);
        return  Result.ok().data(map);
    }

}

