package com.guli.teacher.service;

import com.baomidou.mybatisplus.extension.api.R;
import com.guli.teacher.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author guli
 * @since 2020-02-16
 */
public interface EduVideoService extends IService<EduVideo> {

    /**
     * 根据VideoId删除视频
     * @param id
     * @return
     */
    boolean removeVideoById(String id);

    R deleteByOSSId(String id);
}
