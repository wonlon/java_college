package com.guli.teacher.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.common.result.EduException;
import com.guli.teacher.entity.EduCourse;
import com.guli.teacher.entity.EduCourseDescription;
import com.guli.teacher.entity.query.CourseQuery;
import com.guli.teacher.entity.vo.CourseDesc;
import com.guli.teacher.entity.vo.CoursePublishVo;
import com.guli.teacher.mapper.EduCourseMapper;
import com.guli.teacher.service.EduCourseDescriptionService;
import com.guli.teacher.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author guli
 * @since 2020-02-16
 */
@Service
@Transactional
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionService courseDescriptionService;
    @Override
    public String saveCourseDesc(CourseDesc courseDesc) {

        //1、添加课程基本信息表
        int insert = baseMapper.insert(courseDesc.getEduCourse());
        if(insert <= 0){
            throw new EduException(20001,"添加课程失败");
        }
        //2、获取课程表的Id
        String courseId = courseDesc.getEduCourse().getId();
        //3、添加课程描述表
        courseDesc.getEduCourseDescription().setId(courseId);
        boolean save = courseDescriptionService.save(courseDesc.getEduCourseDescription());
        if(!save){
            throw new EduException(20001,"添加描述失败");
        }
        return courseId;
    }

    @Override
    public CourseDesc getCourseVoById(String id) {
        CourseDesc courseVo = new CourseDesc();
        //获取课程
        EduCourse eduCourse = baseMapper.selectById(id);
        if(eduCourse == null){
            throw new EduException(20001,"此课程不存在");
        }
        courseVo.setEduCourse(eduCourse);

        EduCourseDescription eduCourseDescription = courseDescriptionService.getById(id);
        if(eduCourseDescription != null){
            courseVo.setEduCourseDescription(eduCourseDescription);
        }
        return courseVo;
    }

    @Override
    public Boolean updateCourseVo(CourseDesc courseVo) {
        if(StringUtils.isEmpty(courseVo.getEduCourse().getId())){
            throw new EduException(20001,"没有课程编号，修改失败");
        }
        int i = baseMapper.updateById(courseVo.getEduCourse());
        if(i <= 0 ){
            throw new EduException(20001,"修改课程信息失败");
        }
        //获取course—ID
        String courseId = courseVo.getEduCourse().getId();
        //设置课程描述的ID
        courseVo.getEduCourseDescription().setId(courseId);
        boolean b = courseDescriptionService.updateById(courseVo.getEduCourseDescription());
        return b;
    }
    @Override
    public void pageQuery(Page<EduCourse> pageParam, CourseQuery courseQuery) {
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("gmt_create");
        if (courseQuery == null){
            baseMapper.selectPage(pageParam, queryWrapper);
            return;
        }
        String title = courseQuery.getTitle();
        String teacherId = courseQuery.getTeacherId();
        String subjectParentId = courseQuery.getSubjectParentId();
        String subjectId = courseQuery.getSubjectId();
        if (!StringUtils.isEmpty(title)) {
            queryWrapper.like("title", title);
        }
        if (!StringUtils.isEmpty(teacherId) ) {
            queryWrapper.eq("teacher_id", teacherId);
        }
        if (!StringUtils.isEmpty(subjectParentId)) {
            queryWrapper.eq("subject_parent_id", subjectParentId);
        }
        if (!StringUtils.isEmpty(subjectId)) {
            queryWrapper.eq("subject_id", subjectId);
        }
        baseMapper.selectPage(pageParam, queryWrapper);
    }

    @Override
    public boolean removeCourseById(String id) {
        //TODO 根据id删除所有视频

        //TODO 根据id删除所有章节

        //删除课程描述
        boolean b = courseDescriptionService.removeById(id);
        if(!b){// 如果描述没有删除成功直接返回
            return false;
        }
        Integer result = baseMapper.deleteById(id);

        return result == 1 ;
    }

    @Override
    public CoursePublishVo getCoursePublishVoById(String id) {
        CoursePublishVo vo = baseMapper.getCoursePublishVoById(id);
        return vo;
    }

    @Override
    public Boolean updateStatusById(String id) {
        EduCourse course = new EduCourse();
        course.setId(id);
        course.setStatus("Normal");
        int update = baseMapper.updateById(course);
        return update > 0;
    }

    @Override
    public Map<String, Object> getMapById(String id) {
        Map<String,Object> map = baseMapper.getMapById(id);
        return map;
    }

    @Override
    public Map<String, Object> pageListWeb(Page<EduCourse> pageParam) {

        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();

        queryWrapper.orderByDesc("gmt_modified");

        baseMapper.selectPage(pageParam, queryWrapper);

        List<EduCourse> records = pageParam.getRecords();

        long current = pageParam.getCurrent();

        long pages = pageParam.getPages();

        long size = pageParam.getSize();

        long total = pageParam.getTotal();

        boolean hasNext = pageParam.hasNext();

        boolean hasPrevious = pageParam.hasPrevious();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);
        return map;
    }
}
