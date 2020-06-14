package com.guli.teacher.service.impl;

import com.baomidou.mybatisplus.extension.api.R;
import com.guli.teacher.client.OssSourceClient;
import com.guli.teacher.entity.EduVideo;
import com.guli.teacher.mapper.EduVideoMapper;
import com.guli.teacher.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author guli
 * @since 2020-02-16
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Autowired
    private OssSourceClient VodClient;

    @Override
    public boolean removeVideoById(String id) {
        //删除阿里云上的视频
        //删除数据库中的Video
        int delete = baseMapper.deleteById(id);
        return delete == 1;
    }
    @Override
    public R deleteByOSSId(String id){
        return VodClient.removeVideo(id);
    }
}
