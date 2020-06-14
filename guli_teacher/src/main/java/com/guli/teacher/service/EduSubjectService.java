package com.guli.teacher.service;

import com.guli.teacher.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.teacher.entity.vo.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author guli
 * @since 2020-02-15
 */
public interface EduSubjectService extends IService<EduSubject> {
    /**
     * 导入excel
     * @param file
     * @return
     */
    List<String> importExl(MultipartFile file);

    /**
     * 获取所有的课程分类树
     * @return
     */
    List<OneSubject> getTree();
    /**
     * 保存课程分类
     * @param subject
     * @return
     */
    boolean saveLevelOne(EduSubject subject);
    /**
     * 保存二级课程分类
     * @param subject
     * @return
     */
    boolean saveLevelTwo(EduSubject subject);
}

