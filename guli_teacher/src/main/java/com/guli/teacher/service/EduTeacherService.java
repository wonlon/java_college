package com.guli.teacher.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.teacher.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.teacher.entity.query.TeacherQuery;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author guli
 * @since 2020-02-13
 */
public interface EduTeacherService extends IService<EduTeacher> {

    void pageQuery(Page<EduTeacher> teacherPage, TeacherQuery teacherQuery);

    public Map<String, Object> pageListWeb(Page<EduTeacher> pageParam);
}
