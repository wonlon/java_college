package com.guli.teacher.service;

import com.guli.teacher.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.teacher.entity.vo.OneChapter;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author guli
 * @since 2020-02-16
 */
public interface EduChapterService extends IService<EduChapter> {

    /**
     * 根据课程ID查询章节和小节列表
     * @param id
     * @return
     */
    List<OneChapter> queryChapterAndVideoList(String id);
    /**
     * 根据章节ID删除章节
     * @param id
     * @return
     */
    Boolean removeByChapterId(String id);
}
