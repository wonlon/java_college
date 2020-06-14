package com.guli.teacher.mapper;

import com.guli.teacher.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guli.teacher.entity.vo.CoursePublishVo;

import java.util.Map;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author guli
 * @since 2020-02-16
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {
    CoursePublishVo getCoursePublishVoById(String id);

    Map<String, Object> getMapById(String id);
}
