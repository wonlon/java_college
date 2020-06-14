package com.guli.teacher.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.guli.common.result.EduException;
import com.guli.teacher.entity.EduChapter;
import com.guli.teacher.entity.EduVideo;
import com.guli.teacher.entity.vo.OneChapter;
import com.guli.teacher.entity.vo.TwoVideo;
import com.guli.teacher.mapper.EduChapterMapper;
import com.guli.teacher.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guli.teacher.service.EduVideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author guli
 * @since 2020-02-16
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {
    @Autowired
    private EduVideoService eduVideoService;
    @Override
    public List<OneChapter> queryChapterAndVideoList(String id) {
        //定义一个章节集合
        List<OneChapter> oneChapterList = new ArrayList<>();
        QueryWrapper<EduChapter> wr = new QueryWrapper<>();
        wr.eq("course_id",id);
        wr.orderByAsc("sort", "id");
        //先查询章节列表集合
        List<EduChapter> chapterList = baseMapper.selectList(wr);
        //再遍历章节集合，获取每个章节ID
        for (EduChapter eduChapter : chapterList) {
            OneChapter oneChapter = new OneChapter();
            BeanUtils.copyProperties(eduChapter,oneChapter);
            //再根据每个章节的ID查询节点的列表
            QueryWrapper<EduVideo> videoWrapper = new QueryWrapper<>();
            videoWrapper.eq("chapter_id",oneChapter.getId());
            videoWrapper.orderByAsc("sort", "id");
            List<EduVideo> eduVideoList = eduVideoService.list(videoWrapper);
            //把小节的列表添加章节中
            for(EduVideo eduVideo : eduVideoList){
                TwoVideo twoVideo = new TwoVideo();
                BeanUtils.copyProperties(eduVideo,twoVideo);
                oneChapter.getChildren().add(twoVideo);
            }
            oneChapterList.add(oneChapter);
        }

        return oneChapterList;
    }

    @Override
    public Boolean removeByChapterId(String id) {

        //判断是否存在小节
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id",id);
        List<EduVideo> list = eduVideoService.list(wrapper);
        if(list.size() != 0){
            throw new EduException(20001,"该分章节下存在视频课程，请先删除视频课程");
        }
        //删除章节
        int delete = baseMapper.deleteById(id);
        return delete > 0;
    }
}
