package com.guli.teacher.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.teacher.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.teacher.entity.query.CourseQuery;
import com.guli.teacher.entity.vo.CourseDesc;
import com.guli.teacher.entity.vo.CoursePublishVo;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author guli
 * @since 2020-02-16
 */
public interface EduCourseService extends IService<EduCourse> {
    /**
     * 保存课程基本信息
     * @param courseDesc
     * @return 课程的ID
     */
    String saveCourseDesc(CourseDesc courseDesc);

    /**
     * 根据课程ID获取课程基本信息
     * @param id
     * @return
     */
    CourseDesc getCourseVoById(String id);
    /**
     * 修改课程基本信息
     * @param courseVo
     * @return
     */
    Boolean updateCourseVo(CourseDesc courseVo);
    /**
     * 根据条件分页查询课程列表
     * @param pageParam
     * @param courseQuery
     */
    void pageQuery(Page<EduCourse> pageParam, CourseQuery courseQuery);
    /**
     * 根据课程ID删除课程信息
     * @param id
     * @return
     */
    boolean removeCourseById(String id);

    /**
     * 根据课程Id查询课程Vo对象 - 第一种方式
     * @param id
     * @return
     */
    CoursePublishVo getCoursePublishVoById(String id);
    /**
     * 根据课程Id查询课程Vo对象 - 第二种方式(常用)
     * @param id
     * @return
     */
    Map<String, Object> getMapById(String id);
    /**
     * 修改状态
     * @param id
     * @return
     */
    Boolean updateStatusById(String id);

    Map<String, Object> pageListWeb(Page<EduCourse> pageParam);
}
