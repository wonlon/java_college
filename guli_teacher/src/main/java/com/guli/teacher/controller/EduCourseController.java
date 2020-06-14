package com.guli.teacher.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.common.result.Result;
import com.guli.teacher.entity.EduCourse;
import com.guli.teacher.entity.query.CourseQuery;
import com.guli.teacher.entity.vo.CourseDesc;
import com.guli.teacher.entity.vo.CoursePublishVo;
import com.guli.teacher.service.EduCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author guli
 * @since 2020-02-16
 */
@RestController
@RequestMapping("/course")
@CrossOrigin
@Api(description = "课程管理")
public class EduCourseController {

    @Autowired
    private EduCourseService courseService;

    @PostMapping("save")
    @ApiOperation(value = "创建课程")
    public Result saveCourse(@RequestBody CourseDesc courseDesc){ //接收课程和描述对象
        try {
            //接收课程和描述对象
            String courseId = courseService.saveCourseDesc(courseDesc);
            return Result.ok().data("courseId",courseId);

        } catch (Exception e) {
            e.printStackTrace();
            return Result.error();
        }
    }
    @GetMapping("{id}")
    @ApiOperation(value = "通过id获取课程")
    public Result getCourseVoById(@PathVariable String id)
    {
        CourseDesc course = courseService.getCourseVoById(id);
        return Result.ok().data("courseInfo",course);
    }
    @PutMapping("update")
    @ApiOperation(value = "修改课程信息")
    public Result updateCourse(@RequestBody CourseDesc courseVo){
        Boolean flag = courseService.updateCourseVo(courseVo);
        if(flag){
            return Result.ok().data("courseId",courseVo.getEduCourse().getId());
        }
        return Result.error();
    }
    @ApiOperation(value = "分页课程列表")
    @PostMapping("{page}/{limit}")
    public Result pageQuery(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,

            @ApiParam(name = "courseQuery", value = "查询对象", required = false)
            @RequestBody CourseQuery courseQuery){

        Page<EduCourse> pageParam = new Page<>(page, limit);
        courseService.pageQuery(pageParam, courseQuery);
        List<EduCourse> records = pageParam.getRecords();
        long total = pageParam.getTotal();
        return  Result.ok().data("total", total).data("rows", records);
    }

    @ApiOperation(value = "根据ID删除课程")
    @DeleteMapping("{id}")
    public Result removeById(
            @ApiParam(name = "id", value = "课程ID", required = true)
            @PathVariable String id){

        boolean result = courseService.removeCourseById(id);
        if(result){
            return Result.ok();
        }else{
            return Result.error().message("删除失败");
        }
    }

    /**
     * 根据课程Id查询课程Vo对象
     * @param id
     * @return
     */
    @ApiOperation(value = "根据课程Id查询课程Vo对象")
    @GetMapping("vo/{id}")
    public Result getCoursePublishVoById(@PathVariable String id){
        //第一种方式：封装对象
//        CoursePublishVo voursePublishVo = courseService.getCoursePublishVoById(id);
//        if(voursePublishVo != null){
//            return Result.ok().data("item",voursePublishVo);
//        } else {
//            return Result.error();
//        }

        //第二种放方式：map使用，方便
        Map<String, Object> map = courseService.getMapById(id);
        return  Result.ok().data(map);
    }

    /**
     * 修改课程状态
     * @param id
     * @return
     */
    @ApiOperation(value = "修改课程状态")
    @PutMapping("/status/{id}")
    public Result updateByStatusById(@PathVariable String id){
        Boolean flag = courseService.updateStatusById(id);
        if(flag){
            return Result.ok();
        } else{
            return Result.error();
        }
    }


    @ApiOperation(value = "分页课程列表")
    @GetMapping(value = "{page}/{limit}")
    public Result pageList(
        @ApiParam(name = "page", value = "当前页码", required = true)
        @PathVariable Long page,
        @ApiParam(name = "limit", value = "每页记录数", required = true)
        @PathVariable Long limit){

        Page<EduCourse> pageParam = new Page<EduCourse>(page, limit);

        Map<String, Object> map = courseService.pageListWeb(pageParam);

        return  Result.ok().data(map);

    }


}

